package _2._millionaire.member.dto;

import lombok.Builder;

@Builder
public record MemberResponse (Long memberId,
                              String memberName) {
}
