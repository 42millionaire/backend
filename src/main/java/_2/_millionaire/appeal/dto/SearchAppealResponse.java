package _2._millionaire.appeal.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchAppealResponse(Long taskId,
                                   Long memberId,
                                   String memberName,
                                   String content,
                                   LocalDateTime createTime
                                   ) {
}
