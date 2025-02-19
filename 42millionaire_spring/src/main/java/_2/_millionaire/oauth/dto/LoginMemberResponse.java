package _2._millionaire.oauth.dto;

import lombok.Builder;

@Builder
public record LoginMemberResponse(Long memberId, String memberName, String sessionId) {
}
