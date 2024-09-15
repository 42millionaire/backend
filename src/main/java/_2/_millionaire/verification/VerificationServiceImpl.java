package _2._millionaire.verification;

import _2._millionaire.task.Task;
import _2._millionaire.task.TaskRepository;
import _2._millionaire.verification.dto.SearchVerificationResponse;
import _2._millionaire.task.dto.UpdateTaskStatusRequest;
import _2._millionaire.verification_image.VerificationImage;
import _2._millionaire.verification_image.VerificationImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationServiceImpl implements VerificationService{

    private final VerificationRepository verificationRepository;
    private final VerificationImageRepository verificationImageRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void createVerification(Long taskId, String content, MultipartFile[] images) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 task 입니다."));

        // Task에 대한 Verification 존재 여부 확인
        Verification verification = verificationRepository.findByTask(task)
                .orElse(null);

        if (verification == null) {
            verification = Verification.builder()
                    .task(task)
                    .content(content)
                    .build();
            verificationRepository.save(verification);
        } else {
            verification.setContent(content);
            verificationImageRepository.deleteByVerification(verification);
        }

        // 새로운 이미지 저장
        if (images != null && images.length > 0) {
            List<VerificationImage> verificationImages = new ArrayList<>();
            for (MultipartFile image : images) {
                try {
                    VerificationImage verificationImage = VerificationImage.builder()
                            .image(image.getBytes())
                            .verification(verification)
                            .build();
                    verificationImages.add(verificationImage);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 중 에러가 발생했습니다.");
                }
            }
            verificationImageRepository.saveAll(verificationImages);
        }
    }

    public SearchVerificationResponse searchVerification(Long taskId) {
        Verification verification = verificationRepository.findByTaskId(taskId)
                .orElseThrow(() -> new IllegalArgumentException("해당 task에 대한 인증이 없습니다."));

        List<String> base64Images = verification.getVerificationImages().stream()
                .map(VerificationImage::getBase64Image)
                .collect(Collectors.toList());

        return SearchVerificationResponse.builder()
                .content(verification.getContent())
                .base64Images(base64Images)
                .created_time(verification.getCreatedAt())
                .updated_time(verification.getUpdatedAt())
                .build();
    }
}
