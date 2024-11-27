package _2._millionaire.admin.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class AdminCustomException extends BaseCustomException {

    public AdminCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
