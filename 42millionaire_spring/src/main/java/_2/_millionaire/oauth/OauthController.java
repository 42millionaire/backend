package _2._millionaire.oauth;

import _2._millionaire.oauth.dto.LoginMemberResponse;
import _2._millionaire.oauth.dto.RedirectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OAuthService oAuthService;

//    @GetMapping("/api/oauth/google")
//    public ResponseEntity<RedirectResponse> googleLogin(HttpServletResponse response) throws IOException {
//        String redirectUrl = oAuthService.redirect();
//        log.info(redirectUrl);
//        return ResponseEntity.status(HttpStatus.OK).body(RedirectResponse.builder().url(redirectUrl).build());
//    }

    //임시 auth login API
    @GetMapping("/api/oauth/google")
    public ResponseEntity<RedirectResponse> googleLogin(HttpServletRequest request) {
        String clientHost = request.getHeader("Referer"); // 클라이언트가 접속한 도메인
        String clientIp = request.getRemoteAddr(); // 클라이언트의 IP 주소

        boolean isLocal = isLocalRequest(clientHost, clientIp);

        // 로컬 환경이면 localhost 리다이렉트, 배포 환경이면 도메인 리다이렉트
        String redirectUrl = isLocal
                ? "http://localhost:5173/auth/google/callback"
                : "https://42millionaire.phan.kr/auth/google/callback";

        String finalRedirectUrl = oAuthService.redirect(redirectUrl);
        return ResponseEntity.ok(RedirectResponse.builder().url(finalRedirectUrl).build());
    }

    private boolean isLocalRequest(String host, String ip) {
        return host.contains("localhost") || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip);
    }

    @GetMapping("/api/login/oauth2/code/google")
    public ResponseEntity<LoginMemberResponse> successGoogleLogin(@RequestParam("code") String accessCode, HttpServletRequest servletRequest){
        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.signInOrSignUp(accessCode, servletRequest));
    }
}

