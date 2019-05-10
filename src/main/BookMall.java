package main;

import java.util.List;

import dao.BookDao;
import dao.CartDao;
import dao.CategoryDao;
import dao.MemberDao;
import dao.OrderDao;
import vo.BookVo;
import vo.CartVo;
import vo.CategoryVo;
import vo.MemberVo;
import vo.OrderBookVo;
import vo.OrderVo;

public class BookMall {

	public static void main(String[] args) {
		// 데이터 등록은 test 패키지에서 실시하세요!
		
		// 아래 메서드는 데이터 조회 기능을 합니다.
		getCategoryList();
		getBookList();
		getMemberList();
		getCartList(1L);	// 1번 고객(김철수)의 카트 리스트 확인
		getOrderList(2L);	// 2번 고객(김영희)의 주문 내역 확인
		getOrderBookList(1L);	// 1번 주문의 도서 리스트 확인 
		
	}
	
	// 카테고리 리스트 출력
	public static void getCategoryList() {
		List<CategoryVo> list = new CategoryDao().getList();
		
		System.out.println("=========================\n * 카테고리 리스트 * \n=========================");
		System.out.println("카테고리 등록 번호와 카테고리명이 등록 순으로 출력됩니다.\n-------------------------");
		
		for(CategoryVo vo: list) {
			System.out.printf("번호: %d - 카테고리명: %s\n", vo.getNo(), vo.getName());
		}
		System.out.println("=========================\n");
	}
	
	// 서적 리스트 출력
	public static void getBookList() {
		List<BookVo> list = new BookDao().getList();
		
		System.out.println("=========================\n * 서적 리스트 * \n=========================");
		System.out.println("서적의 제목과 가격이 등록 순으로 출력됩니다.\n-------------------------");
		
		for(BookVo vo: list) {
			System.out.printf("책 제목: %s | 카테고리명: %s | 가격: %d원\n", vo.getTitle(), vo.getCategoryName(), vo.getPrice());
		}
		System.out.println("=========================\n");
	}
	
	// 고객 리스트 출력
	public static void getMemberList() {
		List<MemberVo> list = new MemberDao().getList();
		
		System.out.println("=========================\n * 고객 리스트 * \n=========================");
		System.out.println("고객의 이름, 전화번호, 이메일, 암호화된 비밀번호가 등록 순으로 출력됩니다.\n-------------------------");
		
		for(MemberVo vo: list) {
			System.out.printf("이름: %s | 전화번호: %s | 이메일: %s | 암호화된 비밀번호: %s\n", vo.getName(), vo.getPhone(), vo.getEmail(), vo.getPasswd());
		}
		System.out.println("=========================\n");
	}

	// 카트 리스트 출력
	public static void getCartList(Long memberNo) {
		List<CartVo> list = new CartDao().getCartBookList(memberNo);
		
		System.out.println("=========================\n * 카트 리스트 * \n=========================");
		System.out.println("카트에 넣은 도서 제목, 수량, 수량에 따른 도서의 가격이 도서의 등록 순으로 출력됩니다.\n-------------------------");
			
		for(CartVo vo: list) {
			System.out.printf("책 제목: %s | 수량: %d권 | 수량에 따른 가격: %d원\n", vo.getBookTitle(), vo.getAmount(), vo.getSumPrice());
		}
		System.out.println("=========================\n");
	}
	
	// 주문 리스트 출력
	public static void getOrderList(Long memberNo) {
		List<OrderVo> list = new OrderDao().getOrderList(memberNo);
		
		System.out.println("=========================\n * 주문 리스트 * \n=========================");
		System.out.println("주문 번호(고유번호), 비지니스용 주문 번호, 주문자의 이름과 이메일, 결제금액, 배송지가 주문 등록 순으로 출력됩니다.\n-------------------------");
		
		for(OrderVo vo: list) {
			System.out.printf("주문 번호: %d) %s | 주문자명: %s | 주문자 이메일: %s | 결제금액: %d원 | 배송지: %s\n", vo.getNo(), vo.getOrderNo(), vo.getMemberName(), vo.getMemberEmail(), vo.getPrice(), vo.getReceiveAddress());
		}
		System.out.println("=========================\n");
	}
	
	// 주문 도서 리스트 출력
	public static void getOrderBookList(Long orderNo) {
		List<OrderBookVo> list = new OrderDao().getOrderBookList(orderNo);
		
		System.out.println("=========================\n * 주문 도서 리스트 * \n=========================");
		System.out.println("주문한 도서 번호, 제목, 수량이 도서의 등록 순으로 출력됩니다.\n-------------------------");
		
		for(OrderBookVo vo: list) {
			System.out.printf("번호: %d | 책 제목: %s | 수량: %d권\n", vo.getBookNo(), vo.getBookTitle(), vo.getAmount());
		}
		System.out.println("=========================\n");
	}
	
}
