package org.winners.app.application.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winners.app.application.board.dto.BoardPostInfoDTO;
import org.winners.app.application.board.dto.BoardPostListDTO;
import org.winners.app.application.board.dto.GetPostListSearchParameterDTO;
import org.winners.app.application.board.dto.UpdatePostParameterDTO;
import org.winners.core.domain.board.service.dto.SavePostParameterDTO;

@Service
public interface AppBoardPostService {
    @Transactional(readOnly = true)
    Page<BoardPostListDTO> getPostList(GetPostListSearchParameterDTO searchParameter, PageRequest pageRequest);
    @Transactional(readOnly = true)
    BoardPostInfoDTO getPostInfo(long postId, long viewUserId);
    @Transactional
    void savePost(long userId, SavePostParameterDTO parameter);
    @Transactional
    void updatePost(long userId, long postId, UpdatePostParameterDTO parameter);
}
