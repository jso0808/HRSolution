package salary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.main.LoginDTO;

public class SalaryUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private SalaryDAO saldao = new SalaryDAOImpl();
	private LoginDTO logindto = null;

	public void salarymenu(LoginDTO empdto) {
		logindto = empdto;
		System.out.println("연봉 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
		
		int ch;
		
		try {
			while(true) {
			do {
				System.out.println("1.급여지급(등록) 2.수정 3.전체급여리스트 4.월별급여리스트 5.사번검색 6.메인메뉴");
				ch = Integer.parseInt(br.readLine());
			} while(ch<1 || ch>6);
			
			System.out.println("부서는 "+empdto.getDeptno());
			switch(ch) {
			case 1: 
				// 재무부 코드: 100
				if(empdto.getDeptno().equals("100")) {
					insertpay();
				} else {
					System.out.println("접근 권한이 없습니다. \n");
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertpay() {
		System.out.println("[급여 지급] \n");

		int incom; // 월소득액
		int nationpen, medicinsur, longinsur, employeeinsur;
		int meal = 100_000; // 식대
		int chk;

		try {
			PayDTO paydto = new PayDTO();
			SalaryDTO saldto = new SalaryDTO();
			String id;
			String nowMonth = new SimpleDateFormat("MM").format(new Date());
			String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String payNo = null;

			System.out.print("급여를 지급할 사번 ? ");
			id = br.readLine();

			saldto = saldao.listSalaryEmp(id);
			if (saldto == null) {
				System.out.println("연봉 정보가 존재하지 않습니다. ");
				return;
			}
			
			payNo = id+"-"+nowMonth;
			System.out.println(payNo);

			System.out.println("4대보험 이외에 입력할 공제내역 존재 ? [없으면 0, 있으면 1]");
			chk = br.read();

			switch (chk) {
			case 0:
				paydto.setPaywelfare(0);
				paydto.setPayextra(0);
				paydto.setBonus(0);
				paydto.setPayover(0);
				paydto.setGapfee(0);
				paydto.setPayextra(0);
				break;
			case 1:
				System.out.print("복리후생비 ? ");
				paydto.setPaywelfare(br.read());

				System.out.print("기타 지급 금액 ? ");
				paydto.setPayextra(br.read());

				System.out.print("지급할 상여금 ? ");
				paydto.setBonus(br.read());

				System.out.print("지급할 시간 외 수당 ? ");
				paydto.setPayover(br.read());

				System.out.print("갑근세 ? ");
				paydto.setGapfee(br.read());

				System.out.print("주민세 ? ");
				paydto.setPayextra(br.read());
				break;
			}

			incom = Integer.parseInt(saldto.getSal()) - meal;
			nationpen = (int) (incom * 4.5);
			medicinsur = (int) (incom * 3.495);
			longinsur = (int) (medicinsur * 12.27);
			employeeinsur = (int) (incom * 0.9);

			// [급여번호 형식:사번-월 ex)10001-08 ]
			paydto.setId(saldto.getId());
			paydto.setPayno(payNo);
			paydto.setPayDate(nowDate);
			paydto.setPaynormal(incom);
			paydto.setPaymeal(meal);
			paydto.setMedicinsur(medicinsur);
			paydto.setNationpen(nationpen);
			paydto.setEmployeeinsur(employeeinsur);
			paydto.setLonginsur(longinsur);
			paydto.setAccidantinsur(0);

			saldao.insertPay(paydto);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updatesal() {

	}

	public void listAllpay() {
	}

	public void listMonthpay() {
	}

	public void findBypay() {
		System.out.println("[나의 급여 리스트] \n");
		System.out.println(logindto.getId());
		
		try {
			List<PayDTO> plist = saldao.listPayEmp(logindto.getId());
			System.out.println(plist.size());
			
			System.out.println("-----------------------------");
			for(PayDTO pdto : plist) {
				System.out.print(pdto.getId()+"\t");
				System.out.print(pdto.getPayno()+"\t");
				System.out.print(pdto.getPaynormal()+"\t");
				System.out.println(pdto.getPaymeal());
			}
			System.out.println("-----------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
