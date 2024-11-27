package _2._millionaire.admin.dto;

public record AdminMemberResponse(Long memberId,
                                  String memberName,
                                  boolean isAdmin) {
}
