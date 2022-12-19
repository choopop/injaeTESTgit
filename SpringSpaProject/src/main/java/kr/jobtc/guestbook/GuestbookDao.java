package kr.jobtc.guestbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
//주로 공통기능에 사용되는 component는 메모리에만 저장되는형태 하드디스크->메모리에 올려서 실행하게된다.
//(@service어노테이션은 주로 특정기능, 비즈니스로직에 주로 사용된다.)
//repasitory의 경우는 하드디스크에 올려서 실행을하게된다.

@Component
@Transactional
public class GuestbookDao {
	GPageVo gVo;
	
	@Autowired
	PlatformTransactionManager manager;
	TransactionStatus status;
	
	@Autowired
	GuestbookMapper mapper;
	
	public int getTotSize(GPageVo gVo) {
		//int totSize=0;
		int totSize = mapper.totSize(gVo);
		return totSize;
	}
	
	public List<GuestbookVo> select(GPageVo gVo){
		List<GuestbookVo> list = null;
		int totSize = getTotSize(gVo);
		gVo.setTotSize(totSize);
		gVo.pageCompute();
		this.gVo=gVo;//새로 갱신된 페이지 정보
		list = mapper.list(gVo);
		
		return list;
	}
	
	public List<GuestbookVo> select10(){
		List<GuestbookVo> list = null;
		list = mapper.list10();
		return list;
	}
	
	public boolean insert(GuestbookVo vo) {
		boolean b = false;
		status = manager.getTransaction(new DefaultTransactionDefinition());
		Object savePoint = status.createSavepoint();
		
		int cnt = mapper.insert(vo);	//guestbook.xml에서 <insert id="insert"..>인설트 태그의 경우 cnt로 받을경우 int형으로만 가능하다
		
		if(cnt>0) {
			b=true;
			manager.commit(status);
		}else {
			status.rollbackToSavepoint(savePoint);
		}
		return b;
	}
	
	public boolean delete(GuestbookVo vo) {
		boolean b = false;
		status = manager.getTransaction(new DefaultTransactionDefinition());
		Object savePoint = status.createSavepoint();
		
		int cnt = mapper.delete(vo);
		
		if(cnt>0) {
			b=true;
			manager.commit(status);
		}else {
			status.rollbackToSavepoint(savePoint);
		}
		
		return b;
	}
	
	public boolean update(GuestbookVo vo) {
		boolean b = false;
		status = manager.getTransaction(new DefaultTransactionDefinition());
		Object savePoint = status.createSavepoint();
		
		int cnt = mapper.update(vo);
		
		if(cnt>0) {
			b=true;
			manager.commit(status);
		}else {
			status.rollbackToSavepoint(savePoint);
		}
		
		return b;
	}
	
	
	
	public GPageVo getGPageVo() {
		return gVo;	//새로 갱신된 페이지 정보 현재 여기 필드값의 gVo선언되있음
	}
	
}
