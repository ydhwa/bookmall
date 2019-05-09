package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.OrderBookVo;
import vo.OrderVo;

public class OrderDao {

	public List<OrderVo> getOrderList(Long memberNo) {
		List<OrderVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select o.no, o.order_no, m.name, m.email, o.price, o.receive_address " + 
					"from orders o, member m " + 
					"where o.member_no = m.no " + 
					"	and m.no = ?";
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
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<OrderBookVo> getOrderBookList(Long orderNo) {
		List<OrderBookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select b.no, b.title, ob.amount " + 
					"from orders o, order_book ob, book b " + 
					"where o.no = ob.order_no " + 
					"	and b.no = ob.book_no " + 
					"    and o.no = ?";
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
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Boolean insertOrder(OrderVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into orders " + 
					"values(null, " + 
					"(select concat(date_format(now(), '%Y%m%d'), '-', lpad(ifnull(last_insert_id(), 1), 5, 0))), " + 
					"?, ?, '주문완료', ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getPrice());
			pstmt.setString(2, vo.getReceiveAddress());
			pstmt.setLong(3, vo.getMemberNo());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public Boolean insertOrderBook(OrderBookVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into order_book values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setLong(2, vo.getOrderNo());
			pstmt.setInt(3, vo.getAmount());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 중복된 코드 메소드로 빼기
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.1.48:3307/bookmall";
			conn = DriverManager.getConnection(url, "bookmall", "bookmall"); // url, username, password
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
