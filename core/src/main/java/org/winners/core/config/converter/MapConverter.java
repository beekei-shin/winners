package org.winners.core.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Converter
@Component
@RequiredArgsConstructor
public class MapConverter implements AttributeConverter<Map<String, String>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
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
    public Map<String, String> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
            .map(data -> {
                try {
                    return (Map<String, String>) objectMapper.readValue(data, Map.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).orElse(null);
    }

}
