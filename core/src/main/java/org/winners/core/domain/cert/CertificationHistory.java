package org.winners.core.domain.cert;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("인증 내역")
@Getter
@Entity
@Table(name = "certification_history", uniqueConstraints = {
    @UniqueConstraint(name = "UK_certification_history_certification_key", columnNames = { "certification_key" })
})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "certification_type", length = 50, discriminatorType = DiscriminatorType.STRING)
public class CertificationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("인증유형")
    @Column(name = "certification_type", length = 50, nullable = false, insertable = false, updatable = false)
    private CertificationType certificationType;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @Comment("인증키")
    @JoinColumn(name = "certification_key", columnDefinition = "BINARY(16)", nullable = false)
    protected CertificationKey certificationKey;

    protected CertificationHistory(CertificationType certificationType, CertificationKey certificationKey) {
        this.certificationType = certificationType;
        this.certificationKey = certificationKey;
    }

}
