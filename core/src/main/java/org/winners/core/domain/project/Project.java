package org.winners.core.domain.project;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.BaseEntity;

import java.util.List;

@Comment("프로젝트")
@Getter
@Entity
@Table(name = "project")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("프로젝트명")
    @Column(name = "project_name", length = 100, nullable = false)
    private String name;

    @Comment("프로젝트 내용")
    @Column(name = "project_contents", length = 500, nullable = false)
    private String contents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectMember> memberList;

}
