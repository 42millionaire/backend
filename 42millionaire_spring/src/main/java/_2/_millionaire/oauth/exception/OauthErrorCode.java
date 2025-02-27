package _2._millionaire.oauth.exception;

import _2._millionaire.BaseErrorCode;

public enum OauthErrorCode implements BaseErrorCode {

    GOOGLE_OAUTH_400_ERROR("Google OAuth exception", 404);

    private final String message;
    private final int statusCode;

    OauthErrorCode(String message, int statusCode) {
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
