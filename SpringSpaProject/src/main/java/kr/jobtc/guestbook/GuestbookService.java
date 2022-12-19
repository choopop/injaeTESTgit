package kr.jobtc.guestbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestbookService {
	@Autowired
	GuestbookMapper mapper;
	
	
	public int totSize(GPageVo pVo) {
		int totSize=0;
		totSize = mapper.totSize(pVo);
		System.out.println(pVo.getTotSize());
		return totSize;
	}
	
	
	public List<GuestbookVo> select(GPageVo pVo){
		List<GuestbookVo> list = null;
		list = mapper.list(pVo);
		
		return list;
	}
}
