package kr.jobtc;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.jobtc.board.BoardService;
import kr.jobtc.board.BoardVo;
import kr.jobtc.board.PageVo;

@RestController
public class BoardController {

	
	@Autowired
	BoardService service;	
	
	
	
	@RequestMapping("board/board_select2")
	public ModelAndView select2(PageVo pVo) {
		ModelAndView mv = new ModelAndView();
		List<BoardVo> list = service.select2(pVo);
		pVo = service.getpVo();
		mv.addObject("list", list);
		mv.addObject("pVo",pVo);
		mv.setViewName("board/board_select");  /* WEB-INF/view/board/board_select.jsp */
		return mv;
	}
	
	//최신게시물 10개 원본글만(deep이 0인) 출력
	@RequestMapping("/board/board10")
	public ModelAndView board10(){
		ModelAndView mv = new ModelAndView();
		
		List<BoardVo> list = service.board10();
		
		
		mv.addObject("list",list);
		mv.setViewName("/board/board_board10");
		
		return mv;
	}
	
	
	//boardview
	@RequestMapping("/board/board_view")
	public ModelAndView view(BoardVo bVo, PageVo pVo) {
		ModelAndView mv = new ModelAndView();
		
		//조회수 증가
		bVo = service.view(bVo.getSno(), "up");
		
		//doc안에 있는 \n 기호를 <br/>로 변경
		String temp = bVo.getDoc();
		bVo.setDoc(temp.replace("\n","<br/>"));
		mv.addObject("bVo",bVo);
		mv.addObject("pVo",pVo);
		
		mv.setViewName("/board/board_view");
		return mv;
		
	}
	//delete
	@RequestMapping("/board/board_delete")
	public ModelAndView delete(BoardVo bVo, PageVo pVo) {
		String msg = "";
		ModelAndView mv = new ModelAndView();
		
		boolean b = service.delete(bVo);
		if(!b) {
			msg = "삭제중 오류 발생";
		}
		
		mv = select2(pVo);	//셀렉2로 담겨진 데이터들을 들고 있게됌; 삭제되고 다시 select.jsp로 넘어가게 하려고
		mv.addObject("msg",msg);
		mv.setViewName("/board/board_select");
		return mv;
		
	}
	
	@RequestMapping("/board/board_insert")
	public ModelAndView insert( PageVo pVo) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pVo",pVo);
		mv.setViewName("/board/board_insert");
		return mv;
		
	}
	@RequestMapping("/board/board_update")
	public ModelAndView update( PageVo pVo) {
		ModelAndView mv = new ModelAndView();
		BoardVo bVo =service.view(pVo.getSno(), "");
		mv.addObject("pVo",pVo);
		mv.addObject("bVo",bVo);
		mv.setViewName("/board/board_update");
		return mv;
	}
	
	@RequestMapping("/board/board_repl")
	public ModelAndView repl(PageVo pVo, BoardVo bVo) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pVo",pVo);
		mv.addObject("bVo",bVo);
		mv.setViewName("/board/board_repl");
		return mv;
	}
	
	
	
	
	
	
	
	
	
	//테스트로 만들어봄
		@RequestMapping("board/board_select")
		public ModelAndView select() {
			ModelAndView mv = new ModelAndView();
			List<BoardVo> list = service.select("1");
			mv.addObject("list", list);
			mv.setViewName("board/board_select");  /* WEB-INF/view/board/board_select.jsp */
			return mv;
		}
	
}
