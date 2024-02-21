package org.winners.app.application.field.impl;

import lombok.RequiredArgsConstructor;
import org.winners.app.application.field.FieldService;
import org.winners.app.application.field.dto.FieldDTO;
import org.winners.core.domain.field.FieldRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FieldServiceV1 implements FieldService {

    private final FieldRepository fieldRepository;

    @Override
    public List<FieldDTO> getFieldList() {
        return fieldRepository.findAllByOrderBySortOrderAsc().stream()
            .map(FieldDTO::create)
            .collect(Collectors.toList());
    }

}
