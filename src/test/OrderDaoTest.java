package test;

import java.util.List;

import dao.OrderDao;
import vo.OrderBookVo;
import vo.OrderVo;

public class OrderDaoTest {

	public static void main(String[] args) {
		// 특정 고객의 주문 조회
		getOrderListTest(1L);
		
		// 특정 고객의 주문에서 주문한 책들 조회
		getOrderBookListTest(1L);
		
		// 주문 삽입 테스트
		insertOrderTest(0, "강원도 춘천시", "주문완료", 1L);
		// 책 주문 목록에 삽입하는 테스트
		insertOrderBookTest(1L, 1L, 2);
	}

	public static void getOrderListTest(Long memberNo) {
		List<OrderVo> list = new OrderDao().getOrderList(memberNo);
		
		for(OrderVo vo: list) {
			System.out.println(vo);
		}
	}
	
	public static void getOrderBookListTest(Long memberNo) {
		List<OrderBookVo> list = new OrderDao().getOrderBookList(memberNo);
		
		for(OrderBookVo vo: list) {
			System.out.println(vo);
		}
	}
	
	public static void insertOrderTest(Integer price, String receiveAddress, String status, Long memberNo) {
		OrderVo vo = new OrderVo();
		vo.setPrice(price);
		vo.setReceiveAddress(receiveAddress);
		vo.setStatus(status);
		vo.setMemberNo(memberNo);
		
		new OrderDao().insertOrder(vo);
	}
	
	private static void insertOrderBookTest(Long bookNo, Long orderNo, Integer amount) {
		OrderBookVo vo = new OrderBookVo();
		vo.setBookNo(bookNo);
		vo.setOrderNo(orderNo);
		vo.setAmount(amount);
		
		new OrderDao().insertOrderBook(vo);
	}
}
