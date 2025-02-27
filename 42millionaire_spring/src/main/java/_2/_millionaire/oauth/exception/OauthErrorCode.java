package _2._millionaire.oauth.exception;

import _2._millionaire.BaseErrorCode;

public enum OauthErrorCode implements BaseErrorCode {

    GOOGLE_OAUTH_400_ERROR("Google OAuth exception", 400);

    private final String message;
    private final int statusCode;

    OauthErrorCode(String message, int statusCode) {
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
