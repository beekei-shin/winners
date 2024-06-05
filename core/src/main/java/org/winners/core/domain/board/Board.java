package org.winners.core.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.board.service.dto.SaveAndUpdateBoardCategoryParameterDTO;
import org.winners.core.domain.common.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Comment("게시판 유형")
    @Column(name = "board_type", length = 50, nullable = false, updatable = false)
    private BoardType type;

    @Comment("게시판명")
    @Column(name = "board_name", length = 100, nullable = false)
    private String name;

    @Comment("게시판 고유번호")
    @OrderBy("orderNumber ASC")
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST }, orphanRemoval = true)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "FK_board_category_board_id"))
    protected List<BoardCategory> categoryList;

    public static Board createBoard(BoardType boardType, String boardName) {
        return Board.builder()
            .type(boardType)
            .name(boardName)
            .build();
    }

    public void updateBoard(String name) {
        this.name = name;
    }

    public List<BoardCategory> getCategoryList() {
        return Optional.ofNullable(this.categoryList)
            .map(categoryList -> categoryList.stream()
                .sorted(Comparator.comparing(BoardCategory::getOrderNumber))
                .collect(Collectors.toList()))
            .orElseGet(ArrayList::new);
    }

    public Optional<BoardCategory> getCategory(long categoryId) {
        return Optional.ofNullable(this.categoryList)
            .flatMap(categoryList -> categoryList.stream().filter(boardCategory -> boardCategory.getId().equals(categoryId)).findFirst());
    }

    public void saveCategories(LinkedHashSet<String> categoryNames) {
        if (categoryNames.isEmpty()) return;
        if (this.categoryList == null) this.categoryList = new ArrayList<>();
        List<String> categoryNameList = new ArrayList<>(categoryNames);
        IntStream.range(0, categoryNameList.size())
            .forEach(idx -> this.categoryList.add(BoardCategory.createCategory(this.id, categoryNameList.get(idx), idx + 1)));
    }

    public void saveAndUpdateCategories(List<SaveAndUpdateBoardCategoryParameterDTO> updateCategoryList) {
        IntStream.range(0, updateCategoryList.size()).forEach(idx -> {
            SaveAndUpdateBoardCategoryParameterDTO updateCategory = updateCategoryList.get(idx);
            String categoryName = updateCategory.getCategoryName();
            int orderNumber = updateCategory.getOrderNumber();
            Optional.ofNullable(updateCategory.getCategoryId()).ifPresentOrElse(
                categoryId -> this.getCategory(categoryId).ifPresent(category -> category.updateCategory(categoryName, orderNumber)),
                () -> this.categoryList.add(BoardCategory.createCategory(this.id, categoryName, orderNumber)));
        });
    }

    public void deleteCategories(Set<Long> categoryIds) {
        Optional.ofNullable(this.categoryList)
            .ifPresent(categoryList -> categoryList.removeAll(categoryList.stream()
                .filter(category -> categoryIds.contains(category.getId()))
                .toList()));
    }

}
