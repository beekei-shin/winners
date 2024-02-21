package org.winners.app.application.field.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.field.Field;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FieldDTO {
    private long fieldId;
    private String fieldName;

    public static FieldDTO create(Field field) {
        return FieldDTO.builder()
            .fieldId(field.getId())
            .fieldName(field.getName())
            .build();
    }

}
