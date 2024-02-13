package com.ssafy.lam.reserve.service;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reserve.domain.PastReserveRepository;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final CustomerRepository customerRepository;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final PastReserveRepository pastReserveRepository;

    private Logger log = LoggerFactory.getLogger(ReserveServiceImpl.class);
    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    @Override
    public List<ReserveResponseDto> findByUserSeq(Long userSeq) {
        List<Reserve> reserves = reserveRepository.findByUserSeq(userSeq);
        List<ReserveResponseDto> reserveResponseDtos = new ArrayList<>();

        for(Reserve reserve : reserves){
            Hospital hospital = hospitalRepository.findByUserUserSeq(reserve.getHospital().getUserSeq()).get();
            ReserveResponseDto dto = ReserveResponseDto.builder()
                    .customerName(reserve.getCustomer().getName())
                    .hospitalName(hospital.getUser().getName())
                    .reserveType(reserve.getReserveType())
                    .year(reserve.getYear())
                    .month(reserve.getMonth())
                    .day(reserve.getDay())
                    .dayofweek(reserve.getDayofweek())
                    .time(reserve.getTime())
                    .build();

            if(hospital.getProfileFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ hospital.getProfileFile().getName());
                    String hospitalProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String hospitalProfileType = hospital.getProfileFile().getType();
                    dto.setHospitalProfileBase64(hospitalProfileBase64);
                    dto.setHospitalProfileType(hospitalProfileType);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            reserveResponseDtos.add(dto);


        }
        return reserveResponseDtos;
    }


    @Override
    @Transactional
    public Reserve saveReserve(ReserveSaveRequestDto dto) {
        log.info("ReserveSaveRequestDto : {}", dto);
        User customerUser = userRepository.findById(dto.getCustomerUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getCustomerUserSeq()));

//        User hospitalUser = userRepository.findById(dto.getHospitalUserSeq())
//                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getHospitalUserSeq()));
        Hospital hospitalUser = hospitalRepository.findById(dto.getHospitalUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getHospitalUserSeq()));
        log.info("customerUser : {}", customerUser.getUserId());
        log.info("hospitalUser : {}", hospitalUser.getUser().getUserId());
        if (customerUser.getUserType().equals("CUSTOMER") && hospitalUser.getUser().getUserType().equals("HOSPITAL")) {
            Reserve reserve = dto.toEntity(customerUser, hospitalUser.getUser());
            return reserveRepository.save(reserve);
        } else {
            throw new IllegalArgumentException("고객과 병원 매치가 안됨");
        }
    }


    @Override
    @Transactional
    public void deleteReserve(Long reserveSeq) {
        reserveRepository.findById(reserveSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. reserveSeq=" + reserveSeq));

        reserveRepository.deleteById(reserveSeq);
    }

}