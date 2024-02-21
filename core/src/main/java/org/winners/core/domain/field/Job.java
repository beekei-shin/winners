package org.winners.core.domain.field;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.BaseEntity;

@Comment("직업")
@Getter
@Entity
@Table(name = "job")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("직업명")
    @Column(name = "job_name", length = 100, nullable = false)
    private String name;

    @Comment("정렬 순서")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Comment("분야 고유번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

}
