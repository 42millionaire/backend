package _2._millionaire.task.dto;

public record CreateTaskRequest(Long groupId,
                                Long memberId,
                                String type,
                                String content) {
}
