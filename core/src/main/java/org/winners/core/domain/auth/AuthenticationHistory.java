package org.winners.core.domain.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;
import org.winners.core.domain.common.DeviceOs;

import java.time.LocalDateTime;

@Comment("인증 내역")
@Getter
@Entity
@Table(name = "authentication_history")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthenticationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("회원 고유번호")
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Comment("Device OS")
    @Column(name = "device_os", length = 50, updatable = false)
    private DeviceOs deviceOs;

    @Comment("DeviceToken")
    @Column(name = "device_token", length = 500, updatable = false)
    private String deviceToken;

    @Comment("AccessToken")
    @Column(name = "access_token", length = 500, nullable = false, updatable = false)
    private String accessToken;

    @Comment("AccessToken 만료일자")
    @Column(name = "access_token_expire_datetime", nullable = false, updatable = false)
    private LocalDateTime accessTokenExpireDatetime;

    @Comment("RefreshToken")
    @Column(name = "refresh_token", length = 500, nullable = false, updatable = false)
    private String refreshToken;

    @Comment("RefreshToken 만료일자")
    @Column(name = "refresh_token_expire_datetime", nullable = false, updatable = false)
    private LocalDateTime refreshTokenExpireDatetime;

    public static AuthenticationHistory createClientUserAuthHistory(long userId,
                                                                    DeviceOs deviceOs, String deviceToken,
                                                                    String accessToken, LocalDateTime accessTokenExpireDatetime,
                                                                    String refreshToken, LocalDateTime refreshTokenExpireDatetime) {
        return AuthenticationHistory.builder()
            .userId(userId)
            .deviceOs(deviceOs)
            .deviceToken(deviceToken)
            .accessToken(accessToken)
            .accessTokenExpireDatetime(accessTokenExpireDatetime)
            .refreshToken(refreshToken)
            .refreshTokenExpireDatetime(refreshTokenExpireDatetime)
            .build();
    }

    public static AuthenticationHistory createBusinessUserAuthHistory(long userId,
                                                                      String accessToken, LocalDateTime accessTokenExpireDatetime,
                                                                      String refreshToken, LocalDateTime refreshTokenExpireDatetime) {
        return AuthenticationHistory.builder()
            .userId(userId)
            .accessToken(accessToken)
            .accessTokenExpireDatetime(accessTokenExpireDatetime)
            .refreshToken(refreshToken)
            .refreshTokenExpireDatetime(refreshTokenExpireDatetime)
            .build();
    }

    public static AuthenticationHistory createAdminUserAuthHistory(long userId,
                                                                    String accessToken, LocalDateTime accessTokenExpireDatetime,
                                                                    String refreshToken, LocalDateTime refreshTokenExpireDatetime) {
        return AuthenticationHistory.builder()
            .userId(userId)
            .accessToken(accessToken)
            .accessTokenExpireDatetime(accessTokenExpireDatetime)
            .refreshToken(refreshToken)
            .refreshTokenExpireDatetime(refreshTokenExpireDatetime)
            .build();
    }

}
