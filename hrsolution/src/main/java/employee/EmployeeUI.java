package employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;
import com.util.DBConn;

public class EmployeeUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private EmployeeDAO dao = new EmployeeDAOImpl();
	private LoginDTO loginEmp;

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
				case 6:
					returntomain();
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
			String pwd,tel,emp;
			
			EmployeeDTO dto = new EmployeeDTO();

			System.out.print("사원번호를 입력하세요.");
			dto.setId(br.readLine());

			System.out.print("이름을 입력하세요");
			dto.setName(br.readLine());

			System.out.print("주민번호를 입력하세요");
			dto.setRrn(br.readLine());

			System.out.print("이메일을 입력하세요");
			dto.setEmail(br.readLine());

			System.out.print("전화번호를 입력하세요");
			tel = br.readLine();
			dto.setTel(tel);
			emp = tel;
			
		    dto.setPwd(emp.substring(9));

			System.out.print("고용형태를 입력하세요");
			dto.setFt(br.readLine());

			System.out.print("입사날짜를 입력하세요");
			dto.setHireDate(br.readLine());

			System.out.print("퇴사날짜를 입력하세요");
			dto.setLeaveDate(br.readLine());

			System.out.print("근무상태를 입력하세요");
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
			EmployeeDTO dto = new EmployeeDTO();
			System.out.print("수정할 사원번호를 입력하세요.");
			dto.setId(br.readLine());

			if (Integer.parseInt(loginEmp.getPositionno()) < 3 && !(loginEmp.getId().equals(dto.getId()))) {
				System.out.println("접근 권한이 없습니다. \n");
				return;
			}

			System.out.print("새로운 비밀번호를 입력하세요.");
			dto.setPwd(br.readLine());

			System.out.print("새로운 이름을 입력하세요.");
			dto.setName(br.readLine());

			System.out.print("새로운 주민등록번호를 입력하세요.");
			dto.setRrn(br.readLine());

			System.out.print("새로운 이메일을 입력하세요.");
			dto.setEmail(br.readLine());

			System.out.print("새로운 전화번호를 입력하세요.");
			dto.setTel(br.readLine());

			System.out.print("변경된 고용형태를 입력하세요.");
			dto.setFt(br.readLine());

			System.out.print("변경된 입사날짜를 입력하세요.");
			dto.setHireDate(br.readLine());

			System.out.print("새로운 퇴사날짜를 입력하세요.");
			dto.setLeaveDate(br.readLine());

			System.out.print("변경된 근무상태를 입력하세요.");
			dto.setNoWorking(br.readLine());

			int result =+ dao.updateEmployee(dto);
			
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
		try {
			System.out.print("검색할 사번을 입력하세요");
			id = br.readLine();
			EmployeeDTO dto = dao.readEmployee(id);

			if (dto == null) {
				System.out.println("등록된 사원정보가 없습니다.\n");
				return;
			}

			// System.out.println("아이디\t이름 t\t이름\t생년월일\t이메일\t전화번호");
			// System.out.println("--------------------------------------------------");
			System.out.print(dto.getId() + "\t");
			System.out.print(dto.getName() + "\t");
			System.out.print(dto.getRrn() + "\t");
			System.out.print(dto.getEmail() + "\t");
			System.out.print(dto.getTel() + "\t");
			System.out.print(dto.getDept() + "\t");
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
				case 3:
					returntomain();

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
			
			System.out.print("업무진행상황을 입력하세요");
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
			
			if (Integer.parseInt(loginEmp.getPositionno()) < 3 && !(loginEmp.getId().equals(dto.getId()))) {
				System.out.println("접근 권한이 없습니다. \n");
				return;
			}
			
			List<EmployeeDTO> elist = dto.listWork();
			
			System.out.println(id+"님의 업무 기록 ");
			System.out.println("★업무번호★\t사번\t이름\t업무내용\t업무시작\t업무종료\t업무진행상황");
			System.out.println("------------------------------------------------------------------------");
			
			for (EmployeeDTO empdao : elist) {
				System.out.print("★" + empdao.getWorkNo() + "★\t");
				System.out.print(empdao.getId() + "\t");
				System.out.print(empdao.getName() + "\t");
				System.out.print(empdao.getProject()+ "\t");
				System.out.print(empdao.getProStart() + "\t");
				System.out.print(empdao.getProEnd() + "\t");
				System.out.println(empdao.getProject());
			}
			System.out.println("------------------------------------------------------------------------");
		

			dto.setId(id);
			System.out.print("수정할 데이터의 업무번호는 ? ");
			dto.setWorkNo(br.readLine());
			
			System.out.print("새로운 담당업무를 입력하세요. ");
			dto.setProTitle(br.readLine());
			System.out.print("변경할 업무시작일을 입력하세요. ");
			dto.setProStart(br.readLine());
			
			System.out.print("변경할 업무종료일을 입력하세요. ");
		    dto.setProEnd(br.readLine());
			
			
			System.out.print("**** 변경된 담당업무에 대한 설명 ****");
			dto.setProject(br.readLine());
			
			dao.updateWorker(dto);
			
			System.out.println("업무이력 수정 완료 !!! ");
			

			int result = dao.updateWorker(dto);
			
			if (result == 0) {
				System.out.println("등록된 사원정보가 없습니다");
			} else {
				System.out.println("업무이력이 수정되었습니다.");
			}

		} catch (Exception e) {
			System.out.println("등록된 업무이력이 없습니다.");
		}
		System.out.println();
	}



	protected void returntomain() {

	}

}

/*@Override
	public void updateSalary(SalaryDTO sdto) throws SQLException {
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = null;
		
		
		try {	

			// 자동 커밋되지 않도록
			conn.setAutoCommit(false);
			// 현재 연봉 정보 수정
			sql = "UPDATE Salary SET sal=?,salStart=?,salEnd=?,memo=? "
					+ " WHERE salno=(SELECT MAX(salNo) FROM Salary WHERE salNo=?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, sdto.getSal());
			pstmt.setString(2, sdto.getSalStart());
			pstmt.setString(3, sdto.getSalEnd());
			pstmt.setString(4, sdto.getMemo());
			pstmt.setString(5, sdto.getSalNo());

			rs = pstmt.executeUpdate();
			pstmt.close();
			
			// 커밋 
			conn.commit();
			
			System.out.println("연봉 수정 완료 !!! ");
			
		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			
			
			if(e.getErrorCode() == 1400) { // NOT NULL
				System.out.println("필수 입력 사항을 입력하지 않았습니다. ");
			} else {
				System.out.println(e.toString());
			}
			
			throw e;
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		
	}
*/
