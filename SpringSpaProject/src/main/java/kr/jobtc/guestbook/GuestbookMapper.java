package kr.jobtc.guestbook;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GuestbookMapper {
	public int totSize(GPageVo vo);
	public List<GuestbookVo> list(GPageVo vo);	//guestbook.xml의 select id="list" 인노울 실행
//			  resultType,     Id,parametertype
	//public List<GuestbookVo>->result type,
	// list  -> id,
	//(GPageVo vo) -> parameter type;
	public int insert(GuestbookVo vo);
	public int delete(GuestbookVo vo);//여기서의 메서드명은 xml에서의 아이디와 일치해야함
	public int update(GuestbookVo vo);
	public List<GuestbookVo> list10();

}
