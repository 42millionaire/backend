package _2._millionaire.group.dto;

import lombok.Builder;

@Builder
public record GroupResponse (Long groupId,
                            String groupName) {
}
