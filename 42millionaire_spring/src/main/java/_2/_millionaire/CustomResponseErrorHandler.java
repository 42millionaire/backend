package _2._millionaire;
import _2._millionaire.oauth.exception.OauthCustomException;
import _2._millionaire.oauth.exception.OauthErrorCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // 응답 상태 코드가 400 이상인 경우 오류로 처리
        return (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // 여기에서 커스텀 예외를 던지거나 처리
        throw new OauthCustomException(OauthErrorCode.GOOGLE_OAUTH_400_ERROR);
    }
}
