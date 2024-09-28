package _2._millionaire.verification.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SearchVerificationResponse(Long verificationId,
                                         String title,
                                         String memberName,
                                         String content,
                                         List<String> base64Images,
                                         LocalDateTime createdTime,
                                         LocalDateTime updatedTime
                                         ) {
}
