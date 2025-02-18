package _2._millionaire.group.exception;

import _2._millionaire.BaseErrorCode;

public enum GroupErrorCode implements BaseErrorCode {
    GROUP_NOT_FOUND("존재하지 않는 그룹입니다.", 404),
    NOT_ADMIN_OF_GROUP("그룹의 관리자가 아닙니다.", 403),

    ALREADY_JOIN_REQUEST_MEMBER("이미 해당 그룹에 가입 요청을 했습니다.", 409);

    private final String message;
    private final int statusCode;

    GroupErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
