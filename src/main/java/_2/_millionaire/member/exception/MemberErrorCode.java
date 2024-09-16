package _2._millionaire.member.exception;

import _2._millionaire.BaseErrorCode;

public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND("존재하지 않는 사용자입니다.", 404),
    INVALID_USER_CREDENTIALS("잘못된 사용자 자격 증명입니다.", 400);

    private final String message;
    private final int statusCode;

    MemberErrorCode(String message, int statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
