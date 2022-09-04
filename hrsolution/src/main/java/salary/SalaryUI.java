package salary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.main.LoginDTO;
import com.main.ValidCheck;

public class SalaryUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private SalaryDAO saldao = new SalaryDAOImpl();
	private LoginDTO logindto = null;
	private ValidCheck valchk = new ValidCheck(); 
	
	DecimalFormat money = new DecimalFormat("###,### 원");

	public void salarymenu(LoginDTO empdto) {
		logindto = empdto;
		int ch, ch1, ch2;

		try {
			System.out.println("\n");
			System.out.println("\t[연봉 관리 메뉴 실행]");
			System.out.println("사용자: [" + empdto.getId() + "] 님");
			
			while (true) {
				ch = 0; // 초기화
				ch1 = 0;
				ch2 = 0;

				do {
					System.out.print("1.연봉관리   2.급여관리   3.메인메뉴 > ");
					ch = Integer.parseInt(br.readLine());
				} while (ch < 1 || ch > 3);

				if (ch == 3) {
					return;
				}

				loop:
				switch (ch) {
				case 1:
					while (true) {
						System.out.println("\n\t[연봉 관리 메뉴]");
						do {
							System.out.print("1.연봉협상(등록) 2.연봉수정 3.사원연봉정보 4.전체연봉리스트 5.직급별연봉리스트 6.부서별연봉리스트 7.돌아가기  > ");
							ch1 = Integer.parseInt(br.readLine());
						} while (ch1 < 1 || ch1 > 7);
						
						if(ch==0 || ch1==7) {
							break loop;
						}
						switch (ch1) {
						case 1: // 연봉 협상
							if (empdto.getDeptno().equals("100")) {
								insertSal();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						case 2: // 연봉 수정(등록)
							if (empdto.getDeptno().equals("100")) {
								updateSal();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						case 3: // 사원 연봉 정보
							findBySal();
							continue;
						case 4: // 전체 연봉 리스트
							if (empdto.getDeptno().equals("100")) {
								listAllSal();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						case 5: // 직급별 연봉 리스트
							if (empdto.getDeptno().equals("100")) {
								listPosSal();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						case 6: // 부서별 연봉 리스트
							if (empdto.getDeptno().equals("100")) {
								listDeptSal();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						}
					}

				case 2:
					while (true) {
						do {
							System.out.println("\n\t[급여 관리 메뉴]");
							System.out.print("1.급여지급(등록) 2.급여수정 3.사원급여명세서 4.월별급여명세서 5.돌아가기  > ");
							ch2 = Integer.parseInt(br.readLine());
						} while (ch2 < 1 || ch2 > 5);

						if(ch==0 || ch2==5) {
							break loop;
						}
						
						switch (ch2) {
						case 1: // 급여지급
							if (empdto.getDeptno().equals("100")) {
								insertPay();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						case 2: // 급여수정
							if (empdto.getDeptno().equals("100")) {
								updatePay();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						case 3: // 사원 급여 명세서
							findByPay();
							continue;
						case 4: // 월별 급여 명세서
							if (empdto.getDeptno().equals("100")) {
								listMonthPay();
							} else {
								System.out.println("접근 권한이 없습니다. \n");
								continue;
							}
							continue;
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	// 1-1
	public void insertPay() {
		System.out.println("\t\t[급여 지급] \n");

		try {
			PayDTO paydto = new PayDTO();
			SalaryDTO saldto = new SalaryDTO();
			String id, m, payDate;
			int normal; // 월급 (연봉/12)
			int nationpen, medicinsur, longinsur, employeeinsur, gapfee, citizenfee;
			int meal = 100_000; // 식대
			int chk; // 4대보험 이외의 공제내역 체크
			String nowyear = new SimpleDateFormat("yyyy").format(new Date());
			// String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String payNo = null; // 급여지급번호

			while(true) {
				System.out.print("급여를 지급할 사번 ? ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					break;
				}
			}

			System.out.print("급여가 지급될 날짜 ? [형식: YYYY-MM-01]");
			payDate = br.readLine();
			System.out.print("몇월의 급여 ?  [형식: 8월->08] ? ");
			m = br.readLine();

			saldto = saldao.listSalaryNowEmp(id);
			if (saldto == null) {
				System.out.println("연봉 정보가 존재하지 않습니다. ");
				return;
			}

			// 사번 + 년도뒷2자리 + 월일
			payNo = id + "-" + nowyear.substring(2) + m;
			System.out.println(payNo);

			do {
				System.out.print("4대보험 이외에 입력할 공제내역 존재 ? [없으면 0, 있으면 1] > ");
				chk = Integer.parseInt(br.readLine());
			} while (chk < 0 || chk > 1);

			switch (chk) {
			case 0:
				paydto.setPaywelfare(0);
				paydto.setPayextra(0);
				paydto.setBonus(0);
				paydto.setPayover(0);
				break;
			case 1:
				System.out.print("복리후생비 ? ");
				paydto.setPaywelfare(Integer.parseInt(br.readLine()));

				System.out.print("기타 지급 금액 ? ");
				
				paydto.setPayextra(Integer.parseInt(br.readLine()));
				System.out.print("지급할 상여금 ? ");
				paydto.setBonus(Integer.parseInt(br.readLine()));

				System.out.print("지급할 시간 외 수당 ? ");
				paydto.setPayover(Integer.parseInt(br.readLine()));

				break;
			}

			/*
			 * 국민연금 = 월소득액 x 4.5%, 건강보험 = 월소득액 x 3.495% 장기요양보험 = 건강보험 x 12.27% 고용보험 = 월소득액
			 * x0.9% 산재보험 = 일반회사=0
			 */
			normal = (Integer.parseInt(saldto.getSal()) / 12);
			nationpen = (int) (normal * 0.045);
			medicinsur = (int) (normal * 0.03495);
			longinsur = (int) (medicinsur * 0.1227);
			employeeinsur = (int) (normal * 0.009);

			// 공제대상가족수 2인 기준
			if (normal <= 2_000_000) {
				gapfee = 20_560;
			} else if (normal <= 2_500_000) {
				gapfee = 45_530;
			} else if (normal <= 3_000_000) {
				gapfee = 87_880;
			} else if (normal <= 3_500_000) {
				gapfee = 146_560;
			} else if (normal <= 4_000_000) {
				gapfee = 214_810;
			} else if (normal <= 4_500_000) {
				gapfee = 285_440;
			} else if (normal <= 5_000_000) {
				gapfee = 335_190;
			} else if (normal <= 5_500_000) {
				gapfee = 424_940;
			} else if (normal <= 6_000_000) {
				gapfee = 562_350;
			} else {
				gapfee = normal / 10;
			}

			gapfee /= 12;
			// 주민세 = 소득세 x 10%
			citizenfee = gapfee / 10;

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
			paydto.setGapfee(gapfee);
			paydto.setCitizenfee(citizenfee);

			saldao.insertPay(paydto);

			System.out.println(saldto.getId() + " 사번의 " + m + "월분 급여 지급 완료 !!! ");

			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 1-2
	public void updatePay() {
		System.out.println("\n\t\t[급여 정보 수정]");
		System.out.println("\t[4대보험 이외의 공제내역, 추가수당 수정] \n");

		try {
			PayDTO paydto = new PayDTO();
			SalaryDTO saldto = new SalaryDTO();
			String id, year, m;
			String payNo = null; // 급여지급번호

			while(true) {
				System.out.print("급여를 수정할 사번 ? ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					break;
				}
			}
			
			System.out.print("수정할 급여정보의 년도 ?  [형식: yyyy] ? ");
			year = br.readLine();
			
			System.out.print("수정할 급여정보의 월 [형식: 8월->08] ? ");
			m = br.readLine();

			saldto = saldao.listSalaryNowEmp(id);
			if (saldto == null) {
				System.out.println("급여 정보가 존재하지 않습니다. ");
				return;
			}

			// 사번 + 년도뒷2자리 + 월일
			payNo = id + "-" + year.substring(2, 4) + m;
			System.out.println(payNo);

			paydto.setPaywelfare(0);
			paydto.setPayextra(0);
			paydto.setBonus(0);
			paydto.setPayover(0);
			paydto.setGapfee(0);
			paydto.setPayextra(0);

			System.out.print("복리후생비 ? ");
			paydto.setPaywelfare(Integer.parseInt(br.readLine()));

			System.out.print("기타 지급 금액 ? ");
			paydto.setPayextra(Integer.parseInt(br.readLine()));

			System.out.print("지급할 상여금 ? ");
			paydto.setBonus(Integer.parseInt(br.readLine()));

			System.out.print("지급할 시간 외 수당 ? ");
			paydto.setPayover(Integer.parseInt(br.readLine()));

			System.out.print("갑근세 ? ");
			paydto.setGapfee(Integer.parseInt(br.readLine()));

			System.out.print("주민세 ? ");
			paydto.setPayextra(Integer.parseInt(br.readLine()));

			// [급여번호 형식:사번-월 ex)10001-yy08 ]
			paydto.setId(saldto.getId());
			paydto.setPayno(payNo);

			paydto.setAccidantinsur(0);

			saldao.updatePay(paydto);

			System.out.println(saldto.getId() + " 사번의 " + m + "월분 급여 수정 완료 !!! ");

			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 1-3
	public void findByPay() {
		System.out.println("\t[사원 급여 명세서] \n");
		// System.out.println(logindto.getId());

		try {
			String id;

			while(true) {
				String nowDate = new SimpleDateFormat("yyyy-mm").format(new Date());
				
				System.out.print("급여명세서를 출력할 사번 ? ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					break;
				}
			}

			// 본인이 아니고 재무부 사원이 아니면
			if (!(logindto.getId().equals(id)) && !(logindto.getDeptno().equals("100"))) {
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

	// 1-4
	public void listMonthPay() {
		System.out.println();
		System.out.println("\t[월별 급여 명세서]");
		String month;

		try {
			System.out.print("출력할 년월 [형식:YYYY-MM] ? ");
			month = br.readLine();

			List<PayDTO> plist = saldao.listPayMonth(month);

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

	// 2-1
	public void insertSal() {
		System.out.println("\t[연봉 협상] \n");

		try {
			SalaryDTO saldto = new SalaryDTO();
			String id;
			
			while(true) {
				System.out.print("협상할 사번 ? ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					saldto.setId(id);
					break;
				}
			}

			System.out.print("새로 협상된 연봉 ? ");
			saldto.setSal(br.readLine());

			System.out.print("협상된 연봉의 시작일 ? ");
			saldto.setSalStart(br.readLine());

			System.out.print("메모 ? ");
			saldto.setMemo(br.readLine());

			saldao.insertSal(saldto);

			System.out.println("연봉 협상 완료 !!! ");
			
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2-2
	public void updateSal() {
		System.out.println("\t[연봉 수정] \n");

		try {
			String id;
			SalaryDTO newsaldto = new SalaryDTO();

			while(true) {
				System.out.print("연봉을 수정할 사번 ? ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					break;
				}
			}
			
			List<SalaryDTO> slist = saldao.listSalaryHisEmp(id);

			if(slist == null) {
				System.out.println("연봉번호가 존재하지 않습니다. ");
			}
			
			System.out.println(id+"님의 연봉 협상 기록");
			System.out.println("★연봉번호★\t사번\t이름\t협상연봉\t\t연봉시작일\t\t메모");
			System.out.println("------------------------------------------------------------------------");
			for (SalaryDTO saldto : slist) {
				System.out.print("★" + saldto.getSalNo() + "★\t");
				System.out.print(saldto.getId() + "\t");
				System.out.print(saldto.getName() + "\t");
				System.out.print(money.format(Integer.parseInt(saldto.getSal())) + "\t");
				System.out.print(saldto.getSalStart() + "\t\t");
				System.out.println(saldto.getMemo());
			}
			System.out.println("------------------------------------------------------------------------");
			
			newsaldto.setId(id);
			System.out.print("수정할 연봉의 연봉번호는 ? ");
			newsaldto.setSalNo(br.readLine());
			
			System.out.print("수정할 연봉 금액 ? ");
			newsaldto.setSal(br.readLine());

			System.out.print("수정할 연봉 시작일 ? ");
			newsaldto.setSalStart(br.readLine());

			System.out.print("메모 ? ");
			newsaldto.setMemo(br.readLine());

			saldao.updateSalary(newsaldto);

			System.out.println("연봉 정보 수정 완료 !!! ");
			
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2-3 사원 연봉 정보
	public void findBySal() {
		System.out.println();
		System.out.print("\t[사원 연봉 정보] \n");

		try {
			String id;

			while(true) {
				System.out.print("연봉을 확인할 사번 ? ");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					break;
				}
			}
			

			SalaryDTO sdto = saldao.listSalaryNowEmp(id);

			if(sdto== null ) {
				System.out.println("데이터가 존재하지 않습니다. ");
				return;
			}
			
			System.out.println("사번\t이름\t직급\t부서\t현재연봉\t\t연봉시작일\t\t메모");
			System.out.println("------------------------------------------------------------------------");
			System.out.print(sdto.getId() + "\t");
			System.out.print(sdto.getName() + "\t");
			System.out.print(sdto.getPosition() + "\t");
			System.out.print(sdto.getDept() + "\t");
			System.out.print(money.format(Integer.parseInt(sdto.getSal())) + "\t");
			System.out.print(sdto.getSalStart() + "\t\t");
			System.out.println(sdto.getMemo());
			System.out.println("------------------------------------------------------------------------");

			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2-4
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

	// 2-5
	public void listPosSal() {
		System.out.println();
		System.out.print("\t\t[직급별 연봉 리스트] \n");

		try {
			List<SalaryDTO> slist = saldao.listSalaryPos();

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

	// 2-6
	public void listDeptSal() {
		System.out.println();
		System.out.print("\t\t[부서별 연봉 리스트] \n");

		try {
			List<SalaryDTO> slist = saldao.listSalaryDept();

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

}
