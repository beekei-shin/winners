package org.winners.app.application.board.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.winners.app.application.board.BoardPostAppService;
import org.winners.app.application.board.dto.BoardPostInfoDTO;
import org.winners.app.application.board.dto.BoardPostListDTO;
import org.winners.app.application.board.dto.GetPostListSearchParameterDTO;
import org.winners.app.application.board.dto.UpdatePostParameterDTO;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.querydsl.QuerydslRepository;
import org.winners.core.domain.board.Board;
import org.winners.core.domain.board.BoardCategory;
import org.winners.core.domain.board.BoardPost;
import org.winners.core.domain.board.BoardPostRepository;
import org.winners.core.domain.board.service.BoardCategoryDomainService;
import org.winners.core.domain.board.service.BoardDomainService;
import org.winners.core.domain.board.service.BoardPostDomainService;
import org.winners.core.domain.board.service.dto.SavePostParameterDTO;

import static org.winners.core.domain.board.QBoard.board;
import static org.winners.core.domain.board.QBoardCategory.boardCategory;
import static org.winners.core.domain.board.QBoardPost.boardPost;
import static org.winners.core.domain.user.QUser.user;

@Component
@RequiredArgsConstructor
public class BoardPostAppServiceV1 implements BoardPostAppService {

    private final QuerydslRepository querydslRepository;
    private final BoardDomainService boardDomainService;
    private final BoardCategoryDomainService boardCategoryDomainService;
    private final BoardPostDomainService boardPostDomainService;
    private final BoardPostRepository boardPostRepository;

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
            .optionalWhere(boardPost.title, searchParameter.getKeyword())
            .orderBy(boardPost.id.desc())
            .getPage(pageRequest);
    }

    @Override
    public BoardPostInfoDTO getPostInfo(long postId, long viewUserId) {
        return querydslRepository
            .select(BoardPostInfoDTO.class)
            .from(boardPost)
            .leftJoin(board, board.id.eq(boardPost.boardId))
            .leftJoin(boardCategory, boardCategory.id.eq(boardPost.categoryId))
            .leftJoin(user, user.id.eq(boardPost.userId))
            .where(boardPost.id, postId)
            .getRow()
            .filter(postInfo -> {
                BoardPost boardPost = boardPostDomainService.getPost(postId);
                boardPostDomainService.possibleViewPostCheck(boardPost, viewUserId);
                return true;
            })
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_POST));
    }

    @Override
    public void savePost(long userId, SavePostParameterDTO parameter) {
        Board board = boardDomainService.getBoard(parameter.getBoardId());
        BoardCategory boardCategory = boardCategoryDomainService.getCategory(parameter.getCategoryId());
        boardPostRepository.save(BoardPost.createPost(
            userId, board, boardCategory,
            parameter.getPostTitle(), parameter.getPostContents(), parameter.isSecretPost()
        ));
    }

    @Override
    public void updatePost(long userId, long postId, UpdatePostParameterDTO parameter) {
        BoardPost boardPost = boardPostDomainService.getPost(postId);
        boardPostDomainService.possibleUpdatePostCheck(boardPost, userId);

        BoardCategory boardCategory = boardCategoryDomainService.getCategory(parameter.getCategoryId());

        boardPost.updatePost(boardCategory, parameter.getPostTitle(), parameter.getPostContents(), parameter.isSecretPost());
    }

}
