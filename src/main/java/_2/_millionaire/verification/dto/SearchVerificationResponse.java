package _2._millionaire.verification.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SearchVerificationResponse(Long verificationId,
                                         String content,
                                         List<String> base64Images,
                                         LocalDateTime created_time,
                                         LocalDateTime updated_time
                                         ) {
}