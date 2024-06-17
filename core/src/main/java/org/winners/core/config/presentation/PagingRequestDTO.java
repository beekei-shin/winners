package org.winners.core.config.presentation;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public class PagingRequestDTO {

    private final static int DEFAULT_PAGE = 1;
    private final static int DEFAULT_SIZE = 10;

    @Getter
    private Integer page;

    @Getter
    private Integer size;

    public PagingRequestDTO(Integer page, Integer size) {
        setPage(page);
        setSize(size);
    }

    public void setPage(Integer page) {
        this.page = Optional.ofNullable(page)
            .map(p -> p <= 0 ? 1 : p)
            .orElse(DEFAULT_PAGE);
    }

    public void setSize(Integer size) {
        this.size = Optional.ofNullable(size)
            .map(s -> s <= 0 ? 1 : s)
            .orElse(DEFAULT_SIZE);
    }

    public PageRequest of(Sort sort) {
        return Optional.ofNullable(sort)
            .map(s -> PageRequest.of(page - 1, size, s))
            .orElseGet(this::of);
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size);
    }

}
