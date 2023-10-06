package edu.spring.ex02;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })

@WebAppConfiguration

public class SqlSessionFactorytest {
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactorytest.class);

	// Spring Framework가 관리하는 DataSource 객체를 주입받음
	// - root-context.xml에서 관리하는 bean으로 선택된 DataSource 객체를 주입받음

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Test
	public void testSessionFactoey() {
		SqlSession session = sessionFactory.openSession();
		if(session != null) {
			logger.info("sqlSession 생성 성공");
		}else {
			logger.info("sqlSession 생성실패");
		}
		
	}

}
