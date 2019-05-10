package test;

import dao.CartDao;
import vo.CartVo;

public class CartDaoTest {

	public static void main(String[] args) {		
		// 카트에 책 삽입 테스트.
		insertCartBookTest();
	}

	public static void insertCartBookTest() {
		// 김철수 (전설-2/찰리-3/톰캣-1)
		CartVo vo = new CartVo();
		vo.setBookNo(1L);
		vo.setMemberNo(1L);
		vo.setAmount(2);
		new CartDao().insertCartBook(vo);
		
		vo = new CartVo();
		vo.setBookNo(3L);
		vo.setMemberNo(1L);
		vo.setAmount(3);
		new CartDao().insertCartBook(vo);
		
		vo = new CartVo();
		vo.setBookNo(2L);
		vo.setMemberNo(1L);
		vo.setAmount(1);
		new CartDao().insertCartBook(vo);
		
		// 이영희 (찰리-1/톰캣-6)
		vo = new CartVo();
		vo.setBookNo(3L);
		vo.setMemberNo(2L);
		vo.setAmount(1);
		new CartDao().insertCartBook(vo);
		
		vo = new CartVo();
		vo.setBookNo(2L);
		vo.setMemberNo(2L);
		vo.setAmount(6);
		new CartDao().insertCartBook(vo);
	}
}
