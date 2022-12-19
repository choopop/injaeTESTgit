package kr.jobtc.guestbook;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class GuestbookController {
	
	@Autowired
	GuestbookDao dao;
	
	//경유하는 페이지인 bean페이지의 역할을 restcontroller가 대신해줌
	@RequestMapping("/guestbook/guestbook_select")
	public ModelAndView select(GPageVo gVo) {	//select안의 매개변수로 GPageVo 타입의 gVo를 넣어주게되면 스프링부트에서 자동으로 객체로 쓸수 있게 설정해줌?!
		ModelAndView mv = new ModelAndView();
		//GPageVo gVo = new GPageVo();
		//service or dao
		System.out.println("dao : " + dao);	//GuestbookDao에 @Component 또는 @Service 어노테이션을 붙여줘야함.
		System.out.println("now page : " +gVo.getNowPage());//select안의 매개변수로 GPageVo 타입의 gVo를 넣어주게되면 스프링부트에서 자동으로 객체로 쓸수 있다. 서블릿으로 작업시 해당 pVo를 가지고있는 유즈빈 태그페이지를 경유하는 내용과 비슷한 역할.
		System.out.println("findstr : " +gVo.getFindStr());
		//검색어에 따른 totSize를 가져와 page를 재계산
		int totSize = dao.getTotSize(gVo);
		gVo.setTotSize(totSize);	
		gVo.pageCompute(); //GPageVo 안에 setTotSize 정의시 컴퓨트가 실행되게끔 코드짜여있어서 없어도 되는 부분
		//검색어에 따른 List 가져옴
		List<GuestbookVo> list = dao.select(gVo);
		
		//List를 mv에 추가
		gVo = dao.getGPageVo();//새로 갱신된 페이지 정보
		
		mv.addObject("gVo",gVo);
		mv.addObject("list",list);
		mv.setViewName("guestbook/guestbook_select");	//실제 경로
		return mv;

	/*-------------------------------------------------------
	@Autowired
	GuestbookService service;
	
	@RequestMapping("/guestbook/guestbook_select")
	public ModelAndView select() {s
		int totSize=0;
		ModelAndView mv = new ModelAndView();
		GPageVo pVo = new GPageVo();
//		pVo.setFindStr(""); //GPageVo에서 기본값으로findStr="";으로 줘버림
		totSize = service.totSize(pVo);
		pVo.setTotSize(totSize);
		pVo.pageCompute();
		List<GuestbookVo> list = service.select(pVo);
		mv.addObject("pVo",pVo);
		mv.addObject("list", list);
		mv.setViewName("guestbook/guestbook_select");	//실제 경로
		return mv;
	*/
	}
	
	@RequestMapping("/guestbook/guestbook_insert")	
	public String insert(GuestbookVo vo, HttpServletResponse resp) {
		String msg="";
		boolean b = dao.insert(vo);
		/*
		try {
			PrintWriter out = resp.getWriter();
			if(!b) {
				out.print("<script>");
				out.print(" alert('저장중 오류발생')");
				out.print("</script>");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}*/
		if(!b) {
			msg="작성중 오류 발생";
		}else if(b) {
			msg="게시글 작성 완료";
		}
		return msg;
	}
	
	//위에 insert 메서드 복사해서 delete로만 바꿔줌 
	@RequestMapping("/guestbook/guestbook_delete")	
	public String delete(GuestbookVo vo, HttpServletResponse resp) {
		String msg="";
		boolean b = dao.delete(vo);
		if(!b) {
			msg="삭제중 오류 발생";
		}else if(b) {
			msg="게시글 삭제 완료";
		}
		return msg;
		
	}
	
	@RequestMapping("/guestbook/guestbook_update")	
	public String update(GuestbookVo vo, HttpServletResponse resp) {
		String msg="";
		boolean b = dao.update(vo);
		
		if(!b) {
			msg="수정중 오류 발생";
		}else if(b) {
			msg="게시글 수정 완료";
		}
		return msg;
	}
	
	@RequestMapping("/guestbook/guestbook10")
	public ModelAndView select10(GPageVo gVo) {	//select안의 매개변수로 GPageVo 타입의 gVo를 넣어주게되면 스프링부트에서 자동으로 객체로 쓸수 있게 설정해줌?!
		ModelAndView mv = new ModelAndView();
		List<GuestbookVo> list = dao.select10();	//리스트타입이 아니라 제이슨 타입또는 다른 데이터타입으로 넘기는게 더 좋은 상황이 나올 수 도 있다
		mv.addObject("list",list);
		mv.setViewName("guestbook/guestbook10");	//실제 경로
		return mv;
	}
	
}
