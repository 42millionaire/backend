package _2._millionaire.member.dto;

import lombok.Builder;

@Builder
public record CreateMemberRequest (String nickName,
                                   String email) {
}
