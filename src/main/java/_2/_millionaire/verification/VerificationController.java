package _2._millionaire.verification;

import _2._millionaire.verification.dto.SearchVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationServiceImpl verificationService;

    @PostMapping("")
    public ResponseEntity<String> createVerification(@RequestParam("taskId") Long taskId,
                                                     @RequestParam("content") String content,
                                                     @RequestParam(value = "images", required = false) MultipartFile[] images){
        verificationService.createVerification(taskId, content, images);
        return ResponseEntity.status(HttpStatus.CREATED).body("인증 제출 되었습니다.");

    }
    @GetMapping("/{taskId}")
    public ResponseEntity<SearchVerificationResponse> searchVerification(@PathVariable("taskId") Long taskId){
        return ResponseEntity.status(HttpStatus.OK).body(verificationService.searchVerification(taskId));
    }
}
