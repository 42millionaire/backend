package _2._millionaire.oauth.exception;

import _2._millionaire.BaseCustomException;
import _2._millionaire.BaseErrorCode;

public class OauthCustomException extends BaseCustomException {

    public OauthCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
