package org.winners.core.domain.board.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavePostParameterDTO {
    private final long categoryId;
    private final String postTitle;
    private final String postContents;
    private final boolean isSecretPost;
}
