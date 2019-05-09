package test;

import java.util.List;

import dao.BookDao;
import vo.BookVo;
import vo.CategoryVo;

public class BookDaoTest {

	public static void main(String[] args) {
		// 책 삽입 테스트
		insertTest("전설로 떠나는 월가의 영웅", 23000, 2L);
		insertTest("톰캣 최종분석", 24000, 3L);
		insertTest("찰리와 초콜릿 공장", 8800, 1L);
		
		// 책 조회
		getListTest();		
	}
	
	public static void getListTest() {
		List<BookVo> list = new BookDao().getList();
		
		for(BookVo vo: list) {
			System.out.println(vo);
		}
	}
	
	public static void insertTest(String title, Integer price, Long categoryNo) {
		BookVo vo = new BookVo();
		vo.setTitle(title);
		vo.setPrice(price);
		vo.setCategoryNo(categoryNo);
		
		new BookDao().insert(vo);
	}
}
