package edu.spring.ex02.persistence;

import java.util.List;

import edu.spring.ex02.domain.BoardVO;
import edu.spring.ex02.pageutil.PageCriteria;

public interface BoardDAO {
	int insert(BoardVO vo);
	List<BoardVO> select();
	BoardVO select(int boardId);
	int update(BoardVO vo);
	int delete(int boardId);
	int getTotalCounts();
	List<BoardVO> select(PageCriteria criteria);
	List<BoardVO> select(String memberId);
	List<BoardVO> selectByTitleOrContent(String keyword);

}
