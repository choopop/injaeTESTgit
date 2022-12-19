package kr.jobtc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.jobtc.board.BoardService;
import kr.jobtc.board.BoardVo;

@Controller
public class MainController {
	@Autowired
	BoardService service;	
	
	
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		
		//게시판 1포함하는 데이터 출력 list로
		List<BoardVo> list = service.select("1");
		mv.addObject("list", list);
		
		
		//두가지 list,vlist 가 addObject된 ModelandViewdp viewName =index로 셋하고 리턴한다
		//mv에서 리퀘스트에 담아주고 set된 ViewName으로 이동시켜준다?!
		mv.setViewName("index");
		return mv;
	}
	
	
}
