package _2._millionaire.verification.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class VerificationCustomException extends BaseCustomException {
    public VerificationCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
