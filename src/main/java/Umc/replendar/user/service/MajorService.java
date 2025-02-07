package Umc.replendar.user.service;

import Umc.replendar.user.dto.req.MajorDtoReq;
import Umc.replendar.user.dto.res.MajorDtoRes;
import Umc.replendar.major.entity.Major;
import Umc.replendar.major.entity.School;
import Umc.replendar.major.repository.MajorRepository;
import Umc.replendar.major.repository.SchoolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MajorService {

    private final MajorRepository majorRepository;
    private final SchoolRepository schoolRepository;

    public List<MajorDtoRes.MajorSimpleRes> searchMajor(Long schoolId, String keyword) {

        List<Major> majorList;
        if (keyword == null || keyword.trim().isEmpty()) {
            majorList = majorRepository.findBySchoolId(schoolId);
        } else {
            majorList = majorRepository.findBySchoolIdAndMajorNameContaining(schoolId, keyword);
        }

        return majorList.stream()
                .map(MajorDtoRes.MajorSimpleRes::fromEntity)
                .toList();
    }

    public MajorDtoRes.MajorSimpleRes createMajor(MajorDtoReq.CreateMajorReq request) {
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학교 ID입니다."));

        if (majorRepository.existsBySchoolIdAndMajorName(
                request.getSchoolId(), request.getMajorName())) {
            throw new IllegalArgumentException("이미 해당 학교에 존재하는 학과명입니다.");
        }

        Major major = new Major();
        major.setSchool(school);
        major.setMajorName(request.getMajorName());

        Major saved = majorRepository.save(major);
        return MajorDtoRes.MajorSimpleRes.fromEntity(saved);
    }
}
