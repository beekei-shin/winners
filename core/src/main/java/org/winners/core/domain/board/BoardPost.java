package org.winners.core.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("게시판 게시글")
@Getter
@Entity
@Table(name = "board_post")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Comment("회원 고유번호")
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Comment("게시판 고유번호")
    @Column(name = "board_id", nullable = false, updatable = false)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Comment("게시판 유형")
    @Column(name = "board_type", length = 50, nullable = false, updatable = false)
    private BoardType boardType;

    @Comment("카테고리 고유번호")
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Comment("제목")
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Comment("내용")
    @Column(name = "contents", length = 500, nullable = false)
    private String contents;

    @Comment("비밀글 여부")
    @Column(name = "is_secret_post", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isSecretPost;

    public static BoardPost createPost(long userId, Board board, BoardCategory category,
                                       String title, String contents, boolean isSecretPost) {
        return BoardPost.builder()
            .userId(userId)
            .boardId(board.getId())
            .boardType(board.getType())
            .categoryId(category.getId())
            .title(title)
            .contents(contents)
            .isSecretPost(isSecretPost)
            .build();
    }

    public void updatePost(BoardCategory category, String title, String contents, boolean isSecretPost) {
        this.categoryId = category.getId();
        this.title = title;
        this.contents = contents;
        this.isSecretPost = isSecretPost;
    }

}
