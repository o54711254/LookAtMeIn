import torch.nn as nn
from torch.nn import functional as F
import numpy as np


def get_nonspade_norm_layer(cfg, norm_type="instance"):
    # helper function to get # output channels of the previous layer
    def get_out_channel(layer):
        if hasattr(layer, "out_channels"):
            return getattr(layer, "out_channels")
        return layer.weight.size(0)

    # this function will be returned
    def add_norm_layer(layer):
        nonlocal norm_type
        if norm_type.startswith("spectral"):
            layer = spectral_norm(layer)
            subnorm_type = norm_type[len("spectral") :]

        if subnorm_type == "none" or len(subnorm_type) == 0:
            return layer

        # remove bias in the previous layer, which is meaningless
        # since it has no effect after normalization
        if getattr(layer, "bias", None) is not None:
            delattr(layer, "bias")
            layer.register_parameter("bias", None)

        if subnorm_type == "batch":
            norm_layer = nn.BatchNorm2d(get_out_channel(layer), affine=True)
        elif subnorm_type == "instance":
            norm_layer = nn.InstanceNorm2d(get_out_channel(layer), affine=False)
        else:
            raise ValueError("normalization layer %s is not recognized" % subnorm_type)

        return nn.Sequential(layer, norm_layer)

    return add_norm_layer

def spectral_norm(module, mode=True):
    if mode:
        return nn.utils.spectral_norm(module)

    return module


class MultiscaleDiscriminator(nn.Module):
    def __init__(self, cfg):
        super().__init__()
        self.cfg = cfg
        self.num_D = cfg.num_D

        for i in range(cfg.num_D):
            subnetD = self.create_single_discriminator(cfg)
            self.add_module("discriminator_%d" % i, subnetD)

    def create_single_discriminator(self, cfg):
        netD = NLayerDiscriminator(cfg)
        return netD

    def downsample(self, input):
        return F.avg_pool2d(
            input, kernel_size=3, stride=2, padding=[1, 1], count_include_pad=False
        )

    # Returns list of lists of discriminator outputs.
    # The final result is of size cfg.num_D x cfg.n_layers_D
    def forward(self, input):
        result = []
        get_intermediate_features = not self.cfg.no_ganFeat_loss
        for name, D in self.named_children():
            out = D(input)
            if not get_intermediate_features:
                out = [out]
            result.append(out)
            input = self.downsample(input)

        return result


# Defines the PatchGAN discriminator with the specified arguments.
class NLayerDiscriminator(nn.Module):
    def __init__(self, cfg):
        super().__init__()
        self.cfg = cfg

        kw = 4
        padw = int(np.ceil((kw - 1.0) / 2))
        nf = cfg.ndf
        input_nc = cfg.input_nc_D

        norm_layer = get_nonspade_norm_layer(cfg, cfg.norm_D)
        sequence = [
            [
                nn.Conv2d(input_nc, nf, kernel_size=kw, stride=2, padding=padw),
                nn.LeakyReLU(0.2, False),
            ]
        ]

        for n in range(1, cfg.n_layers_D):
            nf_prev = nf
            nf = min(nf * 2, 512)
            stride = 1 if n == cfg.n_layers_D - 1 else 2
            sequence += [
                [
                    norm_layer(
                        nn.Conv2d(
                            nf_prev, nf, kernel_size=kw, stride=stride, padding=padw
                        )
                    ),
                    nn.LeakyReLU(0.2, False),
                ]
            ]

        sequence += [[nn.Conv2d(nf, 1, kernel_size=kw, stride=1, padding=padw)]]

        # We divide the layers into groups to extract intermediate layer outputs
        for n in range(len(sequence)):
            self.add_module("model" + str(n), nn.Sequential(*sequence[n]))

    def forward(self, input):
        results = [input]
        for submodel in self.children():
            intermediate_output = submodel(results[-1])
            results.append(intermediate_output)

        get_intermediate_features = not self.cfg.no_ganFeat_loss
        if get_intermediate_features:
            return results[1:]
        else:
            return results[-1]
