package _2._millionaire.oauth.dto;

import lombok.Builder;

@Builder
public record RedirectResponse(String url) {
}
