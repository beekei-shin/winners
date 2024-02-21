package org.winners.core.domain.field;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.BaseEntity;

import java.util.List;

@Comment("분야")
@Getter
@Entity
@Table(name = "field")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Field extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("분야명")
    @Column(name = "field_name", length = 100, nullable = false)
    private String name;

    @Comment("정렬 순서")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OrderBy("sortOrder ASC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field")
    private List<Job> jobList;

    @OrderBy("sortOrder ASC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field")
    private List<Subject> subjectList;

}
