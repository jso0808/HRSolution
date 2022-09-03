package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;

public class ManagementUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private ManagementDAO dao = new ManagementDAOImpl();

	public void managementmenu(LoginDTO loginEmp) {
		System.out.println("종합 관리 메뉴 실행");
		System.out.println(loginEmp.getId() + " 님");

		int ch;

		while (true) {
			try {
				System.out.println("1.조직도 2.전체 사원 리스트 3. 부서별 사원 리스트 "
						+ "4. 직급별 사원 리스트 5.채용담당자 리스트  6.역대 프로젝트 담당리스트 7. 현재 프로젝트 담당리스트 8.메인메뉴 =>");
				ch = Integer.parseInt(br.readLine());

				if (ch == 8) {
					return;
				} else if (ch >= 9 || ch <= 0) {
					System.out.println("잘못입력하였습니다. 다시입력하세요");
				}

				switch (ch) {
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
			} catch (NumberFormatException e) {
				System.out.println("입력은 숫자만 가능합니다.다시입력하세요");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("올바른 숫자가 아닙니다. 다시입력하세요");
				e.printStackTrace();
			}
		}
	}

	private void totWorkingList() {
		System.out.println("역대 프로젝트를 검색할 사번을 입력하세요");
		System.out.println();
		try {
			String id;
			id = br.readLine();
			List<ManagementDTO> list = dao.totWorking(id);
			if (list.isEmpty()) {
				System.out.println("해당하는 정보가 존재하지 않습니다. ");
				return;
			}
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
			System.out.println("\n 역대프로젝트 검색결과");
			System.out.println("사번\t이름\t직위\t프로젝트 시작일\t\t프로젝트 종료일\t프로젝트 제목\t\t프로젝트 진행률\t프로젝트 번호\t프로젝트 내용");
			

			for (ManagementDTO dto : list) {
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getPos() + "\t");

				System.out.print(dto.getProStart() + "\t");
				if (dto.getProEnd().length()<= 3 ) {
					System.out.print("진행중입니다." + "\t");
				} else {
					System.out.print(dto.getProEnd() + "\t");
				}
				if (dto.getProTitle().length() <= 5) {
					System.out.print(dto.getProTitle() + "\t\t\t");
				} else if (dto.getProTitle().length() <= 11) {
					System.out.print(dto.getProTitle() + "\t\t");
				} else {
					System.out.print(dto.getProTitle() + "\t");
				}
				System.out.print(dto.getProRate() + "\t\t");
				System.out.print(dto.getWorkNo() + "\t\t");
				System.out.println(dto.getProject());

			}
		} catch (IOException e) {
			System.out.println("일치하는 사번이 없습니다.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("역대프로젝트 검색결과 에러");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

	private void nowWorkingList() {
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		try {
			List<ManagementDTO> list = dao.nowWorking();
			if (list.isEmpty()) {
				System.out.println("해당하는 정보가 존재하지 않습니다. ");
				return;
			}
			System.out.println("\n 현재 프로젝트 진행중인 사원리스트 ");
			System.out.println("사번\t이름\t직위\t프로젝트 시작일\t\t프로젝트 제목\t\t\t진행률\t프로젝트내용\t\t\t프로젝트 번호 ");
			
			for (ManagementDTO dto : list) {
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getProStart() + "\t");
				if (dto.getProTitle().length() <= 4) {
					System.out.print(dto.getProTitle() + "\t\t\t\t");
				} else if (dto.getProTitle().length() <= 11) {
					System.out.print(dto.getProTitle() + "\t\t\t");
				} else if (dto.getProTitle().length() <= 17) {
					System.out.print(dto.getProTitle() + "\t\t");
				} else
					System.out.print(dto.getProTitle() + "\t");
				System.out.print(dto.getProRate() + "\t");
				if (dto.getProject().length() <= 5) {
					System.out.print(dto.getProject() + "\t\t\t\t");
				} else if (dto.getProject().length() <= 11) {
					System.out.print(dto.getProject() + "\t\t\t");
				} else if (dto.getProject().length() <= 17) {
					System.out.print(dto.getProject() + "\t\t");
				} else
					System.out.print(dto.getProject() + "\t");
				System.out.println(dto.getWorkNo() + "\t");

			}
		} catch (Exception e) {
			System.out.println("현재 프로젝트 진행중인 사원리스트 에러");
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

	private void totEmployeeList() {
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 전체 사원 리스트 ");
		System.out.println("사번\t이름\t부서\t전화번호\t\t이메일\t\t\t신분(정규직/계약직)");
		try {
			List<ManagementDTO> list = dao.totEmpList();

			for (ManagementDTO dto : list) {
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if (dto.getEmail().length() <= 15)
					System.out.print("\t");
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

	private void deptEmployeeList() {
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 부서별 사원 리스트 ");
		System.out.println("부서\t사번\t이름\t전화번호\t\t직급\t이메일\t\t\t신분(정규직/계약직)");

		try {
			List<ManagementDTO> list = dao.deptEmpList();

			for (ManagementDTO dto : list) {
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if (dto.getEmail().length() <= 15)
					System.out.print("\t");
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

	private void recruitList() {
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 채용담당자 리스트 ");
		System.out.println("직급\t사번\t이름\t직급\t전화번호\t\t이메일\t\t\t신분(정규직/계약직)");

		try {
			List<ManagementDTO> list = dao.recList();
			for (ManagementDTO dto : list) {
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if (dto.getEmail().length() <= 15)
					System.out.print("\t");
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
			System.out.println("채용담당자 리스트 에러");
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

	private void rankEmployeeList() {
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 직급별 사원 리스트 ");
		System.out.println("직급\t사번\t이름\t직급\t전화번호\t\t이메일\t\t\t신분(정규직/계약직)");

		try {
			List<ManagementDTO> list = dao.rankEmpList();
			for (ManagementDTO dto : list) {
				System.out.print(dto.getPos() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getDept() + "\t");
				System.out.print(dto.getTel() + "\t");
				System.out.print(dto.getEmail() + "\t");
				if (dto.getEmail().length() <= 15)
					System.out.print("\t");
				System.out.println(dto.getFt() + "\t");
			}
		} catch (Exception e) {
			System.out.println("직급별 사원리스트 에러");
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

	private void organization() {
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 조직도 리스트 ");

		try {
			List<ManagementDTO> list = dao.organ();
			for (ManagementDTO dto : list) {
				System.out.println(dto.getDept());
			}
		} catch (Exception e) {
			System.out.println("조직도 에러");
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}

}
