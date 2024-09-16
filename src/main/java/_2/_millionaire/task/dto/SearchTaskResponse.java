package _2._millionaire.task.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record SearchTaskResponse(Long taskId,
                                 String content,
                                 LocalDate dueDate,
                                 LocalDateTime createdTime,
                                 LocalDateTime updatedTime,
                                 String status) {
}
