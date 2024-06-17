package org.winners.app.presentation.board.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.app.application.board.dto.BoardPostInfoDTO;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetPostInfoResponseDTO {

    public static GetPostInfoResponseDTO convert(BoardPostInfoDTO postInfo) {
        return GetPostInfoResponseDTO.builder()
            .postId(postInfo.getPostId())
            .userId(postInfo.getUserId())
            .userName(postInfo.getUserName())
            .boardId(postInfo.getBoardId())
            .boardName(postInfo.getBoardName())
            .categoryId(postInfo.getCategoryId())
            .categoryName(postInfo.getCategoryName())
            .postTitle(postInfo.getPostTitle())
            .postContents(postInfo.getPostContents())
            .isSecretPost(postInfo.isSecretPost())
            .postCreatedDatetime(postInfo.getPostCreatedDatetime())
            .postUpdatedDatetime(postInfo.getPostUpdatedDatetime())
            .build();
    }

    private final long postId;
    private final long userId;
    private final String userName;
    private final long boardId;
    private final String boardName;
    private final long categoryId;
    private final String categoryName;
    private final String postTitle;
    private final String postContents;
    private final Boolean isSecretPost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
    private final LocalDateTime postCreatedDatetime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
    private final LocalDateTime postUpdatedDatetime;

}
