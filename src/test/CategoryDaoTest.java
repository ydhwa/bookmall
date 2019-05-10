package test;

import dao.CategoryDao;
import vo.CategoryVo;

public class CategoryDaoTest {

	public static void main(String[] args) {
		// 카테고리 삽입 테스트
		insertTest();		
	}
	
	public static void insertTest() {
		CategoryVo vo = new CategoryVo();
		vo.setName("문학");
		new CategoryDao().insert(vo);
		
		vo = new CategoryVo();
		vo.setName("경제");
		new CategoryDao().insert(vo);
		
		vo = new CategoryVo();
		vo.setName("컴퓨터/IT");
		new CategoryDao().insert(vo);
	}
}
