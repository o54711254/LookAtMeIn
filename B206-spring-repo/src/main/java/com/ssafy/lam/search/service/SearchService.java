package com.ssafy.lam.search.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.QFreeboard;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.hospital.domain.QHospital;
import com.ssafy.lam.reviewBoard.domain.QReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoardRepository;
import com.ssafy.lam.search.dto.FreeboardDto;
import com.ssafy.lam.search.dto.HospitalDto;
import com.ssafy.lam.search.dto.ReviewBoardDto;
import com.ssafy.lam.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final JPAQueryFactory queryFactory;
    private final HospitalRepository hospitalRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final CustomerRepository customerRepository;

    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    public List<?> search(String keyword, String category) {
        if ("hospital".equals(category)) {
            return searchInHospital(keyword);
        } else if ("freeboard".equals(category)) {
            return searchInFreeboard(keyword);
        } else if ("reviewBoard".equals(category)) {
            return searchInReviewBoard(keyword);
        } else {
            List<Object> results = new ArrayList<>();
            results.addAll(searchInHospital(keyword));
            results.addAll(searchInFreeboard(keyword));
            results.addAll(searchInReviewBoard(keyword));
            return results;
        }
    }

    public List<HospitalDto> searchInHospital(String keyword) {
        QHospital hospital = QHospital.hospital;
        List<Hospital> results = queryFactory.selectFrom(hospital)
                .where(hospital.user.name.contains(keyword)
                        .or(hospital.address.contains(keyword))
                        .or(hospital.email.contains(keyword)))
                .fetch();

        return results.stream().map(result -> {
            HospitalDto hospitalDto = HospitalDto.builder()
                    .hospitalInfo_seq(result.getHospitalSeq())
                    .hospitalInfo_name(result.getUser().getName())
                    .hospitalInfo_phoneNumber(result.getTel())
                    .hospitalInfo_introduce(result.getIntro())
                    .hospitalInfo_address(result.getAddress())
                    .hospitalInfo_open(result.getOpenTime())
                    .hospitalInfo_close(result.getCloseTime())
                    .hospitalInfo_avgScore(hospitalRepository.findAvgByHospitalSeq(result.getHospitalSeq()).orElse(0.0))
                    .build();

            if(result.getProfileFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ result.getProfileFile().getName());
                    String hospitalProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String hospitalProfileType = result.getProfileFile().getType();
                    hospitalDto.setHospitalProfileBase64(hospitalProfileBase64);
                    hospitalDto.setHospitalProfileType(hospitalProfileType);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            return hospitalDto;
        }).collect(Collectors.toList());
    }

    public List<FreeboardDto> searchInFreeboard(String keyword) {
        QFreeboard freeboard = QFreeboard.freeboard;
        List<Freeboard> results = queryFactory.selectFrom(freeboard)
                .where(freeboard.title.contains(keyword)
                        .or(freeboard.content.contains(keyword)))
                .fetch();

        return results.stream().map(result -> {
            FreeboardDto freeboardDto = FreeboardDto.builder()
                    .freeboardSeq(result.getFreeboardSeq())
                    .userId(result.getUser().getUserId())
                    .userEmail(customerRepository.findByUserUserSeq(result.getUser().getUserSeq()).get().getEmail())
                    .freeboardTitle(result.getTitle())
                    .freeboardContent(result.getContent())
                    .freeboardRegisterdate(result.getRegisterDate())
                    .freeboardCnt(result.getCnt())
                    .build();

            if(result.getUploadFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ result.getUploadFile().getName());
                    String uploadImgBase64 = EncodeFile.encodeFileToBase64(path);
                    String uploadImgType = result.getUploadFile().getType();
                    freeboardDto.setUploadImgBase64(uploadImgBase64);
                    freeboardDto.setUploadImgType(uploadImgType);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            Customer customer = customerRepository.findByUserUserSeq(result.getUser().getUserSeq()).get();
            if(customer.getProfile() != null){
                UploadFile customerProfile = customer.getProfile();
                Path path = Paths.get(uploadPath+"/"+customerProfile.getName());
                try{
                    String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String customerProfileType = customerProfile.getType();
                    freeboardDto.setCustomerProfileBase64(customerProfileBase64);
                    freeboardDto.setCustomerProfileType(customerProfileType);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }



            return freeboardDto;
        }).collect(Collectors.toList());
    }

    public List<ReviewBoardDto> searchInReviewBoard(String keyword) {
        QReviewBoard reviewBoard = QReviewBoard.reviewBoard;
        List<ReviewBoard> results = queryFactory.selectFrom(reviewBoard)
                .where(reviewBoard.title.contains(keyword)
                        .or(reviewBoard.content.contains(keyword))
                        .or(reviewBoard.region.contains(keyword))
                        .or(reviewBoard.doctor.docInfoName.contains(keyword))
                        .or(reviewBoard.surgery.contains(keyword)))
                .fetch();

        return results.stream().map(result -> {
            ReviewBoardDto reviewBoardDto = ReviewBoardDto.builder()
                    .reviewBoard_seq(result.getSeq())
                    .reviewBoard_title(result.getTitle())
                    .reviewBoard_content(result.getContent())
                    .reviewBoard_score(result.getScore())
                    .customer_name(reviewBoardRepository.findById(result.getSeq()).get().getUser().getName())
                    .reviewBoard_doctor(result.getDoctor().getDocInfoName())
                    .reviewBoard_region(result.getRegion())
                    .reviewBoard_surgery(result.getSurgery())
                    .reviewBoard_hospital(result.getHospital().getUser().getName())
                    .reviewBoard_cnt(result.getCnt())
                    .reviewBoard_score(result.getScore())
                    .build();

            Hospital hospital = result.getHospital();
            if(hospital.getProfileFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ hospital.getProfileFile().getName());
                    String hospitalProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String hospitalProfileType = hospital.getProfileFile().getType();
                    reviewBoardDto.setHospitalProfileBase64(hospitalProfileBase64);
                    reviewBoardDto.setHospitalProfileType(hospitalProfileType);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(result.getUploadFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ result.getUploadFile().getName());
                    String uploadImgBase64 = EncodeFile.encodeFileToBase64(path);
                    String uploadImgType = result.getUploadFile().getType();
                    reviewBoardDto.setUploadImgBase64(uploadImgBase64);
                    reviewBoardDto.setUploadImgType(uploadImgType);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }


            return reviewBoardDto;
        }).collect(Collectors.toList());
    }
}
