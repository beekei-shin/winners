package org.winners.core.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    @DisplayName("게시판 생성")
    void createBoard() {
        BoardType boardType = BoardType.NOTICE;
        String boardName = "공지사항";
        Board board = Board.createBoard(boardType, boardName);

        assertThat(board.getType()).isEqualTo(boardType);
        assertThat(board.getName()).isEqualTo(boardName);
    }

    @Test
    @DisplayName("게시판 수정")
    void updateBoard() {
        Board board = BoardMock.createBoard(1L);

        String updateBoardName = "게시판명 수정";
        board.updateBoard(updateBoardName);

        assertThat(board.getName()).isEqualTo(updateBoardName);
    }

    @Test
    @DisplayName("카테고리 복수 등록")
    void saveCategories() {
        Board board = BoardMock.createBoard(1L);
        List<String> addCategoryNameList = List.of("카테고리1", "카테고리2", "카테고리3");
        board.saveCategories(new LinkedHashSet<>(addCategoryNameList));

        List<BoardCategory> categoryList = board.getCategoryList();
        assertThat(categoryList.size()).isEqualTo(addCategoryNameList.size());
        IntStream.range(0, categoryList.size() - 1).forEach(idx -> {
            BoardCategory boardCategory = categoryList.get(idx);
            assertThat(boardCategory.getName()).isEqualTo(addCategoryNameList.get(idx));
            assertThat(boardCategory.getOrderNumber()).isEqualTo(idx + 1);
        });
    }

    @Test
    @DisplayName("카테고리 등록")
    void saveCategory() {
        Board board = BoardMock.createBoard(1L);
        List<String> addCategoryNameList = List.of("카테고리1", "카테고리2", "카테고리3");
        addCategoryNameList.forEach(board::saveCategory);

        assertThat(board.getCategoryList().size()).isEqualTo(addCategoryNameList.size());
        List<BoardCategory> categoryList = board.getCategoryList();
        IntStream.range(0, categoryList.size() - 1).forEach(idx -> {
            BoardCategory boardCategory = categoryList.get(idx);
            assertThat(boardCategory.getName()).isEqualTo(addCategoryNameList.get(idx));
            assertThat(boardCategory.getOrderNumber()).isEqualTo(idx + 1);
        });
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() {
        List<BoardCategory> categoryList = List.of(
            BoardCategoryMock.createCategory(1L, "카테고리1", 1),
            BoardCategoryMock.createCategory(2L, "카테고리2", 2),
            BoardCategoryMock.createCategory(3L, "카테고리3", 3)
        );
        Board board = BoardMock.createHasCategoryBoard(1L, categoryList);

        String updateCategory1 = "카테고리1 수정";
        board.updateCategory(1L, updateCategory1);
        assertThat(board.getCategoryList().get(0).getName()).isEqualTo(updateCategory1);

        String updateCategory2 = "카테고리2 수정";
        board.updateCategory(2L, updateCategory2);
        assertThat(board.getCategoryList().get(1).getName()).isEqualTo(updateCategory2);

        String updateCategory3 = "카테고리3 수정";
        board.updateCategory(3L, updateCategory3);
        assertThat(board.getCategoryList().get(2).getName()).isEqualTo(updateCategory3);
    }

}