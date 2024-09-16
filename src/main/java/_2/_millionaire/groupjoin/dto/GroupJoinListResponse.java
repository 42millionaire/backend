package _2._millionaire.groupjoin.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupJoinListResponse (List<GroupJoinResponse> groupJoinResponses) {
}
