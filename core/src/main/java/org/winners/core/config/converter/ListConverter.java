package org.winners.core.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Converter
@Component
@RequiredArgsConstructor
public class ListConverter <T> implements AttributeConverter<List<T>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        return Optional.ofNullable(attribute)
            .map(attr -> {
                try {
                    return objectMapper.writeValueAsString(attr);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            })
            .orElse(null);
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
            .map(data -> {
                try {
                    return (List<T>) objectMapper.readValue(data, List.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).orElse(null);
    }

}
