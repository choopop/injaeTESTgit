package kr.jobtc.board;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import kr.jobtc.mybatis.BoardMapper;

@Transactional
@Service
public class BoardService {
	PageVo pVo;
	
	public PageVo getpVo() {return pVo;}
	
	@Autowired
	PlatformTransactionManager manager;
	TransactionStatus status;
	
	@Autowired
	BoardMapper mapper;
	
	Object savePoint;
	
	public boolean insertR(BoardVo vo) {
		status = manager.getTransaction(new DefaultTransactionDefinition());
		savePoint = status.createSavepoint();
		boolean flag = true;
		
		int cnt = mapper.insertR(vo);
		if(cnt<1) {
			//status.rollbackToSavepoint(savePoint);
			flag=false;
		}else if(vo.getAttList().size()>0) {
			int attCnt = mapper.insertAttList(vo.getAttList());
			if(attCnt<0) flag=false;
		}
		if(flag) {
			manager.commit(status);
		}else {
			status.rollbackToSavepoint(savePoint);
			
			String[] delFiles = new String[vo.getAttList().size()];
			for(int i=0; i<vo.getAttList().size(); i++) {
				delFiles[i] = vo.getAttList().get(i).getSysFile();
			}
			fileDelete(delFiles);
		}
		
		return flag;
	}
	
	public void insertAttList(List<AttVo> attList) {
		
		int cnt = mapper.insertAttList(attList);
		if(cnt>0) {
			manager.commit(status);
		}else {
			status.rollbackToSavepoint(savePoint);
		}
		
	}
	
	public boolean replR(BoardVo vo) {
		status = manager.getTransaction(new DefaultTransactionDefinition());
		savePoint = status.createSavepoint();
		
		boolean b = true;
		mapper.seqUp(vo);
		int cnt = mapper.replR(vo);
		if(cnt<1) {
			b=false;
		}else if(vo.getAttList().size()>0) {
			int attCnt = mapper.insertAttList(vo.getAttList());
			if(attCnt<0) b=false;
		}
		
		if(b) manager.commit(status);
		else {
			status.rollbackToSavepoint(savePoint);
			
			String[] delFiles = new String[vo.getAttList().size()];
			for(int i=0; i<vo.getAttList().size(); i++) {
				delFiles[i] = vo.getAttList().get(i).getSysFile();
			}
			fileDelete(delFiles);
		
		}
		return b;
	}
	
	 public boolean updateR(BoardVo bVo, String[] delFiles) {
		status = manager.getTransaction(new DefaultTransactionDefinition());
		savePoint = status.createSavepoint();
	    
		boolean b=true;
	    int cnt = mapper.update(bVo);
	    if(cnt<1) {
	        b=false;
	    }else if(bVo.getAttList().size()>0) {
	        int attCnt = mapper.attUpdate(bVo);	//카페북에 update로 되어있음. insert로 수정* (int attCnt = session.update("board.attUpdate", bVo);
	        if(attCnt<1) {
	        	b=false;
	        }
	    }
	       
	        
	    if(b) {
	    		manager.commit(status);
	            if(delFiles !=null && delFiles.length>0) {
	                // 첨부 파일 데이터 삭제
	                cnt = mapper.attDelete(delFiles);
	                if(cnt>0) {
	                    fileDelete(delFiles); // 파일 삭제
	                }else {
	                    b=false;
	                }
	            }
	        }else {
	        	status.rollbackToSavepoint(savePoint);
	        	
	        	delFiles = new String[bVo.getAttList().size()];
	        	for(int i=0; i<bVo.getAttList().size(); i++) {
	        		delFiles[i] = bVo.getAttList().get(i).getSysFile();
	        	}
	        	fileDelete(delFiles);
	        }
	        return b;
	 }
	
	public List<BoardVo> select2(PageVo pVo){
		int totSize = mapper.totList(pVo);
		pVo.setTotSize(totSize);
		this.pVo = pVo;	//서비스에서 sql문돌아간 데이터 받고 그때의 pVo를 this.pVo로 해준다음 리턴된 list를 보드컨트롤에서 getpVo해서 pVo를 한번 더 가져옴
		List<BoardVo> list = null;
		list = mapper.select2(pVo);
		return list;
	}
	
	public List<BoardVo> board10(){
		List<BoardVo> list = null;
		list = mapper.board10();
		return list;
	}
	
	public BoardVo view(int sno, String up) {
		BoardVo bVo =null;
		if(up != null && up.equals("up")) {
			mapper.hitUp(sno);
		}
		bVo = mapper.view(sno);
		List<AttVo> attList = mapper.attList(sno);
		bVo.setAttList(attList);
		
		return bVo;
	}
	
	//SPABoard Dao 참고
	public boolean delete(BoardVo bVo) {
		boolean b = true;
		
		//자신의 글에 댓글이 있는지 판단하기
		//같은 grp안에 자신의 seq보다 1더 큰 seq의 자료에서
		//deep이 자신 보다 큰 것이 있으면 댓글이 있는 것임.
		int replCnt = mapper.replCheck(bVo);
		
		if(replCnt>0) {
			b=false;
			return b;
		}
		//sno에 해당하는 테이블 삭제
		status = manager.getTransaction(new DefaultTransactionDefinition());
		Object savePoint = status.createSavepoint();
		
		int cnt = mapper.delete(bVo);
		if(cnt<1) {
			b=false;
		}else {
			//sno를 pSno로 바꾸어 첨부 테이블에서 첨부파일 목록 가져오기
			List<String> attList = mapper.delFileList(bVo.getSno());
			//첨부테이블 삭제
			if(attList.size()>0) {
				cnt = mapper.attDeleteAll(bVo.getSno());
				if(cnt>0) {
					//첨부파일 삭제
					if(attList.size()>0) {
						String[] delList = attList.toArray(new String[0]);
						System.out.println(("delList : ")+  Arrays.toString(delList));
						fileDelete(delList);
					}
				}else {
					b=false;
				}
			}
		}
		
		if(b) manager.commit(status);
		
		else status.rollbackToSavepoint(savePoint);
		
		return b;
	}
	
	public void fileDelete(String[] delFiles) {
		
		  for(String f : delFiles){
		  File file = new File(FileUploadController.path + f);
		  if(file.exists()) file.delete();
		  }
		 
	}
	
	//테스트로만든게시판select메서드
	public List<BoardVo> select(String findStr){
		List<BoardVo> list = null;
		list = mapper.select(findStr);
		return list;
	}
}

