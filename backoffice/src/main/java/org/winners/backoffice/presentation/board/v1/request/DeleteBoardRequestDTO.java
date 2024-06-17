package org.winners.backoffice.presentation.board.v1.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBoardRequestDTO {

    @Min(value = 1)
    private long boardId;

}
