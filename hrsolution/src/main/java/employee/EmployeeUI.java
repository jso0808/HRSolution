package employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;
import com.main.ValidCheck;
import com.util.DBConn;

public class EmployeeUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private EmployeeDAO dao = new EmployeeDAOImpl();
	private LoginDTO loginEmp;
	private ValidCheck valchk = new ValidCheck();

	public void employeemenu(LoginDTO loginEmp) {
		this.loginEmp = loginEmp;

		System.out.println("사원 관리 메뉴 실행");
		System.out.println(loginEmp.getId() + " 님");

		int ch;
		while (true) {
			try {
				System.out.println("1.신입사원 등록 2.사원정보 수정 3.인사기록 확인 4.사원정보 삭제 5.업무이력 등록 및 수정  6.메인 ");
				ch = Integer.parseInt(br.readLine());
				if (ch == 6) {

					return;
				}

				switch (ch) {
				case 1:
					insert();
					break;
				case 2:
					update();
					break;
				case 3:
					findByid();
					break;
				case 4:
					delete();
					break;
				case 5:
					work();
					break;
				}
			} catch (Exception e) {
			}
		}
	}

	protected void insert() {

		System.out.println("\n **** 사원등록 **** ");

		if (Integer.parseInt(loginEmp.getPositionno()) < 3) {
			System.out.println("접근 권한이 없습니다. \n");
			return;
		}
		try {
			String tel,emp;
			String id, name, rrn, email, pareason;
			
			EmployeeDTO dto = new EmployeeDTO();
			
			/*
			System.out.println("         [ 부서코드 참고 ]");
			System.out.println("재무부:100 인사부:200 총무부:300 개발부: 400");
			System.out.println("마게팅부:500 기획부:600 유통부:700");
			*/
			
			while(true) {
				System.out.print("등록될 사원번호를 입력하세요 > ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setId(id);
					break;
				}
			}
			
			while(true) {
				System.out.print("등록 사유를 입력하세요 (ex.입사) > ");
				pareason = br.readLine();
				
				if(valchk.isKorean(pareason)==false) {
					System.out.println("한글만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setPaReason(pareason);
					break;
				}
			}
			
			while(true) {
				System.out.print("이름을 입력하세요 > ");
				name = br.readLine();
				
				if(valchk.isKorean(name)==false) {
					System.out.println("한글만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setName(name);
					break;
				}
			}
			
			while(true) {
				System.out.print("주민번호를 입력하세요 > ");
				rrn = br.readLine();
				
				if(valchk.isPersonalID(rrn)==false) {
					System.out.println("주민번호 형식 오류. 다시 입력해주세요. \n");
				} else {
					dto.setRrn(rrn);
					break;
				}
			}
			
			while(true) {
				System.out.print("이메일을 입력하세요 > ");
				email = br.readLine();
				
				if(valchk.isEmail(email)==false) {
					System.out.println("이메일 형식 오류. 다시 입력해주세요. \n");
				} else {
					dto.setEmail(email);
					break;
				}
			}
			
			while(true) {
				System.out.print("휴대폰 번호를 입력하세요 (-포함) > ");
				tel = br.readLine();
				
				if(valchk.isTel(tel)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setTel(tel);
					emp = tel;
					dto.setPwd(emp.substring(9));
					break;
				}
			}
		    
		    System.out.print("부서코드를 입력하세요" + "\n(재무부:100/인사부:200/총무부:300/개발부:400/마케팅부:500/기획부:600/유통부:700)");
			dto.setDept(br.readLine());
			
			System.out.print("직급코드를 입력하세요" + "\n(인턴:1/사원:2/대리:3/과장:4/차장:5/부장:6/본부장:7/대표이사:8)");
			dto.setPos(br.readLine());
			System.out.print("고용형태를 입력하세요" + "\t(정규직/계약직)");
			dto.setFt(br.readLine());

			System.out.print("입사날짜를 입력하세요");
			dto.setHireDate(br.readLine());

			System.out.print("퇴사날짜를 입력하세요");
			dto.setLeaveDate(br.readLine());

			System.out.print("근무상태를 입력하세요"+ "\t재직중/퇴사");
			dto.setNoWorking(br.readLine());

			dao.insertEmployee(dto);

			System.out.println("*****" + dto.getName() + "사원 등록완료! ******");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	protected void update() {
		System.out.println("\n**** 사원정보 수정 **** ");

		try {
			String id;
			EmployeeDTO dto = new EmployeeDTO();
			while(true) {
				System.out.print("수정할 사원번호를 입력하세요.");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setId(id);
					break;
				}
			}

			if (Integer.parseInt(loginEmp.getPositionno()) < 3 && !(loginEmp.getId().equals(dto.getId()))) {
				System.out.println("접근 권한이 없습니다. \n");
				return;
			}

			System.out.print("새로운 이름을 입력하세요.");
			dto.setName(br.readLine());

			System.out.print("새로운 주민등록번호를 입력하세요.");
			dto.setRrn(br.readLine());

			System.out.print("새로운 이메일을 입력하세요.");
			dto.setEmail(br.readLine());

			System.out.print("새로운 전화번호를 입력하세요.");
			dto.setTel(br.readLine());

			System.out.print("새로운 부서번호를 입력하세요." + "\n(재무부:100/인사부:200/총무부:300/개발부:400/마케팅부:500/기획부:600/유통부:700)");
			
			dto.setDept(br.readLine());
			
			System.out.print("새로운 직급번호를 입력하세요." + "\n(인턴:1/사원:2/대리:3/과장:4/차장:5/부장:6/본부장:7/대표이사:8)");
			dto.setPos(br.readLine());
			
			System.out.print("변경된 고용형태를 입력하세요."+ "(계약직/정규직)");
			dto.setFt(br.readLine());

			System.out.print("변경된 입사날짜를 입력하세요.");
			dto.setHireDate(br.readLine());

			System.out.print("새로운 퇴사날짜를 입력하세요.");
			dto.setLeaveDate(br.readLine());

			System.out.print("변경된 근무상태를 입력하세요."+"(재직중/퇴사)");
			
			dto.setNoWorking(br.readLine());

			int result = dao.updateEmployee(dto);
			
			if (result == 0) {
				System.out.println("등록된 사원정보가 없습니다");
			} else {
				System.out.println("사원정보가 수정되었습니다.");
			}

		} catch (Exception e) {
			System.out.println("사원정보 수정실패...!! ");
		}
		System.out.println();
	}


	protected void findByid() {
		System.out.println("\n**** 인사 기록 확인 ****");
		String id;
		EmployeeDTO dto = null;
		try {
			
			while(true) {
				System.out.print("검색할 사번을 입력하세요\n");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto = dao.readEmployee(id);
					break;
				}
			}

			if (dto == null) {
				System.out.println("등록된 사원정보가 없습니다.\n");
				return;
			}
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			System.out.println("*사원번호*\t 이름\t이메일\t            전화번호 \t\t부서\t직급\t고용형태\t입사날짜\t\t\t퇴사날짜\t근무상태");
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			System.out.print(dto.getId() + "\t");
			System.out.print(dto.getName() + "\t");
			System.out.print(dto.getEmail() + "   ");
			System.out.print(dto.getTel()+"   ");
			System.out.print("   "+dto.getDept() + "\t");
			System.out.print(dto.getPos() + "\t");
			System.out.print(dto.getFt() + "\t");
			System.out.print(dto.getHireDate() + "\t");
			System.out.print(dto.getLeaveDate() + "\t");
			System.out.println(dto.getNoWorking() + "\t");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("사원검색 실패");
		}
		System.out.println();
	}

	protected void delete() {
		System.out.println("\n**** 사원정보 삭제 ****");

		if (Integer.parseInt(loginEmp.getPositionno()) < 3) {
			System.out.println("접근 권한이 없습니다. \n");
			return;
		}
		String id;
		try {
			System.out.print("삭제할 사원번호를 입력해주세요.");
			id = br.readLine();
			int result = dao.deleteEmployee(id);

			if (result == 0) {
				System.out.println("등록된 사원이 아닙니다.");
			} else {
				System.out.println("사원정보를 삭제 했습니다.");
			}

		} catch (Exception e) {
			System.out.println("사원정보삭제 실패");
		}
		System.out.println();
	}

	protected void work() {

		int cha;
		while (true) {
			try {
				System.out.print("1.새로운 업무등록 2.업무수정 3.돌아가기");
				cha = Integer.parseInt(br.readLine());
				if (cha == 3) {

					return;
				}

				switch (cha) {
				case 1:
					insertwork();
					break;
				case 2:
					updatework();
					break;


				}
			} catch (Exception e) {
			}

		}
	}

	protected void insertwork() {
		System.out.println("\n **** 새로운 업무 등록 ****");

		try {

			if (Integer.parseInt(loginEmp.getPositionno()) < 3) {
				System.out.println("접근 권한이 없습니다.(대리직급이상 가능) \n");
				return;
			}

			EmployeeDTO dto = new EmployeeDTO();

		
			System.out.print("사원번호를 입력하세요.");
			dto.setId(br.readLine());
			
			System.out.print("업무코드를 입력하세요");
			dto.setWorkNo(br.readLine());

			System.out.print("담당업무 입력하세요");
			dto.setProTitle(br.readLine());

			System.out.print ("업무시작일을 입력하세요");
			dto.setProStart(br.readLine());

			System.out.print("업무종료일을 입력하세요");
			dto.setProEnd(br.readLine());
			
			System.out.print("업무진행상황을 입력하세요 (진행중/완료) ");
			dto.setProRate(br.readLine());

			System.out.print("*** 담당업무에 대한 설명 **** ");
			dto.setProject(br.readLine());

			dao.insertWorker(dto);

			System.out.println("*****" + dto.getId() + "님 업무등록완료! ******");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	protected void updatework() {
		
		System.out.println("\n**** 업무이력 수정 **** ");

		try {
		     String id;
		     EmployeeDTO dto = new EmployeeDTO();
			
			System.out.print("담당사원번호를 입력하세요.");
			id = br.readLine();
			
			
			if (
				Integer.parseInt(loginEmp.getPositionno()) < 3 && !(loginEmp.getId().equals(dto.getId()))) {
				System.out.println("접근 권한이 없습니다. \n");
				return;
			}
			
			
			List<EmployeeDTO> list = dao.listWork(id);
			
			System.out.println(id+"님의 업무 기록 ");
			System.out.println("★실무자번호★   업무번호\t사번\t 이름\t업무내용\t\t    업무시작일\t\t업무종료일\t\t업무진행상황");
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			
			for (EmployeeDTO empdao : list) {
				System.out.print("  ");
				System.out.print("★" + empdao.getWorkerNo() + "★\t");
				System.out.print("    "+empdao.getWorkNo()+"\t");
				System.out.print("      "+empdao.getId() + "     ");
				System.out.print(empdao.getName() + "\t");
				System.out.print(empdao.getProTitle()+ "\t"+"    ");
				System.out.print("     "+empdao.getProStart() + "\t");
				System.out.print(empdao.getProEnd() + "\t");
				System.out.println(empdao.getProRate());
			}
			System.out.println("------------------------------------------------------------------------------------------------------------------");

		
		//	EmployeeDTO dto = new EmployeeDTO();
			dto.setId(id);
			System.out.print("수정할 업무이력의 실무자번호를 입력하세요. ");
			dto.setWorkerNo(br.readLine());
		
			System.out.print("새로운 업무번호를 입력하세요. ");
			dto.setWorkNo(br.readLine());
			
			System.out.print("새로운 업무내용 입력하세요. ");
			dto.setProTitle(br.readLine());
			
			System.out.print("변경할 업무시작일을 입력하세요. ");
			dto.setProStart(br.readLine());
			
			System.out.print("변경할 업무종료일을 입력하세요. ");
		    dto.setProEnd(br.readLine());
			
		    System.out.print("업무진행상황 (진행중/완료) ");
		    dto.setProRate(br.readLine());
			
			System.out.print("**** 변경된 담당업무에 대한 설명 ****");
			dto.setProject(br.readLine());
			
			dao.updateWorker(dto);
			
			System.out.println("업무이력 수정 완료 !!! ");
			
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


