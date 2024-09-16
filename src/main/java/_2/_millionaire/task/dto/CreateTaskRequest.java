package _2._millionaire.task.dto;

import java.time.LocalDate;

public record CreateTaskRequest(Long groupId,
                                Long memberId,
                                LocalDate dueDate,
                                String type,
                                String content) {
}
