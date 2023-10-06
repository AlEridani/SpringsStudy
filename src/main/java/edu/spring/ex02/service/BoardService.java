package edu.spring.ex02.service;

import java.util.List;

import edu.spring.ex02.domain.BoardVO;
import edu.spring.ex02.pageutil.PageCriteria;

// 	CURD(Create, Read, Update, Delete)
public interface BoardService {
	int create(BoardVO vo);
	List<BoardVO> read(PageCriteria criteria);
	BoardVO read(int boardId);
	int update(BoardVO vo);
	int delete(int boardId);
	int getTotalCounts();

}
