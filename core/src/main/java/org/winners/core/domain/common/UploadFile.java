package org.winners.core.domain.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("업로드 파일")
@Getter
@Entity
@Table(name = "upload_file")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type", length = 50, discriminatorType = DiscriminatorType.STRING)
public class UploadFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Comment("파일 유형")
    @Column(name = "file_type", length = 50, nullable = false, insertable=false, updatable=false)
    private FileType fileType;

    @Comment("원본 파일명")
    @Column(name = "original_file_name", length = 200, nullable = false)
    private String originalName;

    @Comment("업로드 파일명")
    @Column(name = "upload_file_name", length = 200, nullable = false)
    private String uploadName;

    @Comment("파일 URL")
    @Column(name = "file_url", length = 500, nullable = false)
    private String url;

    @Comment("파일 경로")
    @Column(name = "file_path", length = 500, nullable = false)
    private String path;

    @Comment("파일 사이즈")
    @Column(name = "file_size", nullable = false)
    private Long size;

    @Comment("파일 확장자")
    @Column(name = "file_extension", length = 10, nullable = false)
    private String extension;

}
