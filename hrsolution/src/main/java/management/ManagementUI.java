package management;

import employee.EmployeeDTO;

public class ManagementUI {
	
	public void managementmenu(EmployeeDTO empdto) {
		System.out.println("종합 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
	}

}
