package Umc.replendar.apiPayload.code.status;

import Umc.replendar.apiPayload.code.BaseErrorCode;
import Umc.replendar.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 에러 예시
    FAIL_OOOOO(HttpStatus.BAD_REQUEST, "FAIL", "실패하였습니다."),

    // 토큰 관련 에러
    JWT_AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "JWT4001", "권한이 없습니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT4002", "유효하지 않은 토큰입니다."),
    JWT_EMPTY(HttpStatus.UNAUTHORIZED, "JWT4003", "JWT 토큰을 넣어주세요."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT4004", "만료된 토큰입니다."),
    JWT_REFRESHTOKEN_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "JWT4005", "RefreshToken이 일치하지 않습니다."),

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // token
    TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "TOKEN_400_1", "헤더에 토큰이 존재하지 않음"),
    TOKEN_UNABLE_TO_EXTRACT(HttpStatus.BAD_REQUEST, "TOKEN_400_2", "토큰을 추출할 수 없음"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_401_1", "토큰이 만료됨"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN_401_2", "토큰이 유효하지 않음"),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "TOKEN_401_3", "지원하지 않는 토큰 타입임"),
    ID_TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "TOKEN_400_3", "id_token 이 만료되었거나 유효하지 않음."),

    // Notice
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE404", "공지사항을 찾을 수 없습니다."),
    NOTICE_NOT_UPDATE(HttpStatus.BAD_REQUEST, "NOTICE400", "공지사항 수정 실패"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

//    @Override
//    public ErrorReasonDTO getReason() {
//        return ErrorReasonDTO.builder()
//                .message(message)
//                .code(code)
//                .isSuccess(false)
//                .build();
//    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}