package _2._millionaire.member.dto;

import _2._millionaire.member.Member;
import lombok.Builder;

@Builder
public class MemberResponse {

    private Long memberId;

    private String memberName;

    public MemberResponse(final Member member) {
        this.memberId = member.getId();
        this.memberName = member.getNickName();
    }
}
