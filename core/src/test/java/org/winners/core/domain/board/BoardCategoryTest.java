package org.winners.core.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardCategoryTest {

    @Test
    @DisplayName("카테고리 생성")
    void createCategory() {
        long boardId = 1L;
        String categoryName = "카테고리1";
        int orderNumber = 2;

        BoardCategory category = BoardCategory.createCategory(boardId, categoryName, orderNumber);

        assertThat(category.getBoardId()).isEqualTo(boardId);
        assertThat(category.getName()).isEqualTo(categoryName);
        assertThat(category.getOrderNumber()).isEqualTo(orderNumber);
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() {
        BoardCategory category = BoardCategoryMock.createCategory(1L, "카테고리1", 1);

        String updateCategoryName = "카테고리1 수정";
        int updateOrderNumber = 2;
        category.updateCategory(updateCategoryName, updateOrderNumber);

        assertThat(category.getName()).isEqualTo(updateCategoryName);
        assertThat(category.getOrderNumber()).isEqualTo(updateOrderNumber);
    }

}