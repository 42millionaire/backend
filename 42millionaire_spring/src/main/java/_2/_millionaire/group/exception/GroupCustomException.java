package _2._millionaire.group.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class GroupCustomException extends BaseCustomException {
    public GroupCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
