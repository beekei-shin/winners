package org.winners.backoffice.presentation.board.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.winners.core.config.presentation.validation.ValidEnum;
import org.winners.core.domain.board.BoardType;

import java.util.LinkedHashSet;
import java.util.Set;

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
    @Size(min = 1, max = 10)
    private LinkedHashSet<String> categoryNames;

    public BoardType getBoardType() {
        return BoardType.valueOf(boardType);
    }

}
