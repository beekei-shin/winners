package org.winners.core.domain.board;

import java.util.List;

public class BoardMock {

    public static Board createBoard(long id, BoardType boardType) {
        Board board = Board.createBoard(boardType, "게시판명");
        board.id = id;
        return board;
    }

    public static Board createBoard(long id) {
        return BoardMock.createBoard(id, BoardType.NOTICE);
    }

    public static Board createHasCategoryBoard(long id, List<BoardCategory> categoryList) {
        Board board = createBoard(id);
        board.categoryList = categoryList;
        return board;
    }

}
