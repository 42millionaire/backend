package _2._millionaire.appeal.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class AppealCustomException extends BaseCustomException {
    public AppealCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
