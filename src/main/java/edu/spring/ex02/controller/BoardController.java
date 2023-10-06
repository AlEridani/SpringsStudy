package edu.spring.ex02.controller;

import java.nio.file.attribute.FileStoreAttributeView;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.spring.ex02.domain.BoardVO;
import edu.spring.ex02.pageutil.PageCriteria;
import edu.spring.ex02.pageutil.PageMaker;
import edu.spring.ex02.service.BoardService;
import edu.spring.ex02.service.BoardServiceImple;

@Controller // @Component

// * 표현계층(Presentation Layer)
// - view(페이지)와 service를 연결하는 역할
// - request에 대한 response를 전달하는 역할
@RequestMapping(value = "/board") // url : /ex02/board
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public void list(Model model, Integer page, Integer numsPerPage) {
		logger.info("list 호출");
		logger.info("page = " + page + ", numsPerPage = " + numsPerPage);

		PageCriteria criteria = new PageCriteria();
		if (page != null) {
			criteria.setPage(page);
		}

		if (numsPerPage != null) {
			criteria.setNumsPerPage(numsPerPage);
		}

		List<BoardVO> list = boardService.read(criteria);
		model.addAttribute("list", list);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(boardService.getTotalCounts());
		pageMaker.setPageData();
		model.addAttribute("pageMaker", pageMaker);
	}
	
	

	@GetMapping("/register")
	public void registerGET() {
		logger.info("registerGET 호출");

	}// end registerGET()

	@PostMapping("/register")
	public String registerPOST(BoardVO vo, RedirectAttributes reAttr) {
		// RedirectAttrubutes
		// - 리다이렉트시 데이터를 전달하기 위한 인터페이스
		logger.info("registerPOST 호출");
		logger.info(vo.toString());
		int result = boardService.create(vo);
		logger.info(result + "행 삽입");
		if (result == 1) {
			reAttr.addFlashAttribute("insert_result", "success");
			return "redirect:/board/list";
		} else {
			return "redirect:/board/register";
		}
	}//end registerPOST
	
	@GetMapping("/detail")
	public void detail(Model model, Integer boardId, Integer page) {
		logger.info("detail 호출 : boardId = " + boardId);
		BoardVO vo = boardService.read(boardId);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		
	}//end detail
	
	@GetMapping("/update")
	public void updateGET(Model model, Integer boardId, Integer page) {
		logger.info("update호출 : boardId = " + boardId);
		BoardVO vo = boardService.read(boardId);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
	}
	
	@PostMapping("/update")
	public String updatePOST(BoardVO vo, Integer page) {
		logger.info("updatePOST 호출 vo : " + vo.toString());
		
		int result = boardService.update(vo);
		if(result == 1) {
			return "redirect:/board/list?page=" + page;
		}else {
			return "redirect:/board/update?boardId=" + vo.getBoardId();
		}

	}//end updatePOST
	
	@PostMapping("/delete")
	public String delete(Integer boardId) {
		logger.info("delete 호출 boardId : " + boardId);
		int result = boardService.delete(boardId);
		
		if(result == 1) {
			return "redirect:/board/list";
		}else {
			return "redirect:/board/list";
		}
	}

}// end BoardController
