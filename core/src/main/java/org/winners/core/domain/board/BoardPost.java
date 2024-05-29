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
    private Long id;

    @Comment("회원 고유번호")
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Comment("게시판 고유번호")
    @Column(name = "board_id", nullable = false, updatable = false)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Comment("게시판 유형")
    @Column(name = "board_type", length = 50, nullable = false, updatable = false)
    private BoardType type;

    @Comment("카테고리 고유번호")
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Comment("제목")
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Comment("내용")
    @Column(name = "contents", length = 500, nullable = false)
    private String contents;

}
