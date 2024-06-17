package org.winners.app.presentation.board.v1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.domain.board.service.dto.SavePostParameterDTO;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavePostRequestDTO {

    @Min(value = 1)
    private long categoryId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContents;

    @NotNull
    private boolean isSecretPost;

    public SavePostParameterDTO convertParameter() {
        return SavePostParameterDTO.builder()
            .categoryId(categoryId)
            .postTitle(postTitle)
            .postContents(postContents)
            .isSecretPost(isSecretPost)
            .build();
    }

}
