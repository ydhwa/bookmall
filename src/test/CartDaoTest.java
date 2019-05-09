package test;

import java.util.List;

import dao.CartDao;
import vo.CartVo;
import vo.CartVo;

public class CartDaoTest {

	public static void main(String[] args) {
		// 1번 고객의 카트 조회(일반 조회)
		getCartBookListTest(1L, false);
		// 1번 고객의 구매결정 한 책들 조회
		getCartBookListTest(1L, true);
		
		// 카트에 책 삽입 테스트.
		// 카트는 멤버 생성 시 자동으로 만들어지도록 했기 때문에 MemberDao에서 테스트함
		insertCartBookTest(2L, 1L, 3);
		insertCartBookTest(3L, 2L, 1);
	}
	
	// order = false: 특정 고객의 카트 안의 책들 get
	// order = true: 특정 고객의 카트 안의 책들 중 '구매결정'을 한 책만 get
	public static void getCartBookListTest(Long memberNo, Boolean order) {
		List<CartVo> list = new CartDao().getCartBookList(memberNo, order);
		
		for(CartVo vo: list) {
			System.out.println(vo);
		}
	}
	
	public static void insertCartBookTest(Long bookNo, Long memberNo, Integer bookAmount) {
		CartVo vo = new CartVo();
		vo.setBookNo(bookNo);
		vo.setMemberNo(memberNo);
		vo.setAmount(bookAmount);
		
		new CartDao().insertCartBook(vo);
	}
}
