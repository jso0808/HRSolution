package evaluation;

import com.main.LoginDTO;


public class EvaluationUI {
	
	public void evaluationmenu(LoginDTO loginEmp) {
		System.out.println("평가 관리 메뉴 실행");
		System.out.println(loginEmp.getId()+" 님");
	}

}
