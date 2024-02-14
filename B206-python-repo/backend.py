import cv2
import sys
from PIL import Image
import numpy as np
import time
import torch
import os
import torchvision
import torchvision.transforms as transforms
from ntpath import basename



basic_dir = os.path.abspath(os.path.join(os.path.dirname(__file__)))

sys.path.append(basic_dir)
os.environ['CUDA_VISIBLE_DEVICES'] = '0'

class Backend:
  

    def __init__(self, singletoModel):
        print("hello");
        super().__init__()
        self.uploaded_dir = "./uploaded"
        self.demo_save_dir = "./demo_results"
        if os.path.exists(self.demo_save_dir) is False:
            os.makedirs(self.demo_save_dir)

        self.model = singletoModel.getModel()
        self.model.opt.name = "face"
        self.model.load_networks(40)
        print("face model ready")

        self.output_img = None
        self.mat_img = None
        # self.mask_points = []
        self.sketch_points = []
        self.stroke_points = []
        self.ld_mask = None
        self.ld_sk = None
        

        self.modes = [0, 0, 0]
        self.mode_select(0)
        

        self.color = None
    
    def trans_to_pytorch_img(self, img):
       transform = transforms.Compose(
            [
                transforms.ToTensor(),
                transforms.Normalize(mean=[0.5] * 3, std=[0.5] * 3)
            ]
        )
       
       return transform(img)
    
    def trans_to_pytorch_mask(self, mask):
        transform = transforms.Compose([transforms.ToTensor()])
        return transform(mask)
        
    
    def open(self, filename):
        print(filename)

        if filename:
            mat_img_2 = cv2.imread(filename)
            name = basename(str(filename))
            name = name.split('.')[0]
            mat_img = cv2.imread(filename)
            # print("mat_img.shape", mat_img.shape)
            # print("mat_img: " , mat_img)
            

            mat_img = cv2.resize(mat_img, (512, 512), interpolation=cv2.INTER_CUBIC)
            print("type(mat_img): ", type(mat_img))
            print("mat_img.shape: ", mat_img.shape)
            self.mat_img = Image.fromarray(cv2.cvtColor(mat_img, cv2.COLOR_BGR2RGB))   

            mat_img_2 = cv2.resize(mat_img_2, (512, 512),interpolation=cv2.INTER_CUBIC)
            mat_img_2 = mat_img_2 / 127.5 - 1
            self.mat_img_2 = np.expand_dims(mat_img_2, axis=0)



    def complete(self, mask_points,sketch_points, stroke_points):
        
        sketch = self.make_sketch(sketch_points)
        stroke, stroke_down = self.make_stroke(stroke_points)
        mask = self.make_mask(mask_points)


        stroke_down = np.concatenate([stroke_down[:, :, 2:3], stroke_down[:, :, 1:2], stroke_down[:, :, :1]], axis=2)
        mask = Image.fromarray(mask.astype('uint8')).convert('RGB')
        sketch = Image.fromarray(sketch.astype('uint8')).convert('RGB')
        stroke = Image.fromarray(stroke.astype('uint8')).convert('RGB')

        sketch = self.trans_to_pytorch_img(sketch)
        stroke = self.trans_to_pytorch_img(stroke)
        mask = self.trans_to_pytorch_mask(mask)

        if not type(self.ld_mask) == type(None):
            ld_mask = np.expand_dims(self.ld_mask[:, :, 0:1], axis=0)
            ld_mask[ld_mask > 0] = 1
            ld_mask[ld_mask < 1] = 0
            mask = mask + ld_mask
            mask[mask > 0] = 1
            mask[mask < 1] = 0
            mask = np.asarray(mask, dtype=np.uint8)

        if not type(self.ld_sk) == type(None):
            sketch = sketch + self.ld_sk
            sketch[sketch>0] = 1

        input_image = self.trans_to_pytorch_img(self.mat_img)
        input_image = torch.unsqueeze(input_image, 0)
        sketch = torch.unsqueeze(sketch, 0)
        color = torch.unsqueeze(stroke, 0)
        mask_co = torch.unsqueeze(mask.clone(), 0)
        print("input_image.shape: ", input_image.shape)
        print()

        in_sk = sketch

        mask = mask.cuda()
        mask = mask[0]

        mask = torch.unsqueeze(mask, 0)
        mask = torch.unsqueeze(mask, 1)
        mask = mask.byte()
        start_t = time.time()
        
        with torch.no_grad():
            self.model.set_input(input_image, sketch, color, mask, mask_co)
            self.model.forward()
        end_t = time.time()

        print('inference time : {}'.format(end_t - start_t))


        output_dict = self.model.get_current_visuals()
        pic = (torch.cat([
            output_dict["input_image"], 
            output_dict["fake"], 
            output_dict["sketch"], 
            output_dict["color"]
        ], dim=0) + 1) / 2.0
        torchvision.utils.save_image(pic, self.demo_save_dir + '/output.png', nrow=2)
        torchvision.utils.save_image((output_dict["sketch"] + 1) / 2.0, self.demo_save_dir +'/sketch.png')
        torchvision.utils.save_image((output_dict["color"] + 1) / 2.0, self.demo_save_dir + '/color.png')

        result = ((output_dict["fake"].cpu() + 1) / 2.0) * 255
        result = np.asarray(result[0, :, :, :], dtype=np.uint8)
        print("result.shape: ", result.shape)
        result = result.transpose(1, 2, 0)

        self.output_img = result.copy()

        result = np.concatenate([result[:, :, :1], result[:, :, 1:2], result[:, :, 2:3]], axis=2)
        

    def mode_select(self, mode):
        for i in range(len(self.modes)):
            self.modes[i] = 0
        self.modes[mode] = 1

    def make_sketch(self, pts):
        if len(pts) > 0:
            sketch = np.ones((512,512,3)) * 255
            for pt in pts:
                cv2.line(sketch, pt["prev"], pt["curr"], (0,0,0), 2)   
        else:
            sketch = np.ones((512,512,3)) * 255

        return sketch


    def make_mask(self, pts):
        if len(pts) > 0:
            mask = np.zeros((512, 512, 3))
            for pt in pts:
                cv2.line(mask, pt['prev'], pt['curr'], (255, 255, 255), 12)
        else:
            mask = np.zeros((512, 512, 3))
        return mask
    

    
    def make_stroke(self, pts):
        if len(pts) > 0:
            stroke = np.ones((512, 512, 3)) * 127.5
            stroke_down = np.ones((512,512,3)) * 255.0

            for pt in pts:
                c = pt['color'].lstrip('#')
                color = tuple(int(c[i:i + 2], 16) for i in (0, 2, 4))
                color = (color[0], color[1], color[2])
                cv2.line(stroke, pt['prev'], pt['curr'], color, 4)
                cv2.line(stroke_down, pt['prev'], pt['curr'], color, 4)
        else:
            stroke = np.ones((512, 512, 3)) * 127.5
            stroke_down = np.ones((512, 512, 3)) * 255.0
        return stroke, stroke_down

    def save_img(self,  savePath):
        if type(self.output_img):
            
            self.output_img = np.concatenate(
                [self.output_img[:, :, 2:3], self.output_img[:, :, 1:2], self.output_img[:, :, :1]], axis=2)
            
            cv2.imwrite(savePath, self.output_img)   

    
    