package Umc.replendar.apiPayload.code.exception.handler;


import Umc.replendar.apiPayload.code.BaseErrorCode;
import Umc.replendar.apiPayload.code.exception.GeneralException;

public class UserHandler extends GeneralException {

    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
