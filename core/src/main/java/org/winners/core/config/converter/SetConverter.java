package org.winners.core.config.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
@Component
@RequiredArgsConstructor
public class SetConverter implements AttributeConverter<Set<Long>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        return Optional.ofNullable(attribute)
            .map(attr -> attr.stream().map(String::valueOf).collect(Collectors.toSet()))
            .map(attr -> String.join(",", attr))
            .orElse(null);
    }

    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
            .map(data -> Arrays.stream(data.split(",")).map(Long::parseLong).collect(Collectors.toSet()))
            .orElse(null);
    }

}
