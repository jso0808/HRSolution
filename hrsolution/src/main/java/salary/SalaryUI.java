package salary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.main.LoginDTO;


public class SalaryUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private SalaryDAO saldao = new SalaryDAOImpl();
	
	public void salarymenu(LoginDTO empdto) {
		System.out.println("연봉 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
		
		int ch;
		
		try {
			do {
				System.out.println("1.급여지급(등록) 2.수정 3.전체급여리스트 4.월별급여리스트 5.사번검색 6.메인메뉴");
				ch = Integer.parseInt(br.readLine());
			} while(ch<1 || ch>6);
			
			switch(ch) {
			case 1: 
				// 재무부 코드: 100
				if(empdto.getDeptno()=="100") {
					insertpay();
				} else {
					System.out.println("접근 권한이 없습니다.");
					return;
				}
				insertpay();
				break;
			case 2: 
				if(empdto.getDeptno()=="100") {
					updatesal();
				} else {
					System.out.println("접근 권한이 없습니다.");
					return;
				}
				break;
			case 3: 
				listAllpay();
				break;
			case 4: 
				listMonthpay();
				break;
			case 5: 
				findBypay();
				break;
			case 6:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertpay() {
		System.out.println("[급여 지급] \n");
		
		int incom;
		int nationpen, medicinsur, longinsur, employeeinsur;
		
		try {
			PayDTO paydto = new PayDTO();
			SalaryDTO saldto = new SalaryDTO();
			String id;
			String nowMonth = new SimpleDateFormat("MM").format(new Date());
			
			System.out.print("급여를 지급할 사번 ? ");
			id = br.readLine();
			
			saldto = saldao.listSalaryEmp(id);
			if(saldto==null) {
				 System.out.println("연봉 정보 데이터가 존재하지 않습니다. ");
			}
			
			
			/*
			 nationpen 국민연금 = 월소득액 x 4.5%	
			 medicinsur 건강보험 = 월소득액 x 3.495%
			 longinsur 장기요양보험 = 건강보험 x 12.27%
			 employeeinsur 고용보험 = 월소득액 x 0.9%
			 */
			incom = Integer.parseInt(saldto.getSal())- 100_000;
			nationpen = (int) (incom*4.5);
			medicinsur = (int) (incom*3.495);
			longinsur = (int) (medicinsur*12.27);
			employeeinsur = (int) (incom*0.9);
			
			
			//  [형식:사번-월 ex)10001-08 ]
			paydto.setPayno(id+"-"+nowMonth);
			paydto.setPaynormal(incom);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updatesal() {
		
	}
	
	public void listAllpay() {}
	
	public void listMonthpay() {}
	
	public void findBypay() {
		System.out.println("");
	}
	
	
}
