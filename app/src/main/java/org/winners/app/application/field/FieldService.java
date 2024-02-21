package org.winners.app.application.field;

import org.springframework.stereotype.Service;
import org.winners.app.application.field.dto.FieldDTO;

import java.util.List;

@Service
public interface FieldService {

    List<FieldDTO> getFieldList();

}
