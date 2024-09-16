package _2._millionaire.groupmember.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class GroupMemberCustomException extends BaseCustomException {
    public GroupMemberCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
