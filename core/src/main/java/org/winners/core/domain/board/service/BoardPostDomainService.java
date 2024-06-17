package org.winners.core.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotAccessDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.BoardPost;
import org.winners.core.domain.board.BoardPostRepository;

@Service
@RequiredArgsConstructor
public class BoardPostDomainService {

    public static final String SECRET_POST_TITLE = "비밀글입니다.";

    private final BoardPostRepository boardPostRepository;

    public void possibleViewPostCheck(BoardPost boardPost, long viewUserId) {
        if (boardPost.isSecretPost() && boardPost.getUserId() != viewUserId)
            throw new NotAccessDataException(ExceptionMessageType.NOT_ACCESS_BOARD_POST);
    }

    public void possibleUpdatePostCheck(BoardPost boardPost, long updateUserId) {
        if (boardPost.getUserId() != updateUserId)
            throw new NotAccessDataException(ExceptionMessageType.NOT_ACCESS_BOARD_POST);
    }

    public BoardPost getPost(long postId) {
        return boardPostRepository.findById(postId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_POST));
    }

}
