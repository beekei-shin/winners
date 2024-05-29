package org.winners.core.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

import javax.swing.text.html.Option;
import java.util.*;

@Comment("게시판")
@Getter
@Entity
@Table(name = "board")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("게시판 유형")
    @Column(name = "board_type", length = 50, nullable = false, updatable = false)
    private BoardType type;

    @Comment("게시판명")
    @Column(name = "board_name", length = 100, nullable = false)
    private String name;

    @OrderBy("orderNumber ASC")
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "board_id")
    private List<BoardCategory> categoryList;

    protected void setMockId(long id) {
        this.id = id;
    }

    public static Board create(BoardType boardType, String boardName) {
        return Board.builder()
            .type(boardType)
            .name(boardName)
            .build();
    }

    public void addCategories(LinkedHashSet<String> categoryNames) {
        categoryNames.forEach(this::addCategory);
    }

    public void addCategory(String categoryName) {
        if (this.categoryList == null) this.categoryList = new ArrayList<>();
        this.categoryList.add(BoardCategory.create(this.id, categoryName, this.categoryList.size()+ 1));
    }

}
