package management;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;
import com.main.MainUI;

import employee.EmployeeDTO;

public class ManagementUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private ManagementDAO dao = new ManagementDAOImpl();
	public void managementmenu(LoginDTO loginEmp) {
		System.out.println("종합 관리 메뉴 실행");
		System.out.println(loginEmp.getId()+" 님");
		
		int ch;
		
		while(true) {
			try {
				System.out.println("1.조직도 2.전체 사원 리스트 3. 부서별 사원 리스트 "
						+ "4. 직급별 사원 리스트 5.채용담당자 리스트 6.메인메뉴 =>");
				ch = Integer.parseInt(br.readLine());
				
				if(ch ==6) {
					MainUI MainUI = new MainUI();
					MainUI.startmenu();
				}
				
				switch(ch) {
				case 1:
					organization();
					break;
				case 2:
					totEmployeeList();
					break;
				case 3:
					deptEmployeeList();
					break;
				case 4:
					rankEmployeeList();
					break;
				case 5:
					recruitList();
					break;
				}
			} catch (Exception e) {
			}
		}
	}
	
	private void totEmployeeList() {
		System.out.println("\n 전체 사원 리스트 ");
		System.out.println("----------------------------");
		System.out.println("사번\t이름\t전화번호\t\t이메일\t\t신분(정규직/계약직)");
		try {
			List<ManagementDTO> list = dao.totEmpList();
			
			for(ManagementDTO dto : list) {
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if(dto.getEmail().length()<=15)
					System.out.print("\t");	
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
		}
		
		System.out.println();
	}
	
	private void deptEmployeeList() {
		System.out.println("\n 부서별 사원 리스트 ");
		System.out.println("----------------------------");
		System.out.println("부서\t사번\t이름\t전화번호\t직급\t\t이메일\t\t신분(정규직/계약직)");
		
		try {
			List<ManagementDTO> list = dao.deptEmpList();
			
			for(ManagementDTO dto : list) {
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if(dto.getEmail().length()<=15)
					System.out.print("\t");	
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
		}
		
		System.out.println();
		
	}
	private void recruitList() {
		System.out.println("\n 채용담당자 리스트 ");
		System.out.println("----------------------------");
		System.out.println("직급\t사번\t이름\t직급\t전화번호\t\t이메일\t\t신분(정규직/계약직)");
		
		try {
			List<ManagementDTO> list = dao.deptEmpList();
			for(ManagementDTO dto : list) {
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if(dto.getEmail().length()<=15)
					System.out.print("\t");	
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
		}
		
		System.out.println();
		
	}

	private void rankEmployeeList() {
		System.out.println("\n 직급별 사원 리스트 ");
		System.out.println("----------------------------");
		System.out.println("직급\t사번\t이름\t직급\t전화번호\t\t이메일\t\t신분(정규직/계약직)");
		
		try {
			List<ManagementDTO> list = dao.rankEmpList();
			//position, em.id, name,dept,tel,email,ft 
			for(ManagementDTO dto : list) {
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if(dto.getEmail().length()<=15)
					System.out.print("\t");	
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
		}
		
		System.out.println();
		
	}

	private void organization() {
		
	}

}
