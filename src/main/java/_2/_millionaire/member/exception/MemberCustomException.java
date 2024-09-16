package _2._millionaire.member.exception;

import _2._millionaire.BaseCustomException;

public class MemberCustomException extends BaseCustomException {
    public MemberCustomException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
