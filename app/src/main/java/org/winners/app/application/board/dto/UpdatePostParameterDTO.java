package org.winners.app.application.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePostParameterDTO {
    private final long categoryId;
    private final String postTitle;
    private final String postContents;
    private final boolean isSecretPost;
}
