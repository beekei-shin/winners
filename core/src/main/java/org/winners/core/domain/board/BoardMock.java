package org.winners.core.domain.board;

public class BoardMock {

    public static Board craeteBoard() {
        Board board = Board.create(BoardType.COMMUNITY, "커뮤니티");
        board.setMockId(1);
        return board;
    }

}
