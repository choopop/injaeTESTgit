package kr.jobtc.board;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	static String path= "C:\\Users\\LG\\eclipse-workspace\\SpringSpaProject\\src\\main\\resources\\static\\upload\\";
	//해당 첨부파일을 이용하는(미리보기하기위해) board_view.jsp의 img태그(<img src='/upload/${att.sysFile}'/> )에서
	//위 코드를 통해 이미지를 불러오는걸로 코딩해놔서 파일업로드도 위 경로로 파일이 생성되도록 설정해줌
	//application-properties에서 spring.mvc.static-locations=/resources/**   으로 static-locations를 지정해줬기때문에 정적은 위 경로로 설정해준다..?

	@Autowired
	BoardService service;
	
	@RequestMapping("/board/board_insertR")
	public String insertR(@RequestParam("attFile") List<MultipartFile> mul,
						 @ModelAttribute BoardVo vo) {
		//board_insert.jsp 의 네임이attFile 이라는놈을 파람으로 받아와서 List<MultipartFile>타입의 mul이라는 녀석으로받음
		String msg = "";
		try {
			System.out.println(vo.getId());
			System.out.println(vo.getSubject());
		
			List<AttVo> attList = new ArrayList<AttVo>();
			
			//본문 내용을 저장하는 프로세스
			attList = fileupload(mul);
			vo.setAttList(attList);
			boolean flag = service.insertR(vo);	
			
			if(!flag) { 
				msg = "저장중오류발생";
			}
	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		//return "redirect:/board/board_select";		//파일업로드되고나서 다시 셀렉트페이지로 연결시키는 코드임.
		return msg;
	}
	
	
	@RequestMapping("/board/board_updateR")
	//ModelAttrubute 는 어떤 vo타입으로 받을때 쓰는 어노테이션이고
	//RequestParam js에서 애초에 네임으로
	public String updateR(@RequestParam(name="attFile") List<MultipartFile> mul,
				@ModelAttribute BoardVo bVo,@ModelAttribute PageVo pVo,
				@RequestParam(name="delFile", required= false) String[] delFile) {
		System.out.println("attList 업데이트테스트입니다:" + delFile);
		String msg="";
		try {
			List<AttVo> attList = fileupload(mul);
			bVo.setAttList(attList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		boolean flag = service.updateR(bVo, delFile);
		
		if(!flag) msg ="수정중 오류 발생";
		
		return msg;
		
	}
	//file upload 하는 공통 메서드를 따로 빼서 만듬(insertR, updateR, replR 에서 계속쓰이기때문에 공통 메서드로 만들어서 코드량을 줄이기위함.)
	public List<AttVo> fileupload(List<MultipartFile> mul) throws Exception{
		List<AttVo> attList = new ArrayList<AttVo>();
		//-------------
		for(MultipartFile m : mul) {
			if(m.isEmpty()) continue;
			
			
			UUID uuid = UUID.randomUUID();
			String oriFile = m.getOriginalFilename();
			String sysFile ="";
			File temp = new File(path + oriFile);
			m.transferTo(temp);
			sysFile = (uuid.getLeastSignificantBits()*-1)+ "-" + oriFile;	//uuid를통한랜덤난수생성 + 첨부된파일의원래이름(oriFile) 를 sysFile로 받음
			File f = new File(path + sysFile);//새로운 파일 f를 만듬, 그리고 그경로와 이름을 위에서 만들어놓은 sysFile로 정함
			temp.renameTo(f);
			
			AttVo attVo = new AttVo();
			attVo.setOriFile(oriFile);
			attVo.setSysFile(sysFile);
			//attVo.setpSno(sno);	//DB에서 getSerial함수로 만들어서 board.xml에서 그대로사용 그래서필요없음
			
			attList.add(attVo);
			
			System.out.println(m.getOriginalFilename());
			System.out.println("uuid_test1 : " + uuid.toString());
			System.out.println("uuid_test2 : " + uuid.getLeastSignificantBits());	//랜덤난수가 마이너스로 시작됌
			//uuid.~~ : 랜덤내용찍는?기능을갖는메서드 ctrl+space눌러서 uuid객체의 여러기능을사용가능
		}
		
		//-------------
		return attList;
	}
	
	@RequestMapping("/board/board_replR")
	public synchronized String replR(@RequestParam("attFile") List<MultipartFile> mul,
						 @ModelAttribute BoardVo bVo, @ModelAttribute PageVo pVo) {
		
		try {
			List<AttVo> attList = new ArrayList<AttVo>();
			attList = fileupload(mul);
			bVo.setAttList(attList);			
			//본문 내용을 저장하는 프로세스
			boolean flag = service.replR(bVo);	
			
			if(!flag) return "저장중오류발생";
			attList = fileupload(mul);	
			
			for(MultipartFile m : mul) {
				//fileupload 부분이 insertR,updateR,replR 에서 계속쓰이기때문에 따로 fileupload라는 메서드로 만들어서 코드를 간소화했다
				if(!m.isEmpty()) {
					service.insertAttList(attList);
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/board/board_select";		//파일업로드되고나서 다시 셀렉트페이지로 연결시키는 코드임.
	}

}
