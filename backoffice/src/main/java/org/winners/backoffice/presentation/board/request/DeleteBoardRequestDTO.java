package org.winners.backoffice.presentation.board.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBoardRequestDTO {

    @NotNull
    @Min(value = 1)
    private Long boardId;

}
