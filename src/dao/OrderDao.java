package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.CartVo;
import vo.OrderBookVo;
import vo.OrderVo;

public class OrderDao {

	// 주문 리스트 조회
	public List<OrderVo> getOrderList(Long memberNo) {
		List<OrderVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = CustomConnector.getConnection();
			String sql = 
					"select o.no, o.order_no, m.name, m.email, o.price, o.receive_address " + 
					"from orders o, member m " + 
					"where o.member_no = m.no " + 
						"and m.no = ? " + 
					"order by o.order_no asc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberNo);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String orderNo = rs.getString(2);
				String memberName = rs.getString(3);
				String memberEmail = rs.getString(4);
				Integer price = rs.getInt(5);
				String receiveAddress = rs.getString(6);

				OrderVo vo = new OrderVo();
				vo.setNo(no);
				vo.setOrderNo(orderNo);
				vo.setMemberName(memberName);
				vo.setMemberEmail(memberEmail);
				vo.setPrice(price);
				vo.setReceiveAddress(receiveAddress);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(rs, pstmt, conn);
		}
		return result;
	}

	// 특정 주문에 있는 책 리스트 조회
	public List<OrderBookVo> getOrderBookList(Long orderNo) {
		List<OrderBookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = CustomConnector.getConnection();
			String sql = 
					"select b.no, b.title, ob.amount " + 
					"from orders o, order_book ob, book b " + 
					"where o.no = ob.order_no " + 
						"and b.no = ob.book_no " + 
						"and o.no = ? " + 
						"order by b.no asc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, orderNo);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long bookNo = rs.getLong(1);
				String bookTitle = rs.getString(2);
				Integer amount = rs.getInt(3);

				OrderBookVo vo = new OrderBookVo();
				vo.setBookNo(bookNo);
				vo.setBookTitle(bookTitle);
				vo.setAmount(amount);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(rs, pstmt, conn);
		}
		return result;
	}

	// 주문 삽입
	public Boolean insertOrder(OrderVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = CustomConnector.getConnection();

			String sql = 
					"insert into orders(order_no, receive_address, member_no) " + 
					"values(concat(date_format(now(), '%Y%m%d'), '-', lpad((select count(*) + 1 from orders o where (select substr(order_no, 1, 8) from orders oo where oo.no = o.no) = date_format(now(), '%Y%m%d')), 5, 0)), ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getReceiveAddress());
			pstmt.setLong(2, vo.getMemberNo());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}
	
	// 특정 주문에 책 삽입
	public Boolean insertOrderBook(OrderBookVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = CustomConnector.getConnection();

			String sql = "insert into order_book values(?, (select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders'), ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setInt(2, vo.getAmount());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}
	
	// 카트에 담겨 있는 책의 구매결정을 시킴 -> 구매결정 한것만 주문에 넘어가도록 하기 위함
	public Boolean updateCartBookOrderOk(CartVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = CustomConnector.getConnection();

			String sql = 
					"update cart set order_ok = true " + 
					"where book_no = ? " + 
						"and member_no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setLong(2, vo.getMemberNo());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}
	
	// 특정 주문에 특정 멤버의 카트에 있는 책들 삽입 (멤버 번호를 사용한다.)
	public Boolean insertOrderBookFromCart(Long memberNo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = CustomConnector.getConnection();

			String sql = 
					"insert into order_book(book_no, order_no, amount) " +
						"select book_no, (select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders'), amount " + 
						"from cart " + 
						"where order_ok = true " + 
							"and member_no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberNo);

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}
	
	// 최종 계산 금액 갱신(주문 뒤 바로 갱신됨) (아직 discount는 고려 안함(=0으로 설정) - discount 추가할 경우 파라미터 추가)
	public Boolean updateOrderPrice(Integer discount) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = CustomConnector.getConnection();

			String sql =
					"update orders " + 
					"set price = ((select sum(b.price * ob.amount) from order_book ob, book b where ob.order_no = (select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders') and b.no = ob.book_no group by ob.order_no) - ?) " + 
					"where no = (select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders')";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, discount);

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}
}
