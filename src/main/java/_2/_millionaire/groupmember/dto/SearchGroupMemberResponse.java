package _2._millionaire.groupmember.dto;

import lombok.Builder;

@Builder
public record SearchGroupMemberResponse(Long memberId,
                                        String memberName,
                                        String grade) {
}
