package _2._millionaire.task.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchTaskResponse(Long taskId,
                                 String content,
                                 LocalDateTime createdTime,
                                 LocalDateTime updatedTime,
                                 String status) {
}
