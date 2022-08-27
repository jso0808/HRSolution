package evaluation;

import employee.EmployeeDTO;

public class EvaluationUI {
	
	public void evaluationmenu(EmployeeDTO empdto) {
		System.out.println("평가 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
	}

}
