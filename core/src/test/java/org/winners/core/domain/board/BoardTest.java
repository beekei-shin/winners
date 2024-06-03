package org.winners.core.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.domain.board.service.dto.SaveAndUpdateBoardCategoryParameterDTO;

import java.util.*;
import java.util.stream.Collectors;
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
    @DisplayName("카테고리 목록 조회")
    void getCategoryList() {
        List<BoardCategory> categoryList = List.of(
            BoardCategoryMock.createCategory(1L, "카테고리1", 3),
            BoardCategoryMock.createCategory(2L, "카테고리2", 2),
            BoardCategoryMock.createCategory(3L, "카테고리3", 1));
        Board board = BoardMock.createHasCategoryBoard(1L, categoryList);

        List<BoardCategory> boardCategoryList = board.getCategoryList();
        assertThat(boardCategoryList).isEqualTo(categoryList.stream().
            sorted(Comparator.comparing(BoardCategory::getOrderNumber))
            .collect(Collectors.toList()));
    }

    @Test
    @DisplayName("카테고리 조회")
    void getCategory() {
        List<BoardCategory> categoryList = List.of(BoardCategoryMock.createCategory(1L, "카테고리1", 3));
        Board board = BoardMock.createHasCategoryBoard(1L, categoryList);

        Optional<BoardCategory> boardCategoryOpt1 = board.getCategory(1L);
        assertThat(boardCategoryOpt1.isPresent()).isTrue();
        assertThat(boardCategoryOpt1.get()).isEqualTo(categoryList.get(0));

        Optional<BoardCategory> boardCategoryOpt2 = board.getCategory(2L);
        assertThat(boardCategoryOpt2.isPresent()).isFalse();
    }

    @Test
    @DisplayName("카테고리 등록")
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
    @DisplayName("카테고리 등록 및 수정")
    void saveAndUpdateCategories() {
        List<BoardCategory> categoryList = new ArrayList<>() {{
            add(BoardCategoryMock.createCategory(1L, "카테고리1", 3));
            add(BoardCategoryMock.createCategory(2L, "카테고리2", 2));
            add(BoardCategoryMock.createCategory(3L, "카테고리3", 1));
        }};
        Board board = BoardMock.createHasCategoryBoard(1L, categoryList);

        List<SaveAndUpdateBoardCategoryParameterDTO> updateCategoryList = List.of(
            SaveAndUpdateBoardCategoryParameterDTO.builder().categoryId(1L).categoryName("카테고리1 수정").orderNumber(1).build(),
            SaveAndUpdateBoardCategoryParameterDTO.builder().categoryId(2L).categoryName("카테고리2 수정").orderNumber(2).build(),
            SaveAndUpdateBoardCategoryParameterDTO.builder().categoryId(null).categoryName("카테고리4 생성").orderNumber(4).build()
        );
        board.saveAndUpdateCategories(updateCategoryList);

        updateCategoryList.forEach(updateCategory ->
            Optional.ofNullable(updateCategory.getCategoryId())
                .ifPresentOrElse(categoryId ->
                    board.getCategory(categoryId).ifPresent(category -> {
                        assertThat(category.getName()).isEqualTo(updateCategory.getCategoryName());
                        assertThat(category.getOrderNumber()).isEqualTo(updateCategory.getOrderNumber());
                }), () ->
                    board.getCategoryList().stream()
                        .filter(category -> category.getName().equals(updateCategory.getCategoryName()))
                        .findFirst()
                        .ifPresent(category -> {
                            assertThat(category.getName()).isEqualTo(updateCategory.getCategoryName());
                            assertThat(category.getOrderNumber()).isEqualTo(updateCategory.getOrderNumber());
                        })));
    }

    @Test
    @DisplayName("카테고리 삭제")
    public void deleteCategories() {
        List<BoardCategory> categoryList = new ArrayList<>() {{
            add(BoardCategoryMock.createCategory(1L, "카테고리1", 3));
            add(BoardCategoryMock.createCategory(2L, "카테고리2", 2));
            add(BoardCategoryMock.createCategory(3L, "카테고리3", 1));
        }};
        Board board = BoardMock.createHasCategoryBoard(1L, categoryList);

        board.deleteCategories(Set.of(1L, 2L));

        assertThat(board.getCategoryList().size()).isEqualTo(1);
        assertThat(board.getCategoryList().get(0).getId()).isEqualTo(3L);
    }

}