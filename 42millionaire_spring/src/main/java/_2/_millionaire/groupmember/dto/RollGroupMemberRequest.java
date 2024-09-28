package _2._millionaire.groupmember.dto;

public record RollGroupMemberRequest (Long groupId,
                                      Long memberId,
                                      String role) {
}
