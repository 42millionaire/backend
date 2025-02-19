package _2._millionaire.oauth;

import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
import _2._millionaire.oauth.dto.LoginMemberResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    private final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Value("${client_id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${client_secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${redirect_url}")
    private String LOGIN_REDIRECT_URL;

    private final MemberRepository memberRepository;
    public LoginMemberResponse signInOrSignUp(String accessCode, HttpServletRequest sr, HttpServletResponse res) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", accessCode);
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", LOGIN_REDIRECT_URL);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            String accessToken = extractAccessToken(responseEntity.getBody());
            Member member = getOrCreateMember(accessToken);
            HttpSession session = sr.getSession();
            session.setMaxInactiveInterval(30 * 60);
            session.setAttribute("user", member);
            res.addHeader("Set-Cookie", "JSESSION="+ session.getId() + "; HttpOnly; Path=/; Secure; SameSite=None");
            log.info("세션 ID: " + session.getId());
            log.info("member ID: " + member.getId());

            return LoginMemberResponse.builder()
                    .memberId(member.getId())
                    .memberName(member.getName())
                    .build();
        }
        return null;
    }

    private String extractAccessToken(String body) {
        try {
            // ObjectMapper를 사용하여 responseBody를 JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(body);

            // "access_token" 키의 값을 추출
            return root.path("access_token").asText();
        } catch (Exception e) {
            // 파싱 오류 처리
            throw new RuntimeException("엑세스 토큰을 추출하는 데 실패했습니다.", e);
        }
    }

    public String redirect() {
        // UriComponentsBuilder를 사용하여 URL 생성 및 인코딩
        URI uri = UriComponentsBuilder.fromHttpUrl(GOOGLE_AUTH_URL)
                .queryParam("client_id", GOOGLE_CLIENT_ID)
                .queryParam("redirect_uri", LOGIN_REDIRECT_URL)
                .queryParam("response_type", "code")
                .queryParam("scope", "profile email")  // 자동으로 인코딩됨
                .queryParam("flowName", "GeneralOAuthFlow")
                .build()
                .toUri();

        return uri.toString();
    }


    private Member getOrCreateMember(String accessToken) {
        // 사용자 정보를 가져오기 위한 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(GOOGLE_USER_INFO_URL, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = response.getBody();
            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");

            // 이메일로 사용자를 찾기
            Member member = memberRepository.findByEmail(email);
            if (member == null) {
                member = new Member(name, email);
                memberRepository.save(member);
                log.info("[" + name + "]이 회원가입 되었습니다.");
            }

            return member; // 로그인 또는 새로 생성한 사용자 반환
        }
        throw new RuntimeException("사용자 정보를 가져오는 데 실패했습니다.");
    }
}

