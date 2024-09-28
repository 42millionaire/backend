package _2._millionaire.groupjoin.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GroupJoinResponse (Long memberId,
                                 String name,
                                 LocalDateTime createdTime) {
}
