package _2._millionaire.appeal.exception;

import _2._millionaire.BaseErrorCode;

public enum AppealErrorCode implements BaseErrorCode {
    APPEAL_NOT_FOUND("존재하지 않는 이의입니다.", 404);

    private final String message;
    private final int statusCode;

    AppealErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
