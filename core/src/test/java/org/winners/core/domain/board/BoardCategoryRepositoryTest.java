package org.winners.core.domain.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.winners.core.config.RepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoardCategoryRepositoryTest extends RepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @Test
    void deleteByBoardId() {
        Board noticeBoard = boardRepository.saveAndFlush(Board.createBoard(BoardType.NOTICE, "공지사항"));
        boardCategoryRepository.saveAndFlush(BoardCategory.createCategory(noticeBoard.getId(), "카테고리1", 1));
        boardCategoryRepository.saveAndFlush(BoardCategory.createCategory(noticeBoard.getId(), "카테고리2", 2));
        boardCategoryRepository.saveAndFlush(BoardCategory.createCategory(noticeBoard.getId(), "카테고리3", 3));

        Board communityBoard = boardRepository.saveAndFlush(Board.createBoard(BoardType.COMMUNITY, "커뮤니티"));
        boardCategoryRepository.saveAndFlush(BoardCategory.createCategory(communityBoard.getId(), "카테고리1", 1));
        boardCategoryRepository.saveAndFlush(BoardCategory.createCategory(communityBoard.getId(), "카테고리2", 2));
        boardCategoryRepository.saveAndFlush(BoardCategory.createCategory(communityBoard.getId(), "카테고리3", 3));

        boardCategoryRepository.deleteByBoardId(noticeBoard.getId());

        List<BoardCategory> savedBoardCategoryList = boardCategoryRepository.findAll();

        assertThat(savedBoardCategoryList.size()).isEqualTo(3);
        savedBoardCategoryList.forEach(boardCategory -> assertThat(boardCategory.getBoardId()).isEqualTo(communityBoard.getId()));
    }

}