package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.MemberVo;

public class MemberDao {
	// 고객 삽입
	public Boolean insert(MemberVo vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CustomConnector.getConnection();

			String sql = "insert into member values(null, ?, ?, ?, password(?))";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPhone());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getPasswd());

			result = (1 == pstmt.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			CustomConnector.closeConnection(null, pstmt, conn);
		}

		return result;
	}

	// 고객 리스트 조회
	public List<MemberVo> getList() {
		List<MemberVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = CustomConnector.getConnection();
			String sql = "select name, phone, email, passwd from member order by no asc";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String phone = rs.getString(2);
				String email = rs.getString(3);
				String passwd = rs.getString(4);

				MemberVo vo = new MemberVo();
				vo.setName(name);
				vo.setPhone(phone);
				vo.setEmail(email);
				vo.setPasswd(passwd);

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
