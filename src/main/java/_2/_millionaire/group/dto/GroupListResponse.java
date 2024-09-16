package _2._millionaire.group.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupListResponse (List<GroupResponse> groupResponses) {
}
