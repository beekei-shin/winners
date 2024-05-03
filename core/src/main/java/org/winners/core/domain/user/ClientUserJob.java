package org.winners.core.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("사용자 회원 직업")
@Getter
@Entity
@Table(name = "client_user_job",
    uniqueConstraints = { @UniqueConstraint(name = "UK_client_user_job", columnNames = { "client_user_id", "job_id" }) })
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientUserJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("사용자 회원 고유번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_user_id", nullable = false)
    private ClientUser clientUser;

    @Comment("직업 고유번호")
    @Column(name = "job_id", nullable = false, updatable = false)
    private Long jobId;

    public static ClientUserJob create(ClientUser clientUser, long jobId) {
        return ClientUserJob.builder()
            .clientUser(clientUser)
            .jobId(jobId)
            .build();
    }

}
