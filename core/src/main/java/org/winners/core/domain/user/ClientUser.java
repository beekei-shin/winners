package org.winners.core.domain.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.Gender;
import org.winners.core.domain.field.Field;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Comment("사용자 회원")
@Getter
@Entity
@Table(name = "client_user", uniqueConstraints = {
    @UniqueConstraint(name = "UK_client_user_ci", columnNames = { "ci" }),
    @UniqueConstraint(name = "UK_client_user_phone_number", columnNames = { "phone_number" })
})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("CLIENT")
public class ClientUser extends User {

    @Comment("휴대폰 번호")
    @Column(name = "phone_number", length = 300, nullable = false)
    private String phoneNumber;

    @Comment("CI")
    @Column(name = "ci", length = 500, nullable = false)
    private String ci;

    @Comment("DI")
    @Column(name = "di", length = 500)
    private String di;

    @Comment("회원 생년월일")
    @Column(name = "user_birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Comment("회원 성별")
    @Column(name = "user_gender", length = 50)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientUser", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    private List<ClientUserJob> jobList;

    private ClientUser(String name, String phoneNumber, String ci, @Nullable String di, @Nullable LocalDate birthday, @Nullable Gender gender) {
        super(UserType.CLIENT, name);
        this.phoneNumber = phoneNumber;
        this.ci = ci;
        this.di = di;
        this.birthday = birthday;
        this.gender = gender;
    }

    public static ClientUser create(String name, String phoneNumber, String ci, @Nullable String di, @Nullable LocalDate birthday, @Nullable Gender gender) {
        return new ClientUser(name, phoneNumber, ci, di, birthday, gender);
    }

    public void updateJobs(Set<Long> jobIds) {
        if (this.jobList == null) this.jobList = new ArrayList<>();
        this.jobList.removeAll(this.jobList.stream()
            .filter(clientUserJob -> !jobIds.contains(clientUserJob.getJobId()))
            .toList());
        jobIds.stream()
            .filter(jobId -> !this.jobList.stream().map(ClientUserJob::getJobId).collect(Collectors.toSet()).contains(jobId))
            .map(jobId -> ClientUserJob.create(this, jobId))
            .forEach(clientUserJob -> this.jobList.add(clientUserJob));
    }

}
