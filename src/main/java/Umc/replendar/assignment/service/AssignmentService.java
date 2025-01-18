package Umc.replendar.assignment.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;

import java.util.List;


public interface AssignmentService {

    ApiResponse<String> createAssignment(AssignmentReq.CreateReqDto reqDto); //과제 생성

    ApiResponse<List<AssignmentRes.assMainTopRes>> getAssMainTopAssignment(Long userId);

    ApiResponse<String> updateAssignment(AssignmentReq.updateReqDto reqDto);

    ApiResponse<String> deleteAssignment(Long assId);

    ApiResponse<AssignmentRes.assDetailRes> getAssDetail(Long assId);

    ApiResponse<List<AssignmentRes.assMonthRes>> getAssMonth(Long userId, String month);
}
