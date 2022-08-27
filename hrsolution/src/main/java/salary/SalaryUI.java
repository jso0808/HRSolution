package salary;

import employee.EmployeeDTO;

public class SalaryUI {
	
	public void salarymenu(EmployeeDTO empdto) {
		System.out.println("연봉 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
	}

}
