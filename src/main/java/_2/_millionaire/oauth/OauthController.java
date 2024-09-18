package _2._millionaire.oauth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void googleLogin(HttpServletResponse response) throws IOException {
        String redirectUrl = oAuthService.redirect();
        log.info(redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<String> successGoogleLogin(@RequestParam("code") String accessCode){
        return oAuthService.getGoogleAccessToken(accessCode);
    }
}

