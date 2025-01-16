package Umc.replendar.assignment.service;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;


public interface AssignmentService {

    ApiResponse<String> createAssignment(AssignmentReq.CreateReqDto reqDto); //과제 생성
}
