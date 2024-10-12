package _2._millionaire.task.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record SearchTaskResponse(Long taskId,
                                 String content,
                                 String memberName,
                                 String type,
                                 LocalDate dueDate,
                                 LocalDateTime createdTime,
                                 LocalDateTime updatedTime,
                                 String status) {
}
