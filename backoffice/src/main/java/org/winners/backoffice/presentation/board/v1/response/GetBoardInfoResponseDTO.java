package org.winners.backoffice.presentation.board.v1.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.backoffice.application.board.dto.BoardInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetBoardInfoResponseDTO {

    private final long boardId;
    private final String boardName;
    private final List<BoardCategoryDTO> categoryList;

    public static GetBoardInfoResponseDTO convert(BoardInfoDTO boardInfo) {
        return GetBoardInfoResponseDTO.builder()
            .boardId(boardInfo.getBoardId())
            .boardName(boardInfo.getBoardName())
            .categoryList(boardInfo.getCategoryList().stream().map(BoardCategoryDTO::convert).collect(Collectors.toList()))
            .build();
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class BoardCategoryDTO {

        private final long categoryId;
        private final String categoryName;

        public static BoardCategoryDTO convert(BoardInfoDTO.BoardCategoryDTO boardCategoryDTO) {
            return BoardCategoryDTO.builder()
                .categoryId(boardCategoryDTO.getCategoryId())
                .categoryName(boardCategoryDTO.getCategoryName())
                .build();
        }
    }

}
