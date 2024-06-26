package org.winners.core.domain.cert;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.winners.core.config.exception.AlreadyProcessedDataException;
import org.winners.core.config.exception.UnprocessedDataException;
import org.winners.core.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.winners.core.config.exception.ExceptionMessageType.*;

@Comment("인증키")
@Getter
@Entity
@Table(name = "certification_key")
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CertificationKey extends BaseEntity {

    @Comment("인증키")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    @Comment("인증 유형")
    @Enumerated(EnumType.STRING)
    @Column(name = "certification_type", length = 50, nullable = false, updatable = false)
    private CertificationType certificationType;

    @Comment("요청일자")
    @Column(name = "request_datetime", nullable = false, updatable = false)
    private LocalDateTime requestDatetime;

    @Comment("인증여부")
    @Column(name = "is_certified", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false, insertable = false)
    private boolean isCertified;

    @Comment("인증일자")
    @Column(name = "certified_datetime", insertable = false)
    private LocalDateTime certifiedDatetime;

    @Comment("사용여부")
    @Column(name = "is_used", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false, insertable = false)
    private boolean isUsed;

    @Comment("사용일자")
    @Column(name = "used_datetime", insertable = false)
    private LocalDateTime usedDatetime;

    @Comment("만료일자")
    @Column(name = "expired_datetime", nullable = false, updatable = false)
    private LocalDateTime expiredDatetime;

    public static CertificationKey createKey(CertificationType certificationType, int expiredMinute) {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .certificationType(certificationType)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.plusMinutes(expiredMinute))
            .build();
    }

    public boolean isExpired() {
        return this.expiredDatetime.isBefore(LocalDateTime.now());
    }

    public void expiredCheck() {
        if (this.isExpired())
            throw new AlreadyProcessedDataException(EXPIRED_CERTIFICATION_KEY);
    }

    public void notCertifiedCheck() {
        if (!this.isCertified)
            throw new UnprocessedDataException(NOT_CERTIFIED_CERTIFICATION_KEY);
    }

    public void certifiedCheck() {
        if (this.isCertified)
            throw new AlreadyProcessedDataException(ALREADY_CERTIFIED_CERTIFICATION_KEY);
    }

    public void usedCheck() {
        if (this.isUsed)
            throw new AlreadyProcessedDataException(ALREADY_USED_CERTIFICATION_KEY);
    }

    public void possibleCertifyCheck() {
        this.expiredCheck();
        this.certifiedCheck();
        this.usedCheck();
    }

    public void possibleUseCheck() {
        this.expiredCheck();
        this.notCertifiedCheck();
        this.usedCheck();
    }

    public void certify() {
        this.isCertified = true;
        this.certifiedDatetime = LocalDateTime.now();
    }

    public void use() {
        this.isUsed = true;
        this.usedDatetime = LocalDateTime.now();
    }

}
