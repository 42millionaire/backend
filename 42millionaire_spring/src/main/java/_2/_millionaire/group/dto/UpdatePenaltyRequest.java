package _2._millionaire.group.dto;

public record UpdatePenaltyRequest(Long groupId,
                                   Integer monthlyPenalty,
                                   Integer weeklyPenalty,
                                   Integer dailyPenalty) {
}
