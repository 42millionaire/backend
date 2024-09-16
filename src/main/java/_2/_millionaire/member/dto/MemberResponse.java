package _2._millionaire.member.dto;

import _2._millionaire.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class MemberResponse {

    private Long memberId;

    private String memberName;

    public MemberResponse(final Member member) {
        this.memberId = member.getId();
        this.memberName = member.getNickName();
    }
}
