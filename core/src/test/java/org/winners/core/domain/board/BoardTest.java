package org.winners.core.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    @DisplayName("게시판 생성")
    void create() {
        BoardType boardType = BoardType.NOTICE;
        String boardName = "공지사항";
        Board board = Board.create(boardType, boardName);

        assertThat(board.getType()).isEqualTo(boardType);
        assertThat(board.getName()).isEqualTo(boardName);
    }

    @Test
    @DisplayName("복수 카테고리 추가")
    void addCategories() {
        Board board = BoardMock.craeteBoard();
        List<String> addCategoryNameList = List.of("카테고리1", "카테고리2", "카테고리3");
        board.addCategories(new LinkedHashSet<>(addCategoryNameList));

        assertThat(board.getCategoryList().size()).isEqualTo(addCategoryNameList.size());
        List<BoardCategory> categoryList = board.getCategoryList();
        IntStream.range(0, categoryList.size() - 1).forEach(idx -> {
            BoardCategory boardCategory = categoryList.get(idx);
            assertThat(boardCategory.getName()).isEqualTo(addCategoryNameList.get(idx));
            assertThat(boardCategory.getOrderNumber()).isEqualTo(idx + 1);
        });
    }

    @Test
    @DisplayName("카테고리 추가")
    void addCategory() {
        Board board = BoardMock.craeteBoard();
        List<String> addCategoryNameList = List.of("카테고리1", "카테고리2", "카테고리3");
        addCategoryNameList.forEach(board::addCategory);

        assertThat(board.getCategoryList().size()).isEqualTo(addCategoryNameList.size());
        List<BoardCategory> categoryList = board.getCategoryList();
        IntStream.range(0, categoryList.size() - 1).forEach(idx -> {
            BoardCategory boardCategory = categoryList.get(idx);
            assertThat(boardCategory.getName()).isEqualTo(addCategoryNameList.get(idx));
            assertThat(boardCategory.getOrderNumber()).isEqualTo(idx + 1);
        });
    }

}