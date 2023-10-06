package edu.spring.ex02.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.spring.ex02.domain.BoardVO;
import edu.spring.ex02.pageutil.PageCriteria;
import edu.spring.ex02.persistence.BoardDAO;

@Service // @Component
// * 서비스 계층(Service/Business Layer)
// - 표현계층 (Presentation Layer)과 영속계층(Persistence Layer)사이를
//	 연결하여 두 계층이 직접적으로 통신하지 않도록 하는 역할
// - 트랜잭션(transaction) 관리
// - DB와 상관없이 데이터를 처리 가능
public class BoardServiceImple implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImple.class);

	@Autowired
	private BoardDAO dao;
	
	@Override
	public int create(BoardVO vo) {
		logger.info("create() 호출 vo : " + vo.toString()); 
		return dao.insert(vo);
	}

	@Override
	public List<BoardVO> read(PageCriteria criteria) {
		logger.info("read() 호출 ");
		logger.info("start = " + criteria.getStart());
		logger.info("end = " + criteria.getEnd());
		return dao.select(criteria);
	}

	@Override
	public BoardVO read(int boardId) {
		logger.info("read 호출 boardId = " + boardId  );
		return dao.select(boardId);
	}

	@Override
	public int update(BoardVO vo) {
		logger.info("update 호출 vo = " + vo.toString());
		return dao.update(vo);
	}

	@Override
	public int delete(int boardId) {
		logger.info("delete 호출 boardId = " + boardId);
		return dao.delete(boardId);
	}

	@Override
	public int getTotalCounts() {
		logger.info("getTotalCounts 호출 ");
		return dao.getTotalCounts();
	}

}
