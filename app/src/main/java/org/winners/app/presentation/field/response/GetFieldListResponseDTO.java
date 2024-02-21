package org.winners.app.presentation.field.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.winners.app.application.field.dto.FieldDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFieldListResponseDTO {

    private final List<FieldResponseDTO> fieldList;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class FieldResponseDTO {
        private long fieldId;
        private String fieldName;
    }

    public static GetFieldListResponseDTO convert(List<FieldDTO> fieldList) {
        return new GetFieldListResponseDTO(fieldList.stream()
            .map(field -> FieldResponseDTO.builder()
                .fieldId(field.getFieldId())
                .fieldName(field.getFieldName())
                .build())
            .collect(Collectors.toList()));
    }

}
