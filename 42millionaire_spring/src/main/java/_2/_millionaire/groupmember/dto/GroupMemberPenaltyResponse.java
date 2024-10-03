package _2._millionaire.groupmember.dto;

import lombok.Builder;

@Builder
public record GroupMemberPenaltyResponse(Long memberId,
                                         String memberName,
                                         Long penalty) {
}
