package _2._millionaire.task.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchTaskResponse(Long taskId,
                                 String content,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt,
                                 String status) {
}
