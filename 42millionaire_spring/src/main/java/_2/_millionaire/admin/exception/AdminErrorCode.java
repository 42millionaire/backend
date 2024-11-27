package _2._millionaire.admin.exception;

import _2._millionaire.BaseErrorCode;

public enum AdminErrorCode implements BaseErrorCode {
    NOT_ADMIN("관리자만 요청 가능합니다.", 403);


    private final String message;
    private final int statusCode;

    AdminErrorCode(String message, int statusCode) {
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
