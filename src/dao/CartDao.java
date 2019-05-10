package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.CartVo;

public class CartDao {
	// 카트에 넣은 책들 리스트 조회
	public List<CartVo> getCartBookList(Long memberNo) {
		List<CartVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = CustomConnector.getConnection();
			String sql =
					"select b.title, c.amount, (b.price * c.amount) " + 
					"from cart c, book b, member m " + 
					"where c.book_no = b.no " + 
						"and c.member_no = m.no " + 
						"and m.no = ? " +
						"order by b.no asc";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, memberNo);
			
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
			CustomConnector.closeConnection(rs, pstmt, conn);
		}
		return result;
	}

	// 카트에 책 삽입
	public Boolean insertCartBook(CartVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = CustomConnector.getConnection();

			String sql = "insert into cart(book_no, member_no, amount) values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setLong(2, vo.getMemberNo());
			pstmt.setInt(3, vo.getAmount());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}
}
