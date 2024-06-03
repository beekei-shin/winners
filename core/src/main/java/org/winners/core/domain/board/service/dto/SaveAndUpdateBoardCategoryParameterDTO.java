package org.winners.core.domain.board.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveAndUpdateBoardCategoryParameterDTO {
    private final Long categoryId;
    private final String categoryName;
    private final int orderNumber;
}
