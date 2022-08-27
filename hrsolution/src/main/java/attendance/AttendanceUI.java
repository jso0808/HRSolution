package attendance;

import employee.EmployeeDTO;

public class AttendanceUI {
	
	public void attendancemenu(EmployeeDTO empdto) {
		System.out.println("근태 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
	}

}
