package test;

import java.util.List;

import dao.MemberDao;
import vo.MemberVo;

public class MemberDaoTest {

	public static void main(String[] args) {
		// 멤버 삽입 테스트
		insertTest("김철수", "010-0000-0000", "kim@email.com", "kimkim");
		insertTest("이영희", "010-1111-1111", "yhlee@email.com", "younghee");
		
		// 멤버 조회
		getListTest();
	}
	
	public static void getListTest() {
		List<MemberVo> list = new MemberDao().getList();
		
		for(MemberVo vo: list) {
			System.out.println(vo);
		}
	}
	
	public static void insertTest(String name, String phone, String email, String passwd) {
		MemberVo vo = new MemberVo();
		vo.setName(name);
		vo.setPhone(phone);
		vo.setEmail(email);
		vo.setPasswd(passwd);
		
		new MemberDao().insert(vo);
	}
}
