package org.winners.core.domain.board;

public class BoardPostMock {

    public static BoardPost createPost(long userId, long postId, boolean isSecretPost) {
        Board board = BoardMock.createBoard(1L);
        BoardCategory boardCategory = BoardCategoryMock.createCategory(1L, "카테고리", 1);
        BoardPost boardPost = BoardPost.createPost(userId, board, boardCategory, "게시글 제목", "게시글 내용", isSecretPost);
        boardPost.id = postId;
        return boardPost;
    }

}
