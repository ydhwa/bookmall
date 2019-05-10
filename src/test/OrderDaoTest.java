package test;

import dao.OrderDao;
import vo.CartVo;
import vo.OrderBookVo;
import vo.OrderVo;

public class OrderDaoTest {

	public static void main(String[] args) {
		insertOrderTest();
		insertOrderFromCartTest();
	}
	
	// 회원이 그냥 바로 주문할 때 테스트
	// 이영희 - 경기도 남양주시 화도읍으로 [톰캣-3/전설-4] 주문
	public static void insertOrderTest() {
		OrderVo orderVo = null;
		OrderBookVo orderBookVo = null;
		
		// 주문 삽입
		orderVo = new OrderVo();
		orderVo.setReceiveAddress("경기도 남양주시 화도읍");
		orderVo.setMemberNo(2L);
		new OrderDao().insertOrder(orderVo);
		
		// 책 삽입
		orderBookVo = new OrderBookVo();
		orderBookVo.setBookNo(2L);
		orderBookVo.setAmount(3);
		new OrderDao().insertOrderBook(orderBookVo);
		
		orderBookVo = new OrderBookVo();
		orderBookVo.setBookNo(1L);
		orderBookVo.setAmount(4);
		new OrderDao().insertOrderBook(orderBookVo);
		
		// 주문 금액 갱신 (할인되는 금액은 0원이라 가정)
		new OrderDao().updateOrderPrice(0);
	}
	
	// 회원이 카트에 있는 상품을 선택하여 주문할 때 테스트
	// 김철수 - 강원도 춘천시 퇴계로로 [톰캣-1/찰리-3] 주문
	public static void insertOrderFromCartTest() {
		OrderVo orderVo = null;
		CartVo cartVo = null;
		
		// 카트 안의 항목 구매 결정
		cartVo = new CartVo();
		cartVo.setBookNo(3L);
		cartVo.setMemberNo(1L);
		new OrderDao().updateCartBookOrderOk(cartVo);
		
		cartVo = new CartVo();
		cartVo.setBookNo(2L);
		cartVo.setMemberNo(1L);
		new OrderDao().updateCartBookOrderOk(cartVo);
		
		// 주문 삽입
		orderVo = new OrderVo();
		orderVo.setReceiveAddress("강원도 춘천시 퇴계로");
		orderVo.setMemberNo(1L);
		new OrderDao().insertOrder(orderVo);
		
		// 구매 결정한 항목들 주문에 삽입
		new OrderDao().insertOrderBookFromCart(1L);
		
		// 주문 금액 갱신 (할인되는 금액은 0원이라 가정)
		new OrderDao().updateOrderPrice(0);
		
	}
}
