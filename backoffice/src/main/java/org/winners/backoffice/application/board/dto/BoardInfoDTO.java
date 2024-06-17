package org.winners.backoffice.application.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.board.Board;
import org.winners.core.domain.board.BoardCategory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BoardInfoDTO {

    private final long boardId;
    private final String boardName;
    private final List<BoardCategoryDTO> categoryList;

    public static BoardInfoDTO create(Board board) {
        return BoardInfoDTO.builder()
            .boardId(board.getId())
            .boardName(board.getName())
            .categoryList(board.getCategoryList().stream().map(BoardCategoryDTO::create).collect(Collectors.toList()))
            .build();
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class BoardCategoryDTO {
        private final long categoryId;
        private final String categoryName;
        public static BoardCategoryDTO create(BoardCategory boardCategory) {
            return BoardCategoryDTO.builder()
                .categoryId(boardCategory.getId())
                .categoryName(boardCategory.getName())
                .build();
        }
    }

}
