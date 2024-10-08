package _2._millionaire.task.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SearchPendingTaskListResponse(List<SearchPendingTaskResponse> tasks) {
    @Builder
    public record SearchPendingTaskResponse(Long taskId,
                                            String content,
                                            String memberName,
                                            LocalDate dueDate,
                                            LocalDateTime createdTime,
                                            LocalDateTime updatedTime,
                                            String status){
    }
}
