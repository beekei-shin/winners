package org.winners.app.presentation.field.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.application.field.FieldService;
import org.winners.app.application.field.dto.FieldDTO;
import org.winners.app.presentation.field.FieldController;
import org.winners.app.presentation.field.response.GetFieldListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FieldControllerV1 implements FieldController {


    @Qualifier("FieldServiceV1")
    private final FieldService fieldService;

    @Override
    public ApiResponse<GetFieldListResponseDTO> getFieldList() {
        final List<FieldDTO> fieldList = fieldService.getFieldList();
        return ApiResponse.success(GetFieldListResponseDTO.convert(fieldList));
    }

}
