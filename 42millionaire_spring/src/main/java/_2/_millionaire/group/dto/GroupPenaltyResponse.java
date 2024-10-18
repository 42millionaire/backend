package _2._millionaire.group.dto;

import lombok.Builder;

@Builder
public record GroupPenaltyResponse(Integer dailyPenalty,
                                   Integer weeklyPenalty,
                                   Integer monthlyPenalty) {
}
