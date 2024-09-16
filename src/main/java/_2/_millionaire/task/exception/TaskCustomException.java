package _2._millionaire.task.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class TaskCustomException extends BaseCustomException {
    public TaskCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
