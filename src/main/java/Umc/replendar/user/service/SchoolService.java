package Umc.replendar.user.service;


import Umc.replendar.user.dto.req.SchoolDtoReq;
import Umc.replendar.user.dto.res.SchoolDtoRes;
import Umc.replendar.user.entity.School;
import Umc.replendar.user.repository.SchoolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public List<SchoolDtoRes.SchoolSimpleRes> searchSchool(String keyword) {
        List<School> schoolList;
        if (keyword == null || keyword.trim().isEmpty()) {
            // 키워드가 없으면 전체 목록
            schoolList = schoolRepository.findAll();
        } else {
            // 부분 일치 검색
            schoolList = schoolRepository.findBySchoolNameContaining(keyword);
        }

        // 엔티티 → DTO 변환
        return schoolList.stream()
                .map(SchoolDtoRes.SchoolSimpleRes::fromEntity)
                .toList();
    }

    public SchoolDtoRes.SchoolSimpleRes createSchool(SchoolDtoReq.CreateSchoolReq request) {

        if (schoolRepository.existsBySchoolName(request.getSchoolName())) {
            throw new IllegalArgumentException("이미 존재하는 학교명입니다.");
        }

        School school = new School();
        school.setSchoolName(request.getSchoolName());
        School saved = schoolRepository.save(school);

        return SchoolDtoRes.SchoolSimpleRes.fromEntity(saved);
    }
}
