package salary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.main.LoginDTO;

public class SalaryUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private SalaryDAO saldao = new SalaryDAOImpl();
	private LoginDTO logindto = null;

	DecimalFormat money = new DecimalFormat("###,### 원");

	// String str = money.format(12300000);

	public void salarymenu(LoginDTO empdto) {
		logindto = empdto;
		System.out.println("연봉 관리 메뉴 실행");
		System.out.println(empdto.getId() + " 님");

		int ch;

		try {
			while (true) {
				ch = 0; // 초기화
				
				do {
					System.out.println("1.급여지급(등록) 2.연봉정보수정(등록) 3.전체연봉리스트 4.월별급여리스트 5.사번급여검색 6.메인메뉴");
					ch = Integer.parseInt(br.readLine());
				} while (ch < 1 || ch > 6);
				
				switch (ch) {
				case 1:
					// 재무부 코드: 100
					if (empdto.getDeptno().equals("100")) {
						insertpay();
					} else {
						System.out.println("접근 권한이 없습니다. \n");
						return;
					}
					break;
				case 2:
					if (empdto.getDeptno().equals("100")) {
						updatesal();
					} else {
						System.out.println("접근 권한이 없습니다. \n");
						return;
					}
					break;
				case 3:
					if (empdto.getDeptno().equals("100")) {
						listAllSal();
					} else {
						System.out.println("접근 권한이 없습니다. \n");
						return;
					}
					break;
				case 4:
					if (empdto.getDeptno().equals("100")) {
						listMonthpay();
					} else {
						System.out.println("접근 권한이 없습니다. \n");
						return;
					}
					break;
				case 5:
					findBypay();
					break;
				case 6:
					return;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertpay() {
		System.out.println("[급여 지급] \n");

		try {
			PayDTO paydto = new PayDTO();
			SalaryDTO saldto = new SalaryDTO();
			String id, m, payDate;
			int normal; // 월급 (연봉/12)
			int nationpen, medicinsur, longinsur, employeeinsur;
			int meal = 100_000; // 식대
			int chk; // 4대보험 이외의 공제내역 체크
			String nowyear = new SimpleDateFormat("yyyy").format(new Date());
			// String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String payNo = null; // 급여지급번호

			System.out.print("급여를 지급할 사번 ? ");
			id = br.readLine();
			System.out.print("급여가 지급될 날짜 ? [형식: YYYY-MM-01]");
			payDate = br.readLine();
			System.out.print("몇월의 급여 ?  [형식: 8월->08] ? ");
			m = br.readLine();

			saldto = saldao.listSalaryEmp(id);
			if (saldto == null) {
				System.out.println("연봉 정보가 존재하지 않습니다. ");
				return;
			}

			// 사번 + 년도뒷2자리 + 월일
			payNo = id + "-" + nowyear.substring(2) + m;
			System.out.println(payNo);

			do {
				System.out.println("4대보험 이외에 입력할 공제내역 존재 ? [없으면 0, 있으면 1]");
				chk = Integer.parseInt(br.readLine());
			} while (chk < 0 || chk > 1);

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

			normal = (Integer.parseInt(saldto.getSal()) / 12) - meal;
			nationpen = (int) (normal * 0.045);
			medicinsur = (int) (normal * 0.03495);
			longinsur = (int) (medicinsur * 0.1227);
			employeeinsur = (int) (normal * 0.009);
			/*
			 * 국민연금 = 월소득액 x 4.5% 건강보험 = 월소득액 x 3.495% 장기요양보험 = 건강보험 x 12.27% 고용보험 = 월소득액 x
			 * 0.9% 산재보험 = 일반회사=0
			 */

			// [급여번호 형식:사번-월 ex)10001-08 ]
			paydto.setId(saldto.getId());
			paydto.setPayno(payNo);
			paydto.setPayDate(payDate);
			paydto.setPaynormal(normal);
			paydto.setPaymeal(meal);
			paydto.setMedicinsur(medicinsur);
			paydto.setNationpen(nationpen);
			paydto.setEmployeeinsur(employeeinsur);
			paydto.setLonginsur(longinsur);
			paydto.setAccidantinsur(0);

			saldao.insertPay(paydto);

			System.out.println(saldto.getId() + " 사번의 " + m + "월분 급여 지급 완료 !!! ");

			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updatesal() {
		System.out.println("[연봉 정보 수정] \n");

		try {
			SalaryDTO saldto = new SalaryDTO();

			System.out.print("수정할 사번 ? ");
			saldto.setId(br.readLine());

			System.out.print("수정할 연봉 ? ");
			saldto.setSal(br.readLine());

			System.out.print("연봉 시작일 ? ");
			saldto.setSalStart(br.readLine());

			System.out.print("메모 ? ");
			saldto.setMemo(br.readLine());

			saldao.updateSalary(saldto);

			System.out.println("연봉 정보 수정 완료 !!! ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listAllSal() {
		System.out.println();
		System.out.print("\t\t[전체 연봉 리스트] \n");

		try {
			List<SalaryDTO> slist = saldao.listSalaryAll();

			System.out.println("사번\t이름\t직급\t부서\t현재연봉\t\t연봉시작일\t\t메모");
			System.out.println("------------------------------------------------------------------------");
			for (SalaryDTO sdto : slist) {
				System.out.print(sdto.getId() + "\t");
				System.out.print(sdto.getName() + "\t");
				System.out.print(sdto.getPosition() + "\t");
				System.out.print(sdto.getDept() + "\t");
				System.out.print(money.format(Integer.parseInt(sdto.getSal())) + "\t");
				System.out.print(sdto.getSalStart() + "\t\t");
				// System.out.print(sdto.getSalEnd()+"\t");
				System.out.println(sdto.getMemo());

			}
			System.out.println("------------------------------------------------------------------------");
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 월별 급여 리스트
	public void listMonthpay() {
		System.out.println();
		System.out.println("[\t\t월별 급여 리스트]");
		String month;

		try {
			System.out.print("출력할 년월 [형식:YYYY-MM] ? ");
			month = br.readLine();

			List<PayDTO> plist = saldao.listPayAll(month);

			System.out.println("-------------------------------------------------");
			for (PayDTO pdto : plist) {
				System.out.println(
						"\t\t[" + pdto.getPayno().substring(6, 8) + "년 " + pdto.getPayno().substring(8) + "월 급여 명세서]");

				System.out.print("사번: " + pdto.getId() + "    ");
				System.out.println(
						"이름: " + pdto.getName() + "    부서: " + pdto.getDept() + "    직급: " + pdto.getPosition());
				System.out.println("-------------------------------------------------");
				System.out.println("기본급 \t\t" + money.format(pdto.getPaynormal()));
				System.out.println("식대 \t\t" + money.format(pdto.getPaymeal()));
				System.out.println("복리후생비 \t\t" + money.format(pdto.getPaywelfare()));
				System.out.println("기타지급 \t\t" + money.format(pdto.getPayextra()));
				System.out.println("상여금 \t\t" + money.format(pdto.getBonus()));
				System.out.println("시간외수당 \t\t" + money.format(pdto.getPayover()));
				System.out.println("갑근세 \t\t" + money.format(pdto.getGapfee()));
				System.out.println("국민연금 \t\t" + money.format(pdto.getNationpen()));
				System.out.println("건강보험 \t\t" + money.format(pdto.getMedicinsur()));
				System.out.println("고용보험 \t\t" + money.format(pdto.getEmployeeinsur()));
				System.out.println("장기요양보험 \t" + money.format(pdto.getLonginsur()));
				System.out.println("산재보험 \t\t" + money.format(pdto.getAccidantinsur()));
				System.out.println("주민세 \t\t" + money.format(pdto.getCitizenfee()));
				System.out.println("-------------------------------------------------");
				System.out.println("지급액: \t\t" + money.format(pdto.getTot()));
				System.out.println("-------------------------------------------------");
				System.out.println();
			}

			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	public void findBypay() {
		System.out.println("[사원 급여 리스트] \n");
		// System.out.println(logindto.getId());

		try {
			String id;

			System.out.print("급여 리스트 출력할 사번 ? ");
			id = br.readLine();

			// 본인 또는 재무부 사원이 아니면
			if (!(id == logindto.getId() || (logindto.getDeptno().equals("100")))) {
				System.out.println("본인 또는 재무부만 확인 가능합니다. ");
				return;
			}

			List<PayDTO> plist = saldao.listPayEmp(id);
			
			if (plist.size() < 1) {
				System.out.println("급여 정보가 존재하지 않습니다. ");
				return;
			}

			System.out.println("-------------------------------------------------");
			for (PayDTO pdto : plist) {
				System.out.println(
						"\t\t[" + pdto.getPayno().substring(6, 8) + "년 " + pdto.getPayno().substring(8) + "월 급여 명세서]");

				System.out.print("사번: " + pdto.getId() + "    ");
				System.out.println(
						"이름: " + pdto.getName() + "    부서: " + pdto.getDept() + "    직급: " + pdto.getPosition());
				System.out.println("-------------------------------------------------");
				System.out.println("기본급 \t\t" + money.format(pdto.getPaynormal()));
				System.out.println("식대 \t\t" + money.format(pdto.getPaymeal()));
				System.out.println("복리후생비 \t\t" + money.format(pdto.getPaywelfare()));
				System.out.println("기타지급 \t\t" + money.format(pdto.getPayextra()));
				System.out.println("상여금 \t\t" + money.format(pdto.getBonus()));
				System.out.println("시간외수당 \t\t" + money.format(pdto.getPayover()));
				System.out.println("갑근세 \t\t" + money.format(pdto.getGapfee()));
				System.out.println("국민연금 \t\t" + money.format(pdto.getNationpen()));
				System.out.println("건강보험 \t\t" + money.format(pdto.getMedicinsur()));
				System.out.println("고용보험 \t\t" + money.format(pdto.getEmployeeinsur()));
				System.out.println("장기요양보험 \t" + money.format(pdto.getLonginsur()));
				System.out.println("산재보험 \t\t" + money.format(pdto.getAccidantinsur()));
				System.out.println("주민세 \t\t" + money.format(pdto.getCitizenfee()));
				System.out.println("-------------------------------------------------");
				System.out.println("지급액: \t\t" + money.format(pdto.getTot()));
				System.out.println("-------------------------------------------------");
				System.out.println();
			}
			
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

}
