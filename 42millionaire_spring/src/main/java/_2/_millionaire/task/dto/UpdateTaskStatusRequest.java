package _2._millionaire.task.dto;

public record UpdateTaskStatusRequest(Long taskId,
                                      String status) {
}
