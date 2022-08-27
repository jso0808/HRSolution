package recruit;

import employee.EmployeeDTO;

public class RecruitUI {
	
	public void recruitmenu(EmployeeDTO empdto) {
		System.out.println("채용 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
	}

}
