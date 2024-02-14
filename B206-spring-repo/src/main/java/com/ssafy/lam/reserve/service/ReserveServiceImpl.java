package com.ssafy.lam.reserve.service;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.questionnaire.domain.QuesionnareRepository;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveRequestDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final CustomerRepository customerRepository;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;

    private final UploadFileService uploadFileService;
    private final QuesionnareRepository quesionnareRepository;

    private Logger log = LoggerFactory.getLogger(ReserveServiceImpl.class);
    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();


    // 상담 예약 내역 전체 조회
    @Override
    public List<ReserveResponseDto> findByUserSeq(Long userSeq) {
        List<Reserve> reserves = reserveRepository.findAllByUserSeqAndCompletedFalse(userSeq);
        List<ReserveResponseDto> reserveResponseDtos = new ArrayList<>();

        for(Reserve reserve : reserves){
            Hospital hospital = hospitalRepository.findByUserUserSeq(reserve.getHospital().getUserSeq()).get();
            ReserveResponseDto dto = ReserveResponseDto.builder()
                    .customerUserSeq(reserve.getCustomer().getUserSeq())
                    .customerName(reserve.getCustomer().getName())
                    .hospitalUserSeq(reserve.getHospital().getUserSeq())
                    .hospitalName(hospital.getUser().getName())
                    .reserveSeq(reserve.getSeq())
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


    // 상담 예약 내역 상세조회
    @Override
    public Reserve getDetailReserveNotCompleted(Long reserveSeq) {
        return reserveRepository.findBySeqAndCompletedFalse(reserveSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. reserveSeq=" + reserveSeq));
    }

    @Override
    @Transactional
    public Reserve saveReserve(ReserveRequestDto dto) {
        log.info("ReserveSaveRequestDto : {}", dto);
        User customerUser = userRepository.findById(dto.getCustomerUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getCustomerUserSeq()));

        Hospital hospitalUser = hospitalRepository.findById(dto.getHospitalSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getHospitalSeq()));
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
        Reserve reserve = reserveRepository.findById(reserveSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. reserveSeq=" + reserveSeq));

        reserve.setDeleted(true);
        reserveRepository.save(reserve);
//        reserveRepository.deleteById(reserveSeq);
    }


    // ===================== 상담완료 =====================

    @Override
    public void complete(ReserveRequestDto reserveRequestDto, QuestionnaireRequestDto questionnaireRequestDto, MultipartFile beforeImg, MultipartFile afterImg) {
        try{
            Questionnaire questionnaire = Questionnaire.builder()
                    .seq(questionnaireRequestDto.getQuestionnaireSeq())
                    .blood(questionnaireRequestDto.getQuestionnaire_blood())
                    .title(questionnaireRequestDto.getQuestionnaire_title())
                    .content(questionnaireRequestDto.getQuestionnaire_content())
                    .remark(questionnaireRequestDto.getQuestionnaire_remark())
                    .build();

            questionnaire = quesionnareRepository.save(questionnaire);

            User customer = User.builder()
                    .userSeq(reserveRequestDto.getCustomerUserSeq())
                    .build();

            User hospital = User.builder()
                    .userSeq(reserveRequestDto.getHospitalSeq())
                    .build();

            UploadFile beforeFile =  uploadFileService.store(beforeImg);
            UploadFile afterFile = uploadFileService.store(afterImg);


            Reserve reserve = Reserve.builder()
                    .beforeImg(beforeFile)
                    .afterImg(afterFile)
                    .questionnaire(questionnaire)
                    .customer(customer)
                    .hospital(hospital)
                    .seq(reserveRequestDto.getReserveSeq())
                    .completed(true) // 상담 완료
                    .price(reserveRequestDto.getPrice())
                    .year(reserveRequestDto.getYear())
                    .month(reserveRequestDto.getMonth())
                    .day(reserveRequestDto.getDay())
                    .dayofweek(reserveRequestDto.getDayofweek())
                    .time(reserveRequestDto.getTime())
                    .build();


            reserveRepository.save(reserve);


        }catch (Exception e){
            log.error("상담 종료 실패 :{}", e.getMessage());
            throw new RuntimeException("상담 종료 실패");
        }
    }


    // ===================== 상담완료 =====================


    // ===================== 상담한 내역 전체 조회 =====================
    @Override
    public List<ReserveResponseDto> getAllByUserSeqCompleted(Long userSeq) {
        List<Reserve> reserves = reserveRepository.findAllByUserSeqAndCompletedTrue(userSeq);
        List<ReserveResponseDto> reserveResponseDtos = new ArrayList<>();
        for(Reserve r : reserves){



            ReserveResponseDto responseDto = ReserveResponseDto.builder()
                    .reserveSeq(r.getSeq())
                    .customerUserSeq(r.getCustomer().getUserSeq())
                    .customerName(r.getCustomer().getName())
                    .hospitalUserSeq(r.getHospital().getUserSeq())
                    .hospitalName(r.getHospital().getName())
                    .year(r.getYear())
                    .month(r.getMonth())
                    .day(r.getDay())
                    .dayofweek(r.getDayofweek())
                    .time(r.getTime())

                    .content(r.getContent())
                    .price(r.getPrice())
                    .completed(r.isCompleted())
                    .build();
            Hospital hospital = hospitalRepository.findByUserUserSeq(r.getHospital().getUserSeq()).get();
            if(hospital.getProfileFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ hospital.getProfileFile().getName());
                    String hospitalProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String hospitalProfileType = hospital.getProfileFile().getType();
                    responseDto.setHospitalProfileBase64(hospitalProfileBase64);
                    responseDto.setHospitalProfileType(hospitalProfileType);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

//            if(r.getAfterImg() != null){
//                try{
//                    Path path = Paths.get(uploadPath +"/"+ r.getAfterImg().getName());
//                    String afterImgBase64 = EncodeFile.encodeFileToBase64(path);
//                    String afterImgType = r.getAfterImg().getType();
//                    responseDto.setAfterImgBase64(afterImgBase64);
//                    responseDto.setAfterImgType(afterImgType);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }

//            if(r.getBeforeImg() != null){
//                try{
//                    Path path = Paths.get(uploadPath +"/"+ r.getBeforeImg().getName());
//                    String beforeImgBase64 = EncodeFile.encodeFileToBase64(path);
//                    String beforeImgType = r.getBeforeImg().getType();
//                    responseDto.setBeforeImgBase64(beforeImgBase64);
//                    responseDto.setBeforeImgType(beforeImgType);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }

            reserveResponseDtos.add(responseDto);

        }
        return reserveResponseDtos;
    }
    // ===================== 상담한 내역 전체 조회 =====================

    // ===================== 상담한 내역 상세 조회 =====================

    @Override
    public ReserveResponseDto getDetailReseveCompleted(Long reserveSeq) {
        Reserve reserve = reserveRepository.findBySeqAndCompletedTrue(reserveSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. reserveSeq=" + reserveSeq));

        ReserveResponseDto responseDto = ReserveResponseDto.builder()
                .reserveSeq(reserve.getSeq())
                .customerUserSeq(reserve.getCustomer().getUserSeq())
                .customerName(reserve.getCustomer().getName())
                .hospitalUserSeq(reserve.getHospital().getUserSeq())
                .hospitalName(reserve.getHospital().getName())
                .year(reserve.getYear())
                .month(reserve.getMonth())
                .day(reserve.getDay())
                .dayofweek(reserve.getDayofweek())
                .time(reserve.getTime())
                .content(reserve.getContent())
                .price(reserve.getPrice())
                .completed(reserve.isCompleted())
                .build();
        Hospital hospital = hospitalRepository.findByUserUserSeq(reserve.getHospital().getUserSeq()).get();
        if(hospital.getProfileFile() != null){
            try{
                Path path = Paths.get(uploadPath +"/"+ hospital.getProfileFile().getName());
                String hospitalProfileBase64 = EncodeFile.encodeFileToBase64(path);
                String hospitalProfileType = hospital.getProfileFile().getType();
                responseDto.setHospitalProfileBase64(hospitalProfileBase64);
                responseDto.setHospitalProfileType(hospitalProfileType);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        if(reserve.getAfterImg() != null){
            try{
                Path path = Paths.get(uploadPath +"/"+ reserve.getAfterImg().getName());
                String afterImgBase64 = EncodeFile.encodeFileToBase64(path);
                String afterImgType = reserve.getAfterImg().getType();
                responseDto.setAfterImgBase64(afterImgBase64);
                responseDto.setAfterImgType(afterImgType);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        if (reserve.getBeforeImg() != null) {
            try {
                Path path = Paths.get(uploadPath + "/" + reserve.getBeforeImg().getName());
                String beforeImgBase64 = EncodeFile.encodeFileToBase64(path);
                String beforeImgType = reserve.getBeforeImg().getType();
                responseDto.setBeforeImgBase64(beforeImgBase64);
                responseDto.setBeforeImgType(beforeImgType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return responseDto;
    }
    // ===================== 상담한 내역 상세 조회 =====================
}