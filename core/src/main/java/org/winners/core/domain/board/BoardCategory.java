package org.winners.core.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("게시판 카테고리")
@Getter
@Entity
@Table(name = "board_category")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Comment("게시판 고유번호")
    @Column(name = "board_id", nullable = false, updatable = false)
    private Long boardId;

    @Comment("카테고리명")
    @Column(name = "category_name", length = 50, nullable = false)
    private String name;

    @Comment("정렬순서")
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    public static BoardCategory createCategory(long boardId, String name, int orderNumber) {
        return BoardCategory.builder()
            .boardId(boardId)
            .name(name)
            .orderNumber(orderNumber)
            .build();
    }

    public void updateCategory(String name, int orderNumber) {
        this.name = name;
        this.orderNumber = orderNumber;
    }

}
