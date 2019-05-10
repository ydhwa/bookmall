package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BookVo;

public class BookDao {
	// 책 삽입
	public Boolean insert(BookVo vo) {
		Boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CustomConnector.getConnection();

			String sql = "insert into book values(null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setLong(3, vo.getCategoryNo());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}

	// 책 리스트 조회
	public List<BookVo> getList() {
		List<BookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = CustomConnector.getConnection();
			String sql = "select b.title, c.name, b.price from book b, category c where c.no = b.category_no order by b.no asc";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString(1);
				String categoryName = rs.getString(2);
				Integer price = rs.getInt(3);

				BookVo vo = new BookVo();
				vo.setTitle(title);
				vo.setCategoryName(categoryName);
				vo.setPrice(price);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(rs, pstmt, conn);
		}
		return result;
	}
}
