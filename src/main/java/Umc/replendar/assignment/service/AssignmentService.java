package Umc.replendar.assignment.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AssignmentService {

    ApiResponse<String> createAssignment(Long id,AssignmentReq.CreateReqDto reqDto); //과제 생성

    ApiResponse<List<AssignmentRes.assMainTopRes>> getAssMainTopAssignment(Long userId);

    ApiResponse<String> updateAssignment(AssignmentReq.updateReqDto reqDto);

    ApiResponse<String> deleteAssignment(Long assId);

    ApiResponse<AssignmentRes.assDetailRes> getAssDetail(Long assId);

    ApiResponse<List<AssignmentRes.assMonthRes>> getAssMonth(Long userId, String month);

    ApiResponse<List<AssignmentRes.assShareRes>> getAssShareFriendList(Long userId);

    ApiResponse<String> completeAssignment(Long userId, Long assId);

    ApiResponse<String> storeAssignment(AssignmentReq.CreateReqDto reqDto, Long userId);

    ApiResponse<String> statusStoreAssignment(Long userId, Long assId);

    ApiResponse<Page<AssignmentRes.assCompleteRes>> getStoreAssignment(Long userId, Pageable adjustedPageable, Status status);

    ApiResponse<Page<AssignmentRes.assCompleteRes>> getCompleteAssignment(Long userId, Pageable adjustedPageable);
}
