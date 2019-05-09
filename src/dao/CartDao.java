package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.CartVo;

public class CartDao {
	public List<CartVo> getCartBookList(Long memberNo, Boolean order) {
		List<CartVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select b.title, c.amount, (b.price * c.amount) " + 
					"from cart c, book b, member m " + 
					"where c.book_no = b.no " + 
					"	and c.member_no = m.no " + 
					"   and m.no = ?" + (order ? " and c.order = true" : "");
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String bookTitle = rs.getString(1);
				Integer amount = rs.getInt(2);
				Integer sumPrice = rs.getInt(3);
				
				CartVo vo = new CartVo();
				vo.setBookTitle(bookTitle);
				vo.setAmount(amount);
				vo.setSumPrice(sumPrice);

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

	public Boolean insertCartBook(CartVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into cart(book_no, member_no, amount) values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setLong(2, vo.getMemberNo());
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
