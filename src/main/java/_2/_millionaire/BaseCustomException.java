package _2._millionaire;

import lombok.RequiredArgsConstructor;

public class BaseCustomException extends RuntimeException{
    private final BaseErrorCode errorCode;

    public BaseCustomException(BaseErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public BaseErrorCode getErrorCode(){
        return errorCode;
    }
    public int getStatusCode(){
        return errorCode.getStatusCode();
    }
}
