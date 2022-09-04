package recruit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import com.main.LoginDTO;
import com.main.ValidCheck;





public class RecruitUI {
	private BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	private RecruitDAO dao=new RecruitDAOImpl();
	// private LoginDTO logindto = null;
	private ValidCheck valchk = new ValidCheck();

	public void recruitmenu(LoginDTO posdto) {
		// logindto = posdto;
		int ch;
		while(true) {
		try {
			System.out.println("채용 관리 메뉴 실행");
			System.out.println(posdto.getId()+" 님");
			//do {
				System.out.print("1.채용공고등록 2.채용공고수정 3.채용공고삭제 4.채용전체리스트 5.채용접수자 검색 6.채용공고 제목 검색 7.메인 => ");
				ch = Integer.parseInt(br.readLine());
			//} while(ch < 1 || ch > 7);
			
			
			System.out.println("부서는 "+posdto.getDeptno());
			if (ch == 7) {//여기추가
				//return;
			}
			switch(ch) {
			case 1:  
				if(posdto.getDeptno().equals("200")) {
				insert(); 
				}else {
					System.out.println("접근권한이 없습니다.\n");
					return;
				}
				 break;
			case 2:
			if(posdto.getDeptno().equals("200")) {
				update(); 
				}else {
					System.out.println("접근권한이 없습니다.\n");
					return;
				}
			 break;
			case 3: 
				
				if(posdto.getDeptno().equals("200")) {
					delete(); 
					}else {
						System.out.println("접근권한이 없습니다.\n");
						return;
					}
				 break;
			case 4: listAll(); break;
			case 5: findByApName(); break;
			case 6: findByposTitle();  break; 
			case 7: return;
				
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

		
	public void insert() {
		System.out.println("채용공고등록");
	    String id;
		//int null;
		
		try {
			RecruitDTO dto=new RecruitDTO();
			
			while(true) {
				System.out.println("채용공고를 등록할 사번");
				id=br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setId(id);
					break;
				}
			}
			
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			//System.out.print("채용공고번호");
			//dto.setPosNo(br.readLine());
			
			System.out.print("채용인원");
			dto.setPosNum(Integer.parseInt(br.readLine()));
		
			System.out.print("소개");
			dto.setPos(br.readLine());
			
			System.out.print("접수시작일");
			dto.setPosStart(br.readLine());
			
			System.out.print("접수마감일");
			dto.setPosEnd(br.readLine());
			
			System.out.print("접수방법");
			dto.setPosMeans(br.readLine());
			
			System.out.print("전형절차");
			dto.setPosPro(br.readLine());
			
			System.out.print("부서코드");
			dto.setDeptNo(br.readLine());
			
			System.out.print("사번_채용담당");
			dto.setId(br.readLine());
			
			System.out.print("채용공고명");
			dto.setPosTitle(br.readLine());
			
			dao.inserRecruit(dto);
			
			System.out.println("채용공고등록 완료");
		} catch (NumberFormatException e) {
			e.printStackTrace();
	}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
	public void update() {
		System.out.println("채용공고 수정");
		int result = 0;
		
		try {
			String id=null;
			
			RecruitDTO dto=new RecruitDTO();
			
			while(true) {
				System.out.println("채용공고를 수정할 사번");
				id = br.readLine();
				
				if(valchk.isNumber(id)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					dto.setId(id);
					break;
				}
			}
			
			
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			System.out.print("채용공고번호");
			dto.setPosNo(br.readLine());
			System.out.print("채용인원");
			dto.setPosNum(Integer.parseInt(br.readLine()));
			System.out.print("소개");
			dto.setPos(br.readLine());
			System.out.print("접수시작일");
			dto.setPosStart(br.readLine());
			System.out.print("접수마감일");
			dto.setPosEnd(br.readLine());
			System.out.print("접수방법");
			dto.setPosMeans(br.readLine());
			System.out.print("전형절차");
			dto.setPosPro(br.readLine());
			System.out.print("부서코드");
			dto.setDeptNo(br.readLine());
			System.out.print("사번_채용담당");
			dto.setId(br.readLine());
			System.out.print("채용공고명");
			dto.setPosTitle(br.readLine());
			
			// dao.updateRecruit(dto);
			  result = dao.updateRecruit(dto);
	         if(result == 0) {
	            System.out.println("채용공고가 수정이 안됩니다.");
	         } else {
	            System.out.println("채용공고가 수정되었습니다.");
	         }
			 
		} catch (NumberFormatException e) {
			System.out.println("채용공고번호가 잘못되었습니다.");
			System.out.println("수정실패");
		}catch (Exception e) {
			System.out.println("수정실패");
		}
	}
	
	public void delete() {
		System.out.println("채용공고 삭제");				
		String posNo;
		String id;
		int result=0;
		try {
			
			List<RecruitDTO>list=dao.listRecruit();
			System.out.println();
			System.out.println("---------------------------------------------------------------------");
			
			for (RecruitDTO dto : list) {
				System.out.println("공고번호\t채용부서\t채용공고명");
				System.out.print("★"+dto.getPosNo()+"★");
				System.out.print(dto.getDept());
				System.out.println(dto.getPosTitle());
			}
			
			System.out.println("---------------------------------------------------------------------");
			
			System.out.print("채용공고를 삭제할 사번 ? ");
			id=br.readLine();
			
			while(true) {
				System.out.print("삭제할 ★채용공고번호★ ? ");
				posNo=br.readLine();
				
				if(valchk.isNumber(posNo)==false) {
					System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요. \n");
				} else {
					break;
				}
			}
		/*	if(!(id == logindto.getId()&&(logindto.getDeptno().equals("200")))){
				System.out.println("인사부만 등록가능합니다");
				return;
			}*/
			
			//dao.deleteRecruit(posNo);
			result = dao.deleteRecruit(posNo);
	         if(result == 0) {
	            System.out.println("존재하지 않는 공고번호입니다.");
	         } else {
	            System.out.println("채용공고가 삭제되었습니다.");
	         }
			
			
		} catch (NumberFormatException e) {
			System.out.println("채용공고번호가 올바르지 않습니다.");
			System.out.println("삭제 실패!!!");
		}catch (Exception e) {
			System.out.println("삭제 실패!!!");
		}
		System.out.println();
	}
	
	public void listAll() {
		System.out.println("[채용 공고 리스트]");
		
		List<RecruitDTO>list=dao.listRecruit();
		System.out.println();
		System.out.println("---------------------------------------------------------------------");
		
		for (RecruitDTO dto : list) {
			System.out.println("\t["+dto.getPosTitle()+"]" + "                 채용공고번호 : " + dto.getPosNo() );
			System.out.println("채용 부서:    "+dto.getDept());
			System.out.println("채용 인원:    "+dto.getPosNum());
			System.out.println("소개: "+dto.getPos());
			System.out.println("접수시작일:    "+dto.getPosStart());
			System.out.println("접수마감일:    "+dto.getPosEnd());
			System.out.println("전형절차:   "+dto.getPosPro());
			System.out.println("접수방법:   "+dto.getPosMeans());
			System.out.println();
		}
		System.out.println("---------------------------------------------------------------------");
		
		System.out.println();
	}
	/*
	private String APpass1(String ApResult1) {
	try {
		if(ApResult1.equals("1")){
		return "합격";
	}else if(ApResult1.equals("0")) {
		return "불합격";	
	}else {
		return "평가x";
	}
	} catch (NullPointerException e) {
		//System.out.println("평가x");
	}catch (Exception e) {
		//System.out.println("평가x");
	}
	return ApResult1;
		
	}
	private String APpass2(String ApResult2) {
		try {
			if(ApResult2.equals("1")){
			return "합격";
		}else if(ApResult2.equals("0")) {
			return "불합격";	
		}else {
			return "평가x";
		}
		} catch (NullPointerException e) {
			//System.out.println("평가x");
		}catch (Exception e) {
			//System.out.println("평가x");
		}
		return ApResult2;
			
		}
	
	private String APpass3(String ApResult3) {
		try {
			if(ApResult3.equals("1")){
			return "합격";
		}else if(ApResult3.equals("0")) {
			return "불합격";	
		}else {
			return "평가x";
		}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			//System.out.println("평가x");
		}
		return ApResult3;
			
		}
	*/
	
	private String APpass1(String ApResult1) {
		if (ApResult1.equals("1")) {
			return "합격";
		} else if (ApResult1.equals("0")) {
			return "불합격";
		} else {
			return "평가x";
		}
	}

	private String APpass2(String ApResult2) {
		if (ApResult2.equals("1")) {
			return "합격";
		} else if (ApResult2.equals("0")) {
			return "불합격";
		} else {
			return "평가x";
		}
	}

	private String APpass3(String ApResult3) {
		if (ApResult3.equals("1")) {
			return "합격";
		} else if (ApResult3.equals("0")) {
			return "불합격";
		} else {
			return "평가x";
		}
	}
	
	private String interview(String interview) {
		if (interview==null) {
			return "미정\t";
		} 
			return interview;
	}
		
	/*
	private String APpass1(String ApResult1) {
		if(ApResult1.equals("1")){
			return "합격";
		}else  {
			return "불합격";	
		
		}
	}
	private String APpass2(String ApResult2) {
		if(ApResult2.equals("1")){
			return "합격";
		}else  {
			return "불합격";	
		
		}
	}
		
	private String APpass3(String ApResult3) {
		if(ApResult3.equals("1")){
			return "합격";
		}else  {
			return "불합격";	
		
		}
	}
	*/
		
		
	public void findByApName() {
		System.out.println("[채용접수자 이름 검색]");
		System.out.println("---------------------------------------------------------------------------------------------------------------");
		String apName;
		try {
			System.out.print("검색할 이름 ? ");
			apName = br.readLine();
			
			List<RecruitDTO>list=dao.listRecruit(apName);
			for (RecruitDTO dto : list) {
				
				System.out.println("[지원공고번호: "+dto.getPosNo()+ "     지원공고명: "+dto.getPosTitle()+"]");
				System.out.println("접수번호\t이름\t생년월일\t\t접수일자\t\t면접날짜\t\t서류합격\t1차합격\t2차합격\t전화번호" );
				System.out.print(dto.getApNo()+"\t");
				System.out.print(dto.getApName()+"\t");
				System.out.print(dto.getApBirth()+"\t");
				System.out.print(dto.getApDate()+"\t");
				System.out.print(interview(dto.getApInterview())+"\t");
				System.out.print(APpass1(dto.getApResult1())+"\t");
				System.out.print(APpass2(dto.getApResult2())+"\t");
				System.out.print(APpass3(dto.getApResult3())+"\t");
				System.out.println(dto.getApTel());			
				System.out.println();
				
				System.out.println("평가자번호\t평가자사번\t서류점수\t1차점수\t2차점수\t평가사유  " );
				System.out.print(dto.getEvNo()+"\t");
				System.out.print(dto.getId()+"\t");
				System.out.print(dto.getEvGrade1()+"\t");
				System.out.print(dto.getEvGrade2()+"\t");
				System.out.print(dto.getEvGrade3()+"\t");
				System.out.println(dto.getEvReason());
				System.out.println("---------------------------------------------------------------------------------------------------------------");
				
			}
			System.out.println();
		
		}catch (Exception e) {
			e.printStackTrace();
			}
	}

	
	public void findByposTitle() {
		System.out.println("[채용접수자 공고명 검색]");
		
		
		String posTitle;
		try {
			System.out.print("검색할 공고명 ? ");
			posTitle = br.readLine();
			
			List<RecruitDTO> list=dao.listRecruitTitle(posTitle);
			
			if(list==null) {
				System.out.println("검색된 공고가 존재하지 않습니다. ");
				return;
			}
			
			for (RecruitDTO dto : list) {
				
				System.out.println("[지원공고번호: " + dto.getPosNo() + "     지원공고명: " + dto.getPosTitle() + "]");
				
				if(dto.getApName()==null) {
					System.out.println("해당 채용공고는 접수자가 존재하지 않습니다. ");
					break;
				}

				System.out.println("접수번호\t이름\t생년월일\t\t접수일자\t\t면접날짜\t\t서류합격\t1차합격\t2차합격\t전화번호");
				System.out.print(dto.getApNo() + "\t");
				System.out.print(dto.getApName() + "\t");
				System.out.print(dto.getApBirth() + "\t");
				System.out.print(dto.getApDate() + "\t");
				System.out.print(interview(dto.getApInterview()) + "\t");
				System.out.print(APpass1(dto.getApResult1()) + "\t");
				System.out.print(APpass2(dto.getApResult2()) + "\t");
				System.out.print(APpass3(dto.getApResult3()) + "\t");
				System.out.println(dto.getApTel());
				System.out.println();

				System.out.println("평가자번호\t평가자사번\t서류점수\t1차점수\t2차점수\t평가사유  ");
				System.out.print(dto.getEvNo() + "\t");
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getEvGrade1() + "\t");
				System.out.print(dto.getEvGrade2() + "\t");
				System.out.print(dto.getEvGrade3() + "\t");
				System.out.println(dto.getEvReason());
				System.out.println("---------------------------------------------------------------------------------------------------------------");

			}
			
			System.out.println();
			
			System.out.println();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}

