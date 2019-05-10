package test;

import dao.BookDao;
import vo.BookVo;

public class BookDaoTest {

	public static void main(String[] args) {
		// 책 삽입 테스트
		insertTest();	
	}
	
	public static void insertTest() {
		BookVo vo = new BookVo();
		vo.setTitle("전설로 떠나는 월가의 영웅");
		vo.setPrice(14620);
		vo.setCategoryNo(2L);
		new BookDao().insert(vo);
		
		vo = new BookVo();
		vo.setTitle("톰캣 최종분석");
		vo.setPrice(24000);
		vo.setCategoryNo(3L);
		new BookDao().insert(vo);
		
		vo = new BookVo();
		vo.setTitle("찰리와 초콜릿 공장");
		vo.setPrice(9000);
		vo.setCategoryNo(1L);
		new BookDao().insert(vo);
	}
}
