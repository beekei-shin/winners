package org.winners.core.domain.board;

import java.util.List;

public class BoardMock {

    public static Board createBoard(long id) {
        Board board = Board.createBoard(BoardType.COMMUNITY, "커뮤니티");
        board.id = id;
        return board;
    }

    public static Board createHasCategoryBoard(long id, List<BoardCategory> categoryList) {
        Board board = createBoard(id);
        board.categoryList = categoryList;
        return board;
    }

}
