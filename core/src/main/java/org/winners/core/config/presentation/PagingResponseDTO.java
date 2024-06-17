package org.winners.core.config.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PagingResponseDTO {

    protected final int currentPage;
    protected final int currentSize;
    protected final int totalPage;
    protected final long totalCount;

    public PagingResponseDTO(Page<?> page) {
        this.currentPage = page.getNumber() + 1;
        this.currentSize = page.getSize();
        this.totalPage = page.getTotalPages();
        this.totalCount = page.getTotalElements();
    }
}
