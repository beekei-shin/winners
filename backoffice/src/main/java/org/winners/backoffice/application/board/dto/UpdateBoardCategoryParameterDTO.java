package org.winners.backoffice.application.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBoardCategoryParameterDTO {
    private final Long categoryId;
    private final String categoryName;
}
