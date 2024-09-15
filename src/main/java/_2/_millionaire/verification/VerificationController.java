package _2._millionaire.verification;

import _2._millionaire.verification.dto.CreateVerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationServiceImpl verificationService;

    @PostMapping("")
    public ResponseEntity<String> createVerification(@RequestBody CreateVerificationRequest createVerificationRequest){
        verificationService.createVerification(createVerificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("인증 제출 되었습니다.");

    }

}
