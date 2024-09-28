package _2._millionaire.groupmember.exception;

import _2._millionaire.BaseErrorCode;

public enum GroupMemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_IN_GROUP("해당 맴버가 그룹에 존재하지 않습니다.", 404);

    private final String message;
    private final int statusCode;

    GroupMemberErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
