package Umc.replendar.assignment.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.assignment.dto.reqDto.AssignmentReq;
import Umc.replendar.assignment.dto.resDto.AssignmentRes;
import Umc.replendar.assignment.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Operation(summary = "과제 등록 API",description = "과제 등록 API")
    @PostMapping("")
    public ApiResponse<String> createAssignment(@RequestBody AssignmentReq.CreateReqDto reqDto){
        System.out.println(reqDto.getUserId());
        return assignmentService.createAssignment(reqDto);
    }

    @Operation(summary = "메인화면 상단 과제 조회 API",description = "메인화면 상단 과제 조회 API")
    @GetMapping("")
    public ApiResponse<List<AssignmentRes.assMainTopRes>> getMainTopAssignment(@RequestParam Long userId){
        return assignmentService.getAssMainTopAssignment(userId);
    }

    @Operation(summary = "과제 수정 API",description = "과제 수정 API")
    @PatchMapping("")
    public ApiResponse<String> patchAssignment(@RequestBody AssignmentReq.updateReqDto reqDto){
        return assignmentService.updateAssignment(reqDto);
    }

    @Operation(summary = "과제 삭제 API",description = "과제 삭제 API")
    @DeleteMapping("")
    public ApiResponse<String> deleteAssignment(@RequestParam Long assId) {
        return assignmentService.deleteAssignment(assId);
    }

    @Operation(summary = "과제 상세 조회 API",description = "과제 상세 조회 API")
    @GetMapping("/{assId}")
    public ApiResponse<AssignmentRes.assDetailRes> getAssignmentDetail(@PathVariable Long assId){
        return assignmentService.getAssDetail(assId);
    }

    @Operation(summary = "과제 달별로 조회 API",description = "과제 달별로 조회 API")
    @GetMapping("/month")
    public ApiResponse<List<AssignmentRes.assMonthRes>> getAssignmentMonth(@RequestParam Long userId, @RequestParam String month){
        return assignmentService.getAssMonth(userId, month);
    }

    @Operation(summary = "과제 공유할 친구 조회",description = "과제 공유할 친구 조회")
    @GetMapping("/share")
    public ApiResponse<List<AssignmentRes.assShareRes>> getAssignmentShare(@RequestParam Long userId){
        return assignmentService.getAssShareFriendList(userId);
    }


}
