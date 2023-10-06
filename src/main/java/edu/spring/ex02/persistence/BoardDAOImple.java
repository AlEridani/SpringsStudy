package edu.spring.ex02.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.spring.ex02.domain.BoardVO;
import edu.spring.ex02.pageutil.PageCriteria;


@Repository //@Component
// - 영속 계층(Persistence Layer)의 DB 관련 기능을 담당
// - Spring Component bean으로 등록함
// - servlet-context.xml의 component-scan을 통해
//   설정된 component를 찾아와 bean으로 등록
// - <context:component-scan ../>
public class BoardDAOImple implements BoardDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAOImple.class);

	private static final String NAMESPACE = "edu.spring.ex02.BoardMapper";
	
	
	// Mybatis의 SqlSession을 사용하기 위해
	// 스프링 프레임워크가 생성한 bean을 주입(injection)받음
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int insert(BoardVO vo) {
		logger.info("insert 호출");
		return sqlSession.insert(NAMESPACE + ".insert",vo );
		// NAMESPACE가 동일한 mapper를 찾아가서 id="insert"인
		// <insert> 태그에 vo 데이터를 전송
	}

	@Override
	public List<BoardVO> select() {
		logger.info("select_all 호출");
		return sqlSession.selectList(NAMESPACE + ".select_all");
	}

	@Override
	public BoardVO select(int boardId) {
		logger.info("select 호출 : boardId = " + boardId );
		return sqlSession.selectOne(NAMESPACE + ".select_by_board_id", boardId);
	}

	@Override
	public int update(BoardVO vo) {
		logger.info("update 호출 : vo = " + vo.toString());
		return sqlSession.update(NAMESPACE + ".update", vo);
	}

	@Override
	public int delete(int boardId) {
		logger.info("delete 호출 : boardId = " + boardId );
		return sqlSession.delete(NAMESPACE + ".delete", boardId);
	}

	@Override
	public List<BoardVO> select(PageCriteria criteria) {
		logger.info("select 호출");
		logger.info("start = " + criteria.getStart());
		logger.info("end = " + criteria.getEnd());
		return sqlSession.selectList(NAMESPACE+ ".paging", criteria);
	}
	
	@Override
	public int getTotalCounts() {
		logger.info("getTotalCounts호출");
		return sqlSession.selectOne(NAMESPACE+ ".total_count");
	}

	@Override
	public List<BoardVO> select(String memberId) {
		logger.info("select 호출 memberId = : " + memberId);
		return sqlSession.selectList(NAMESPACE + ".select_by_memberid", "%" + memberId + "%");
	}

	@Override
	public List<BoardVO> selectByTitleOrContent(String keyword) {
		logger.info("select 호출 keyword =" + keyword);
		return sqlSession.selectList(NAMESPACE + ".select_by_title_content", "%" + keyword + "%");
	}



}
