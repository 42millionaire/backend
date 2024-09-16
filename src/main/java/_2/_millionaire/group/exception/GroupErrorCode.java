package _2._millionaire.group.exception;

import _2._millionaire.BaseErrorCode;

public enum GroupErrorCode implements BaseErrorCode {
    GROUP_NOT_FOUND("존재하지 않는 그룹입니다.", 404);

    private final String message;
    private final int statusCode;

    GroupErrorCode(String message, int statusCode) {
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