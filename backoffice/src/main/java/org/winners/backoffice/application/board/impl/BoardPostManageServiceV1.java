package org.winners.backoffice.application.board.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.winners.backoffice.application.board.BoardPostManageService;
import org.winners.backoffice.application.board.dto.BoardPostInfoDTO;
import org.winners.backoffice.application.board.dto.BoardPostListDTO;
import org.winners.backoffice.application.board.dto.GetPostListSearchParameterDTO;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.querydsl.QuerydslRepository;

import static org.winners.core.domain.board.QBoard.board;
import static org.winners.core.domain.board.QBoardCategory.boardCategory;
import static org.winners.core.domain.board.QBoardPost.boardPost;
import static org.winners.core.domain.user.QUser.user;

@Component
@RequiredArgsConstructor
public class BoardPostManageServiceV1 implements BoardPostManageService {

    private final QuerydslRepository querydslRepository;

    @Override
    public Page<BoardPostListDTO> getPostList(GetPostListSearchParameterDTO searchParameter, PageRequest pageRequest) {
        return querydslRepository
            .select(BoardPostListDTO.class)
            .countSelect(boardPost.id.countDistinct())
            .from(boardPost)
            .leftJoin(board, board.id.eq(boardPost.boardId))
            .leftJoin(boardCategory, boardCategory.id.eq(boardPost.categoryId))
            .leftJoin(user, user.id.eq(boardPost.userId))
            .optionalWhere(board.id, searchParameter.getBoardId())
            .optionalLike(boardPost.title, searchParameter.getKeyword())
            .orderBy(boardPost.id.desc())
            .getPage(pageRequest);
    }

    @Override
    public BoardPostInfoDTO getPostInfo(long postId) {
        return querydslRepository
            .select(BoardPostInfoDTO.class)
            .from(boardPost)
            .leftJoin(board, board.id.eq(boardPost.boardId))
            .leftJoin(boardCategory, boardCategory.id.eq(boardPost.categoryId))
            .leftJoin(user, user.id.eq(boardPost.userId))
            .where(boardPost.id, postId)
            .getRow()
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_POST));
    }

}
