package org.winners.core.domain.project;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.user.ClientUsersField;

import java.util.List;

@Comment("프로젝트 구성원")
@Getter
@Entity
@Table(name = "project_member")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("프로젝트 고유번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    @Comment("직업 고유번호")
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Comment("사용자 회원 고유번호")
    @Column(name = "client_user_id")
    private Long clientUserId;

}
