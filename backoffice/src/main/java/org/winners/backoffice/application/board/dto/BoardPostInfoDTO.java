package org.winners.backoffice.application.board.dto;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.config.querydsl.QuerydslSelectDTO;

import java.time.LocalDateTime;

import static org.winners.core.domain.board.QBoard.board;
import static org.winners.core.domain.board.QBoardCategory.boardCategory;
import static org.winners.core.domain.board.QBoardPost.boardPost;
import static org.winners.core.domain.user.QUser.user;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardPostInfoDTO implements QuerydslSelectDTO {

    private long postId;
    private long userId;
    private String userName;
    private long boardId;
    private String boardName;
    private long categoryId;
    private String categoryName;
    private String postTitle;
    private String postContents;
    private LocalDateTime postCreatedDatetime;
    private LocalDateTime postUpdatedDatetime;

    @Override
    public ConstructorExpression constructor() {
        return Projections.constructor(
            BoardPostInfoDTO.class,
            boardPost.id,
            user.id, user.name,
            board.id, board.name,
            boardCategory.id, boardCategory.name,
            boardPost.title,
            boardPost.contents,
            boardPost.createdDatetime,
            boardPost.updatedDatetime
        );
    }

}
