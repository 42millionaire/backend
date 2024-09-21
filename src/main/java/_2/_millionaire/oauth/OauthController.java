package _2._millionaire.oauth;

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

    @GetMapping("/oauth/google")
    public ResponseEntity<String> googleLogin(HttpServletResponse response) throws IOException {
        String redirectUrl = oAuthService.redirect();
        log.info(redirectUrl);
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        response.sendRedirect(redirectUrl);
        return ResponseEntity.status(HttpStatus.OK).body(redirectUrl);
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<String> successGoogleLogin(@RequestParam("code") String accessCode){
        return oAuthService.getGoogleAccessToken(accessCode);
    }
}

