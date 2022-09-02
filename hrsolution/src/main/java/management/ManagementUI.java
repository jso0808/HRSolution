package management;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;

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
						+ "4. 직급별 사원 리스트 5.채용담당자 리스트  6.역대 프로젝트 담당리스트 7. 현재 프로젝트 담당리스트 8.메인메뉴 =>");
				ch = Integer.parseInt(br.readLine());
				
				if(ch ==8) {
					return;
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
				case 6:
					totWorkingList();
					break;
				case 7:
					nowWorkingList();
					break;
				}
			} catch (Exception e) {
			}
		}
	}
	
	private void totWorkingList() {
		System.out.println("역대 프로젝트를 검색할 사번을 입력하세요");
		System.out.println("\n 역대프로젝트 리스트 ");
		System.out.println("------------------------------------------------------------");
		System.out.println("사번\t이름\t직위\t프로젝트 시작일\t\t프로젝트 종료일\t\t프로젝트 제목\t프로젝트 진행률\t프로젝트 번호\t프로젝트 내용");
		try {
			String id;
			id = br.readLine();
			List<ManagementDTO> list = dao.totWorking(id);
			
			for(ManagementDTO dto : list) {
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getPos() + "\t");
				
				System.out.print(dto.getProStart() + "\t");
				if(dto.getProEnd()== null) {
					System.out.print("진행중입니다" + "\t");
				} else {
					System.out.print(interval(dto.getProEnd()));
				}
				System.out.print(interval(dto.getProTitle()));
				System.out.print(dto.getProRate() + "\t");
				System.out.print(dto.getWorkNo() + "\t");
				System.out.println(interval(dto.getProject()));
			
			}
		} catch (Exception e) {
		}
		System.out.println();
	}

	 //em.id, name, position, w.proStart, w.proEnd,w.protitle, w.prorate, w.workno "
	 private void nowWorkingList() {
			System.out.println("\n 현재 프로젝트 진행중인 사원리스트 ");
			System.out.println("------------------------------------------------------------");
			System.out.println("사번\t이름\t직위\t프로젝트 시작일\t\t프로젝트 제목\t프로젝트 진행률\t프로젝트내용\t프로젝트 번호 ");
			try {
				List<ManagementDTO> list = dao.nowWorking();
				
				for(ManagementDTO dto : list) {
					System.out.print(dto.getId() + "\t");
					System.out.print(dto.getName() + "\t");
					System.out.print(dto.getPos() + "\t");
					System.out.print(dto.getProStart() + "\t");
					System.out.print(interval(dto.getProTitle()) + "\t");
					System.out.print(dto.getProRate() + "\t");
					System.out.println(dto.getProject() + "\t");
					System.out.print(interval(dto.getWorkNo()) + "\t");
				
				}
			} catch (Exception e) {
			}
			
			System.out.println();
			
		}
 public String interval(String s) {
	if(s.length()<8)
		System.out.print("\t\t");
	else if(s.length()<16){
		System.out.print("\t");
	}
	return s;
 }
	 
	private void totEmployeeList() {
		System.out.println("\n 전체 사원 리스트 ");
		System.out.println("------------------------------------------------------------");
		System.out.println("사번\t이름\t부서\t전화번호\t\t이메일\t\t신분(정규직/계약직)");
		try {
			List<ManagementDTO> list = dao.totEmpList();
			
			for(ManagementDTO dto : list) {
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
	
	private void deptEmployeeList() {
		System.out.println("\n 부서별 사원 리스트 ");
		System.out.println("------------------------------------------------------------");
		System.out.println("부서\t사번\t이름\t전화번호\t\t직급\t이메일\t\t신분(정규직/계약직)");
		
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
		System.out.println("------------------------------------------------------------");
		System.out.println("직급\t사번\t이름\t직급\t전화번호\t\t이메일\t\t신분(정규직/계약직)");
		
		try {
			List<ManagementDTO> list = dao.recList();
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
		System.out.println("------------------------------------------------------------");
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
		System.out.println("\n 조직도 리스트 ");
		System.out.println("------------------------------------------------------------");

		try {
			List<ManagementDTO> list = dao.organ();
			for(ManagementDTO dto : list) {
				System.out.println(dto.getDept());
			}
		} catch (Exception e) {
		}
		
		System.out.println();
	}

}
