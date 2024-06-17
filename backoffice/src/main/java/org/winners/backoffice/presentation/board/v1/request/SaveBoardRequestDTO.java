package org.winners.backoffice.presentation.board.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.winners.core.config.presentation.validation.ValidEnum;
import org.winners.core.domain.board.BoardType;
import org.winners.core.domain.board.service.BoardDomainService;

import java.util.LinkedHashSet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveBoardRequestDTO {

    @NotBlank
    @ValidEnum(enumClass = BoardType.class)
    private String boardType;

    @NotBlank
    @Length(max = 30)
    private String boardName;

    @NotNull
    @Size(min = 1, max = BoardDomainService.BOARD_CATEGORY_MAX_COUNT)
    private LinkedHashSet<String> categoryNames;

    public BoardType getBoardType() {
        return BoardType.valueOf(boardType);
    }

}
