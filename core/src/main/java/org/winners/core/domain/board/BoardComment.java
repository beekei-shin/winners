package org.winners.core.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("게시판 댓글")
@Getter
@Entity
@Table(name = "board_comment")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("회원 고유번호")
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Comment("게시글 고유번호")
    @Column(name = "post_id", nullable = false, updatable = false)
    private Long postId;

    @Comment("댓글 고유번호")
    @Column(name = "comment_id", updatable = false)
    private Long commentId;

    @Comment("내용")
    @Column(name = "contents", length = 500, nullable = false)
    private String contents;

}
