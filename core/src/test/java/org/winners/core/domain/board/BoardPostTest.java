package org.winners.core.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardPostTest {

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        long userId = 1;
        Board board = BoardMock.createBoard(2L);
        BoardCategory category = BoardCategoryMock.createCategory(3L, "카테고리", 1);
        String title = "제목";
        String contents = "내용";
        boolean isSecretPost = true;

        BoardPost boardPost = BoardPost.createPost(userId, board, category, title, contents, isSecretPost);

        assertThat(boardPost.getUserId()).isEqualTo(userId);
        assertThat(boardPost.getBoardId()).isEqualTo(board.getId());
        assertThat(boardPost.getCategoryId()).isEqualTo(category.getId());
        assertThat(boardPost.getTitle()).isEqualTo(title);
        assertThat(boardPost.getContents()).isEqualTo(contents);
        assertThat(boardPost.isSecretPost()).isEqualTo(isSecretPost);
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost() {
        BoardPost boardPost = BoardPostMock.createPost(1L, 1L, true);

        BoardCategory updateCategory = BoardCategoryMock.createCategory(2L, "카테고리2", 2);
        String updateTitle = "게시글 제목 수정";
        String updateContents = "게시글 제목 수정";
        boolean updateIsSecretPost = false;
        boardPost.updatePost(updateCategory, updateTitle, updateContents, updateIsSecretPost);

        assertThat(boardPost.getCategoryId()).isEqualTo(updateCategory.getId());
        assertThat(boardPost.getTitle()).isEqualTo(updateTitle);
        assertThat(boardPost.getContents()).isEqualTo(updateContents);
        assertThat(boardPost.isSecretPost()).isEqualTo(updateIsSecretPost);
    }


}