package org.winners.core.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.BoardCategory;
import org.winners.core.domain.board.BoardCategoryRepository;

@Service
@RequiredArgsConstructor
public class BoardCategoryDomainService {

    private final BoardCategoryRepository boardCategoryRepository;

    public BoardCategory getCategory(long categoryId) {
        return boardCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_CATEGORY));
    }

}
