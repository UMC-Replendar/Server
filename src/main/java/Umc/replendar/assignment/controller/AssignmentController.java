package Umc.replendar.assignment.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;
import Umc.replendar.assignment.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Operation(summary = "Create a new assignment",description = "과제 등록 API")
    @PostMapping("")
    public ApiResponse<String> createAssignment(@RequestBody AssignmentReq.CreateReqDto reqDto){
        System.out.println(reqDto.getUserId());
        return assignmentService.createAssignment(reqDto);
    }


}
