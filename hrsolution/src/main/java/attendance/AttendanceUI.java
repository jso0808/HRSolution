package attendance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;

public class AttendanceUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private AttendanceDAO dao = new AttendanceDAOImpl();
	private LoginDTO logindto = null;
	
	public void attendancemenu(LoginDTO loginEmp) {
		logindto = loginEmp;
		int ch;
		
		while (true) {
			ch = 0;
			do {
				System.out.println("\n※근태 관리 메뉴 실행※");
				System.out.println(loginEmp.getId() + " 님");
				System.out.println("1.출퇴근시간 등록 2.출퇴근시간 수정 3.출퇴근시간 삭제 4.전체 근로내역 출력 5.개인별 근무이력 확인 6.메인메뉴 => ");
				try {
					ch = Integer.parseInt(br.readLine());
					
					if (ch == 6) {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (ch < 1 || ch > 5);

		
		switch(ch){
		case 1:
			if (loginEmp.getDeptno().equals("300")) {
				insertattendance();
			} else {
				System.out.println("접근 권한이 없습니다.\n");
				continue;
			}
			break;
			
		case 2:
			if (loginEmp.getDeptno().equals("300")) {
				updateattendance();
			} else {
				System.out.println("접근 권한이 없습니다.\n");
				continue;
			}
			break;
			
		case 3:
			if (loginEmp.getDeptno().equals("300")) {
				deleteattendance();
			} else {
				System.out.println("접근 권한이 없습니다.\n");
				continue;
			}
			break;
			
		case 4 :

			if (loginEmp.getDeptno().equals("300")) {
				workhistory();
			} else {
				System.out.println("접근 권한이 없습니다.\n");
				continue;
			}
			break;
			
		case 5 : workinghours(); break;
		}
		
		
		}
	}
	
		
	public void insertattendance() {
		System.out.println("[출퇴근시간 등록]");
		
		try {
			AttendanceDTO dto = new AttendanceDTO();
			
			System.out.print("등록할 사번을 입력하세요 => ");
			dto.setId(br.readLine());
			// 추가 : 출근일시 < 퇴근일시
			System.out.print("출근시간을 입력하세요(YYYYMMDD HH24:MI) => ");
			dto.setCIN(br.readLine());
			
			System.out.print("퇴근시간을 입력하세요(YYYYMMDD HH24:MI) => ");
			dto.setCOUT(br.readLine());
			
			System.out.print("기타 사항이 있습니까? (외출, 외출복귀, 휴가, 조퇴 / 없으면 생략 가능 ) => ");
			dto.setMEMO(br.readLine());
			
			dao.insertAttendance(dto);
			
			System.out.println("출퇴근시간이 등록되었습니다.");
			
		} catch (Exception e) {
			System.out.println("등록이 실패하였습니다.");
		}
		System.out.println();
		
	}
	
	public void updateattendance() {
		System.out.println("\n[출퇴근시간 수정]");
		
		try {
			AttendanceDTO dto = new AttendanceDTO();
			
			System.out.print("시간을 수정할 근태번호를 입력하세요 => ");
			dto.setAttNo(br.readLine());
			
			System.out.print("출근시간을 새로 입력하세요(YYYYMMDD HH24:MI) => ");
			dto.setCIN(br.readLine());
			
			System.out.print("퇴근시간을 새로 입력하세요(YYYYMMDD HH24:MI) => "); 
			dto.setCOUT(br.readLine());
			
			System.out.print("기타 사항이 있습니까? (외출, 외출복귀, 휴가, 조퇴 / 없으면 생략 가능 ) => ");
			dto.setMEMO(br.readLine());
		
			int result = dao.updateAttendance(dto);
			if(result == 0) {
				System.out.println("근태번호가 올바르지 않습니다.");
			} else {
				System.out.println("근무시간이 수정되었습니다.");
			}
			
		} catch (Exception e) {
			System.out.println("등록이 실패하였습니다.");
		}
		System.out.println();
		
		
	}
	
	public void deleteattendance() {
		System.out.println("\n[출퇴근시간 삭제]");
		String attNo;
		
		try {
			System.out.print("삭제가 필요한 근태번호를 입력하세요 => ");
			attNo = br.readLine();
			
			int result = dao.deleteAttendance(attNo);
			
			if(result == 0) {
				System.out.println("등록된 정보가 아닙니다.");
			} else {
				System.out.println("근무시간이 삭제되었습니다. ");
			}
			
		} catch (Exception e) {
			System.out.println("삭제를 실패하였습니다.");
		}
		System.out.println();
	}
	
	
	private void workhistory(){
		System.out.println("\n[전체 근로자 근무이력 출력]");
		String date;
		
		try {
			System.out.print("조회할 연도와 월을 입력하세요[yyyy-mm] => ");
			date = br.readLine();
			// 202208이라고 입력해도 조회가 되면 좋겠는데..
			List<AttendanceDTO> list = dao.listAttendance(date);
			
			if(list.isEmpty()) { 
				System.out.println("조회할 근로이력이 없습니다.\n");
				return;
			}
			// 근태번호 오름차순 정렬하는데 왜 이렇게 이상하게 나오는지 모르겠네 
			System.out.println("\n근태번호\t사번\t이름\t출근일시     \t\t퇴근일시     \t\t기타사항");
			System.out.println("---------------------------------------------------------------------------------------");
			
			for(AttendanceDTO dto : list) {
				System.out.print(dto.getAttNo() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getCIN() + "\t");
				System.out.print(dto.getCOUT() + "\t");
				System.out.println(dto.getMEMO());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println();
	}
	
	public void workinghours() {
		System.out.println("\n[개인별 근무이력 및 월간 총 근무시간 출력]");
		
		try {
			String id, date;
			int workingMins= 0;

			System.out.print("사번을 입력하세요 => ");
			id = br.readLine();
			
			if (!(logindto.getId().equals(id) || (logindto.getDeptno().equals("300")))) {
				System.out.println("총무부 소속이 아닐 경우, 본인 근무이력만 확인 가능합니다. ");
				return;
			}
			
			System.out.print("조회할 연도와 월을 입력하세요[yyyy-mm] => ");
			date = br.readLine();
			
			workingMins = dao.readWorkinghours(id, date);
			
			if(workingMins == 0) {
				System.out.println("조회할 내역이 없습니다.");
				System.out.println();
				return;
			} 
			
			int hour, min = 0;
			hour = workingMins/60;
			min = workingMins%60;
			
			System.out.println("\n" + id + "님은 " + date.substring(0,4) + "년 " + date.substring(5) + "월에 총 " + hour + "시간 " + min + "분 근무하였습니다.");
			
			System.out.println("\n근태번호    \t출근일시     \t\t퇴근일시     \t기타사항");
			System.out.println("----------------------------------------------------------------------");
			
			List<AttendanceDTO> list = dao.readAttendacne(id, date);
			for(AttendanceDTO dto : list) {
				System.out.print(dto.getAttNo() + "\t");
				System.out.print(dto.getCIN() + "\t");
				System.out.print(dto.getCOUT() + "\t");
				System.out.println(dto.getMEMO());
			}
			System.out.println();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
