package _2._millionaire.verification.exception;

import _2._millionaire.BaseErrorCode;

public enum VerificationErrorCode implements BaseErrorCode {
    IMAGE_SAVE_ERROR("이미지 저장중 오류가 발생하였습니다.", 400),
    VERIFICATION_NOT_FOUND("인증 내용을 찾을 수 없습니다.", 404);

    private final String message;
    private final int statusCode;

    VerificationErrorCode(String message, int statusCode) {
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
