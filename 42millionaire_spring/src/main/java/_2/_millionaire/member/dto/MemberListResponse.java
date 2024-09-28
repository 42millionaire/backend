package _2._millionaire.member.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberListResponse (List<MemberResponse> members) {
}
