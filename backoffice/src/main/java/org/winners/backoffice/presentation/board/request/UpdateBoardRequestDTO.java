package org.winners.backoffice.presentation.board.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.domain.board.service.BoardDomainService;
import org.winners.core.domain.board.service.dto.SaveAndUpdateBoardCategoryParameterDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBoardRequestDTO {

    @NotNull
    @Min(value = 1)
    private Long boardId;

    @NotBlank
    private String boardName;

    @Valid
    @NotNull
    @Size(min = 1, max = BoardDomainService.BOARD_CATEGORY_MAX_COUNT)
    private List<UpdateCategoryRequestDTO> categoryList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateCategoryRequestDTO {

        @Min(value = 1)
        private Long categoryId;

        @NotBlank
        private String categoryName;

        @NotNull
        @Min(value = 1)
        @Max(value = BoardDomainService.BOARD_CATEGORY_MAX_COUNT)
        private Integer orderNumber;

    }

    public List<SaveAndUpdateBoardCategoryParameterDTO> convertParameter() {
        return this.categoryList.stream()
            .map(category -> SaveAndUpdateBoardCategoryParameterDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .orderNumber(category.getOrderNumber())
                .build())
            .collect(Collectors.toList());
    }

}
