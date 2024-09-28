package _2._millionaire.task.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchTaskListResponse(List<SearchTaskResponse> tasks) {
}
