package test;

import java.util.List;

import dao.CategoryDao;
import vo.CategoryVo;

public class CategoryDaoTest {

	public static void main(String[] args) {
		// 카테고리 삽입 테스트
		insertTest("문학");
		insertTest("경제");
		insertTest("컴퓨터/IT");
		
		// 카테고리 조회
		getListTest();		
	}
	
	public static void getListTest() {
		List<CategoryVo> list = new CategoryDao().getList();
		
		for(CategoryVo vo: list) {
			System.out.println(vo);
		}
	}
	
	public static void insertTest(String name) {
		CategoryVo vo = new CategoryVo();
		vo.setName(name);
		
		new CategoryDao().insert(vo);
	}
}
