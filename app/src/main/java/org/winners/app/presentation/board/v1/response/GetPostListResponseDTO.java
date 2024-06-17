package org.winners.app.presentation.board.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.winners.app.application.board.dto.BoardPostListDTO;
import org.winners.core.config.presentation.PagingResponseDTO;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetPostListResponseDTO extends PagingResponseDTO {

    public GetPostListResponseDTO(Page<BoardPostListDTO> postList) {
        super(postList);
        this.postList = postList.getContent().stream().map(GetPostListResponseDTO.BoardPostDTO::convert).collect(Collectors.toList());
    }

    public static GetPostListResponseDTO convert(Page<BoardPostListDTO> postList) {
        return new GetPostListResponseDTO(postList);
    }

    private final List<GetPostListResponseDTO.BoardPostDTO> postList;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class BoardPostDTO {
        private final long postId;
        private final long userId;
        private final String userName;
        private final long boardId;
        private final String boardName;
        private final long categoryId;
        private final String categoryName;
        private final String postTitle;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
        private final LocalDateTime postCreatedDatetime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
        private final LocalDateTime postUpdatedDatetime;

        public static BoardPostDTO convert(BoardPostListDTO post) {
            return GetPostListResponseDTO.BoardPostDTO.builder()
                .postId(post.getPostId())
                .userId(post.getUserId())
                .userName(post.getUserName())
                .boardId(post.getBoardId())
                .boardName(post.getBoardName())
                .categoryId(post.getCategoryId())
                .categoryName(post.getCategoryName())
                .postTitle(post.getPostTitle())
                .postCreatedDatetime(post.getPostCreatedDatetime())
                .postUpdatedDatetime(post.getPostUpdatedDatetime())
                .build();
        }
    }

}
