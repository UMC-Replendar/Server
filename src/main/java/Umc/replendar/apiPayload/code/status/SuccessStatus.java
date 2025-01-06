package Umc.replendar.apiPayload.code.status;

import Umc.replendar.apiPayload.code.BaseCode;
import Umc.replendar.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK, "COMMON200", "성공!"),

    // 성공 관련 응답
    SUCCESS_GET_NOTICE_LIST(HttpStatus.OK, "COMMON2001", "공지사항 목록 읽기 성공"),
    SUCCESS_FETCH_NOTICE_UPDATE(HttpStatus.OK, "COMMON2002", "공지사항 수정 성공"),
    SUCCESS_GET_NOTICE(HttpStatus.OK, "COMMON2002", "공지사항 읽기 성공");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

//    @Override
//    public ReasonDTO getReason() {
//        return ReasonDTO.builder()
//                .message(message)
//                .code(code)
//                .isSuccess(true)
//                .build();
//    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}