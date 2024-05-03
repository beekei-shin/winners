package org.winners.app.presentation.user.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveClientUserJobRequestDTO {

    @NotNull
    @Size(min = 1)
    private Set<Long> jobIds;

}
