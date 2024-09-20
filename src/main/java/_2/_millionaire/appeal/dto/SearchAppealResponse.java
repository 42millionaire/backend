package _2._millionaire.appeal.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchAppealResponse(Long taskId,
                                   Long appealId,
                                   Long memberId,
                                   String memberName,
                                   String content,
                                   LocalDateTime createdTime
                                   ) {
}
