package org.winners.core.domain.board;

public class BoardCategoryMock {

    public static BoardCategory createCategory(long categoryId, String categoryName, int orderNumber) {
        BoardCategory boardCategory = BoardCategory.createCategory(1L, categoryName, orderNumber);
        boardCategory.id = categoryId;
        return boardCategory;
    }

}
