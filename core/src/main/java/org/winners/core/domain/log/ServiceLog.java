package org.winners.core.domain.log;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("서비스 로그")
@Getter
@Entity
@Table(name = "service_log")
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Comment("회원 고유번호")
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Comment("로그 유형")
    @Column(name = "log_type", length = 50, nullable = false, updatable = false)
    private ServiceLogType type;

    public static ServiceLog createUpdatePasswordLog(long userId) {
        return ServiceLog.builder()
            .userId(userId)
            .type(ServiceLogType.UPDATE_PASSWORD)
            .build();
    }

}
