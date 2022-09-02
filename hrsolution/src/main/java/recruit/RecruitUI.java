package recruit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import com.main.LoginDTO;





public class RecruitUI {
	private BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	private RecruitDAO dao=new RecruitDAOImpl();
	//private LoginDTO empdto;
	private LoginDTO logindto=null;

	public void recruitmenu(LoginDTO posdto) {
		int ch;
		logindto=posdto;
		try {
			System.out.println("채용 관리 메뉴 실행");
			System.out.println(posdto.getId()+" 님");
			do {
				System.out.print("1.채용공고등록 2.채용공고수정 3.채용공고삭제 4.채용전체리스트 5.채용접수자 검색 6.채용공고 제목 검색 7.메인 => ");
				ch = Integer.parseInt(br.readLine());
			} while(ch < 1 || ch > 7);
			
			
			System.out.println("부서는 "+posdto.getDeptno());
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
			case 6: findByposTitle(); break;
			//case 7: 
				//posdto = null;
				//System.out.println(); 
				//break;
			//case 7: return;
				
		
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

		
	public void insert() {
		System.out.println("채용공고등록");
	    String id;
		
		
		try {
			
			System.out.println("채용공고를 등록할 사번");
			id=br.readLine();
			
			/*if(!(logindto.getDeptno().equals("200"))){
				System.out.println("인사부만 등록가능합니다");
				return;
				*/
			//}
			RecruitDTO dto=new RecruitDTO();
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
		} catch (Exception e) {
			System.out.println("채용공고등록실패");
		}
		System.out.println();
	}
	
	public void update() {
		System.out.println("채용공고 수정");
		
	
		
		
		try {
			String id;
			System.out.println("채용공고를 수정할 사번");
			int result=0;
			
		    id=br.readLine();
		    /*if(!(logindto.getDeptno().equals("200"))){
				System.out.println("인사부만 등록가능합니다");
				return;
			}*/
			RecruitDTO dto=new RecruitDTO() ;
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
			 
		} catch (Exception e) {
			System.out.println("수정실패");
		}
	}
	
	public void delete() {
		System.out.println("채용공고 삭제");				
		String posNo;
		String id;
		int result=0;
		try {
			
			System.out.println("채용공고를 삭제할 사번");
			id=br.readLine();
			System.out.println("삭제할 채용공고");		
			posNo=br.readLine();
			
		/*	if(!(id == logindto.getId()&&(logindto.getDeptno().equals("200")))){
				System.out.println("인사부만 등록가능합니다");
				return;
			}*/
			
			//dao.deleteRecruit(posNo);
			result = dao.deleteRecruit(posNo);
	         if(result == 0) {
	            System.out.println("채용공고가 삭제가 안됩니다.");
	         } else {
	            System.out.println("채용공고가 삭제되었습니다.");
	         }
			
			
		} catch (Exception e) {
			System.out.println("삭제 실패!!!");
		}
		System.out.println();
	}
	
	public void listAll() {
		System.out.println("전체리스트");
		
		List<RecruitDTO>list=dao.listRecruit();
		for (RecruitDTO dto : list) {
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			System.out.println("채용공고번호 \t 채용인원 \t 소개 \t접수시작일 \t접수마감일 \t접수방법 \t전형절차 \t부서코드\t사번_채용담당 \t 채용공고명" );
			System.out.print(dto.getPosNo()+"\t");
			System.out.print(dto.getPosNum()+"\t");
			System.out.print(dto.getPos()+"\t");
			System.out.print(dto.getPosStart()+"\t");
			System.out.print(dto.getPosEnd()+"\t");
			System.out.print(dto.getPosMeans()+"\t");
			System.out.print(dto.getPosPro()+"\t");
			System.out.print(dto.getDeptNo()+"\t");
			System.out.print(dto.getId()+"\t");
			System.out.println(dto.getPosTitle());		
			System.out.println();
			System.out.println("채용접수번호 \t 이름 \t 생년월일 \t접수일자 \t면접날짜 \t지원경로  \t서류합격여부\t1차합격여부 \t 2차합격여부 \t전화번호" );
			System.out.print(dto.getApNo()+"\t");
			System.out.print(dto.getApName()+"\t");
			System.out.print(dto.getApBirth()+"\t");
			System.out.print(dto.getApDate()+"\t");
			System.out.print(dto.getApInterview()+"\t");
			System.out.print(dto.getApRoute()+"\t");
			System.out.print(dto.getApResult1()+"\t");
			System.out.print(dto.getApResult2()+"\t");
			System.out.print(dto.getApResult3()+"\t");
			System.out.println(dto.getApTel());
			
			System.out.println();
			System.out.println("사번 \t 채용접수번호 \t 서류평가점수 \t1차평가점수 \t2차평가점수 \t평가사유  " );
			System.out.print(dto.getId()+"\t");
			System.out.print(dto.getEvNo()+"\t");
			System.out.print(dto.getEvGrade1()+"\t");
			System.out.print(dto.getEvGrade2()+"\t");
			System.out.print(dto.getEvGrade3()+"\t");
			System.out.println(dto.getEvReason());
			
		}
		System.out.println();
	}
	
	public void findByApName() {
		System.out.println("이름으로검색");
		System.out.println("---------------------------------------------------------------------------------------------------------------");
		String apName;
		try {
			System.out.print("검색할 이름 ? ");
			apName = br.readLine();
			
			List<RecruitDTO>list=dao.listRecruit(apName);
			for (RecruitDTO dto : list) {
				System.out.println("---------------------------------------------------------------------------------------------------------------");
				System.out.println("채용공고번호 \t 채용인원 \t 소개 \t접수시작일 \t접수마감일 \t접수방법 \t전형절차 \t부서코드\t사번_채용담당 \t 채용공고명" );
				System.out.print(dto.getPosNo()+"\t");
				System.out.print(dto.getPosNum()+"\t");
				System.out.print(dto.getPos()+"\t");
				System.out.print(dto.getPosStart()+"\t");
				System.out.print(dto.getPosEnd()+"\t");
				System.out.print(dto.getPosMeans()+"\t");
				System.out.print(dto.getPosPro()+"\t");
				System.out.print(dto.getDeptNo()+"\t");
				System.out.print(dto.getId()+"\t");
				System.out.println(dto.getPosTitle());
				System.out.println();
				System.out.println("채용접수번호 \t 이름 \t 생년월일 \t접수일자 \t면접날짜 \t지원경로  \t서류합격여부\t1차합격여부 \t 2차합격여부 \t전화번호" );
				System.out.print(dto.getApNo()+"\t");
				System.out.print(dto.getApName()+"\t");
				System.out.print(dto.getApBirth()+"\t");
				System.out.print(dto.getApDate()+"\t");
				System.out.print(dto.getApInterview()+"\t");
				System.out.print(dto.getApRoute()+"\t");
				System.out.print(dto.getApResult1()+"\t");
				System.out.print(dto.getApResult2()+"\t");
				System.out.print(dto.getApResult3()+"\t");
				System.out.println(dto.getApTel());
			    System.out.println();
				System.out.println("사번 \t 채용접수번호 \t 서류평가점수 \t1차평가점수 \t2차평가점수 \t평가사유  " );
				System.out.print(dto.getId()+"\t");
				System.out.print(dto.getEvNo()+"\t");
				System.out.print(dto.getEvGrade1()+"\t");
				System.out.print(dto.getEvGrade2()+"\t");
				System.out.print(dto.getEvGrade3()+"\t");
				System.out.println(dto.getEvReason());
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findByposTitle() {
		System.out.println("채용공고제목으로 검색");
		
		
		String posTitle;
		try {
			System.out.print("검색할 공고제목 ? ");
			posTitle = br.readLine();
			
			List<RecruitDTO> list=dao.listRecruitTitle(posTitle);
			for (RecruitDTO dto : list) {
			System.out.println("---------------------------------------------------------------------------------------------------------------");
			System.out.println("채용공고번호 \t 채용인원 \t 소개 \t접수시작일 \t접수마감일 \t접수방법 \t전형절차 \t부서코드\t사번_채용담당 \t 채용공고명" );
			System.out.print(dto.getPosNo()+"\t");
			System.out.print(dto.getPosNum()+"\t");
			System.out.print(dto.getPos()+"\t");
			System.out.print(dto.getPosStart()+"\t");
			System.out.print(dto.getPosEnd()+"\t");
			System.out.print(dto.getPosNo()+"\t");
			System.out.print(dto.getPosMeans()+"\t");
			System.out.print(dto.getPosPro()+"\t");
			System.out.print(dto.getDeptNo()+"\t");
			System.out.print(dto.getId()+"\t");
			System.out.println(dto.getPosTitle());
			System.out.println();
			System.out.println("채용접수번호 \t 이름 \t 생년월일 \t접수일자 \t면접날짜 \t지원경로  \t서류합격여부\t1차합격여부 \t 2차합격여부 \t전화번호" );
			System.out.print(dto.getApNo()+"\t");
			System.out.print(dto.getApName()+"\t");
			System.out.print(dto.getApBirth()+"\t");
			System.out.print(dto.getApDate()+"\t");
			System.out.print(dto.getApInterview()+"\t");
			System.out.print(dto.getApRoute()+"\t");
			System.out.print(dto.getApResult1()+"\t");
			System.out.print(dto.getApResult2()+"\t");
			System.out.print(dto.getApResult3()+"\t");
			System.out.println(dto.getApTel());
			System.out.println();
			System.out.println("사번 \t 채용접수번호 \t 서류평가점수 \t1차평가점수 \t2차평가점수 \t평가사유  " );
			System.out.print(dto.getId()+"\t");
			System.out.print(dto.getEvNo()+"\t");
			System.out.print(dto.getEvGrade1()+"\t");
			System.out.print(dto.getEvGrade2()+"\t");
			System.out.print(dto.getEvGrade3()+"\t");
			System.out.println(dto.getEvReason());
			/*if(dto==null) {
				System.out.println("공고가 없습니다.");
				return;
				*/
				
			}
			System.out.println();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
