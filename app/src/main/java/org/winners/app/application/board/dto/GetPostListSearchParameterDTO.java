package org.winners.app.application.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPostListSearchParameterDTO {
    private final Long boardId;
    private final String keyword;
}
