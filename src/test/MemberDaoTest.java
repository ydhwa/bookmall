package test;

import dao.MemberDao;
import vo.MemberVo;

public class MemberDaoTest {

	public static void main(String[] args) {
		// 멤버 삽입 테스트
		insertTest();
	}
	
	public static void insertTest() {
		MemberVo vo = new MemberVo();
		vo.setName("김철수");
		vo.setPhone("010-0000-0000");
		vo.setEmail("kim@email.com");
		vo.setPasswd("kimkim");
		new MemberDao().insert(vo);
		
		vo = new MemberVo();
		vo.setName("이영희");
		vo.setPhone("010-1111-1111");
		vo.setEmail("yhlee@email.com");
		vo.setPasswd("younghee");
		new MemberDao().insert(vo);
	}
}
