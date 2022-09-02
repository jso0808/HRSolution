package evaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.main.LoginDTO;

public class EvaluationUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private EvaluationDAO dao = new EvaluationDAOImpl();

	public void evaluationmenu(LoginDTO loginEmp) {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("평가 관리 메뉴 실행");
		System.out.println(loginEmp.getId() + " 님");

		int ch;

		while (true) {
			try {
				System.out.println();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println();
				System.out.println("1.평가등급 검색 2. 사원 평가 등록 3. 사원 평가 수정 4. 사원 평가 삭제 5.사원 1인 평가리스트 6. 메인메뉴 =>");
				ch = Integer.parseInt(br.readLine());

				if (ch == 6) {
					return;
				}
				switch (ch) {
				case 1:
					readGrade();
					break;

				case 2:
					if (loginEmp.getDeptno().equals("200")) {
						insertGrade();
					} else {
						System.out.println("인사부만 접근할 수 있습니다.");
					}
					break;

				case 3:
					if (loginEmp.getDeptno().equals("200")) {
						updateGrade();
					} else {
						System.out.println("인사부만 접근할 수 있습니다.");
					}
					break;

				case 4:
					if (loginEmp.getDeptno().equals("200")) {
						deleteGrade();
					} else {
						System.out.println("인사부만 접근할 수 있습니다.");
					}
					break;
				case 5:
					if (loginEmp.getDeptno().equals("200")) {
						GradelistUI();
					} else {
						System.out.println("인사부만 접근할 수 있습니다.");
					}
				}
			}catch (NumberFormatException e) {
				System.out.println("숫자만 입력가능합니다. 다시 입력하세요");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("잘못된 숫자입니다. 다시 입력하세요");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void GradelistUI() {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 사원 1인 전체평가리스트");
		String id;
		try {
			System.out.println("평가살 사번 ?");
			System.out.println("평가연도\t사번\t평가점수\t평가시기\t\t\t평가자");
			
			id = br.readLine();
			System.out.println("------------------------------------------------------------");

			
			List<EvaluationDTO> list =dao.GradeList(id);
		
			for(EvaluationDTO dto : list) {
				
				System.out.print(dto.getYear() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(EvalScore(dto.getGrade()) + "\t");
				System.out.print(dto.getContent() + "\t");
				System.out.println(dto.getPerid() + "\t");	
			}
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
		}catch (IOException e) {
			System.out.println("일치하는 사번이 없습니다.");
			e.printStackTrace();
		} catch (Exception e) {
		}
	}

	private void readGrade() {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 평가등급 검색");
		int year;
		String id;
		try {
			while (true) {
				try {
					System.out.println("평가사번 ?");
					id = br.readLine();

					System.out.println("평가연도 ?");
					year = Integer.parseInt(br.readLine());
					
					break;
				} catch (NumberFormatException e) {
					System.out.println("평가년도입력이 잘못되었습니다.");
					System.out.println("다시 입력하세요");
				} catch (Exception e) {
					System.out.println("일치하는 정보가 없습니다.");
					System.out.println("다시 입력하세요");
				}
			}
			
			EvaluationDTO dto = dao.readGrade(year, id);

			if (dto == null) {
				System.out.println("일치하는 사원이 없습니다.\n");
				return;
			}
			System.out.println("\n 사원 평가 내역 ");
			System.out.println("평가연도\t사번\t이름\t평가등급\t평가날짜\t\t\t평가내용\t\t평가자");
			System.out.println("------------------------------------------------------------");

			System.out.print(dto.getYear() + "\t");
			System.out.print(dto.getId() + "\t");
			System.out.print(dto.getName() + "\t");
			System.out.print(EvalScore(dto.getGrade()) + "\t");
			System.out.print(dto.getDate() + "\t");
			System.out.print(dto.getContent() + "\t");
			System.out.print(dto.getPerid() + "\t");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
		} catch (NumberFormatException e) {
			System.out.println("일치하는 사번이 없습니다.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("readUI 에러");
			e.printStackTrace();
		}
		System.out.println();
	}

	private String EvalScore(int grade) {
		if (grade > 100 || grade < 0) {
			System.out.println("잘못 입력된 평가점수입니다.");
			return null;
		} else if (grade >= 95) {
			return "A+";
		} else if (grade >= 90) {
			return "A";
		} else if (grade >= 85) {
			return "B+";
		} else if (grade >= 80) {
			return "B";
		} else if (grade >= 75) {
			return "C+";
		} else if (grade >= 70) {
			return "C";
		} else if (grade >= 65) {
			return "D+";
		} else if (grade >= 60) {
			return "D";
		} else
			return "F";
	}

	// 사원번호, 이름, 년도별 등급
	private void insertGrade() {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 평가등급 등록");
		try {
			EvaluationDTO dto = new EvaluationDTO();

			// 사번일치여부 확인 수정해야함

			while (true) {
				try {
					System.out.println("수정할 평가연도 ?");
					dto.setYear(Integer.parseInt(br.readLine()));

					System.out.println("수정할 사번  ? ");
					dto.setId(br.readLine());
					
					break;
				} catch (NumberFormatException e) {
					System.out.println("평가연도입력이 잘못되었습니다.");
					System.out.println("다시 입력하세요");
				} catch (Exception e) {
					System.out.println("일치하는 정보가 없습니다.");
					System.out.println("다시 입력하세요");
				}
				
			}
			while (true) {
				try {
					System.out.println("최종 평가점수 ?");
					dto.setGrade(Integer.parseInt(br.readLine()));

					System.out.print(" 평가날짜[YYYY-MM-DD] :  ");
					dto.setDate(br.readLine());

					System.out.println("평가내용 ?");
					dto.setContent(br.readLine());

					System.out.println("평가자 사번 ?");
					dto.setPerid(br.readLine());
					System.out.println();
					System.out.println("------------------------------------------------------------------------------------------------------------------------");
					System.out.println();
					break;					
				 
				} catch (NumberFormatException e) {
					System.out.println("평가점수이 잘못되었습니다.");
					System.out.println("다시 입력하세요");
				} catch (Exception e) {
					System.out.println("잘못된 입력정보입니다.");
					System.out.println("다시 입력하세요");
				}
				
			}
			
			dao.insertGrade(dto);
			System.out.println("평가등급 등록 완료!!!");			
		}catch (NumberFormatException e) {
			System.out.println("최종 평가점수를 잘못입력했습니다.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("평가등급 등록 UI 에러");
			e.printStackTrace();
		}
	}

	private void updateGrade() {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 평가등급 수정");
		try {
			EvaluationDTO dto = new EvaluationDTO();
			while (true) {
				try {
					System.out.println("수정할 평가연도 ?");
					dto.setYear(Integer.parseInt(br.readLine()));

					System.out.println("수정할 사번  ? ");
					dto.setId(br.readLine());
					break;
					
				} catch (NumberFormatException e) {
					System.out.println("평가연도입력이 잘못되었습니다.");
					System.out.println("다시 입력하세요");
				} catch (Exception e) {
					System.out.println("일치하는 정보가 없습니다.");
					System.out.println("다시 입력하세요");
				}
			}

			
			System.out.println("수정할 평가점수 ?");
			dto.setGrade(Integer.parseInt(br.readLine()));

			System.out.println("수정할 평가날짜[YYYY-MM-DD] ?");
			dto.setDate(br.readLine());

			System.out.println("수정할 평가내용 ?");
			dto.setContent(br.readLine());

			System.out.println("수정할 평가자 사번 ?");
			dto.setPerid(br.readLine());

			dao.updateGrade(dto);

			System.out.println("평가등급 수정 완료!!!");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
		} catch (Exception e) {
			System.out.println("평가등급 수정 UI 에러");
			e.printStackTrace();
		}

	}

	private void deleteGrade() {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\n 평가등급 삭제");
		int year;
		String id;
		try {

			System.out.println("삭제할 평가연도 ?");
			year = Integer.parseInt(br.readLine());

			System.out.println("삭제할 사번  ? ");
			id = br.readLine();

			dao.DeleteGrade(year, id);
			

			System.out.println("평가등급 삭제 완료!!!");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
		}catch (NumberFormatException e) {
			System.out.println("일치하는 평가연도가 없습니다.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("평가등급 삭제 UI 에러");
			e.printStackTrace();
		}
	}
}
