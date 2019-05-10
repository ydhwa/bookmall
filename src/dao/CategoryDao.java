package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.CategoryVo;

public class CategoryDao {
	// 카테고리 삽입
	public Boolean insert(CategoryVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CustomConnector.getConnection();

			String sql = "insert into category values(null, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}

	// 카테고리 목록 조회
	public List<CategoryVo> getList() {
		List<CategoryVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = CustomConnector.getConnection();
			String sql = "select no, name from category order by no asc";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);

				CategoryVo vo = new CategoryVo();
				vo.setNo(no);
				vo.setName(name);

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
