package org.winners.backoffice.application.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winners.backoffice.application.board.dto.BoardPostInfoDTO;
import org.winners.backoffice.application.board.dto.BoardPostListDTO;
import org.winners.backoffice.application.board.dto.GetPostListSearchParameterDTO;

@Service
public interface BoardPostManageService {
    @Transactional(readOnly = true)
    Page<BoardPostListDTO> getPostList(GetPostListSearchParameterDTO searchParameter, PageRequest pageRequest);
    @Transactional(readOnly = true)
    BoardPostInfoDTO getPostInfo(long postId);
}
