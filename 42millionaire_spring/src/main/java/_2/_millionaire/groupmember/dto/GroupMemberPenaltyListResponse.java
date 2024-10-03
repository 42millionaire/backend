package _2._millionaire.groupmember.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GroupMemberPenaltyListResponse(List<GroupMemberPenaltyResponse> members) {
}
