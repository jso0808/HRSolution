package recruit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


import employee.EmployeeDTO;

public class RecruitUI {
	private BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	private RecruitDAO dao=new RecruitDAOImpl();
	
	public void recruitmenu(EmployeeDTO empdto) {
		int ch;
		
			System.out.println("채용 관리 메뉴 실행");
		System.out.println(empdto.getId()+" 님");
	try {
		do {
			System.out.print("1.채용접수등록 2.채용공고수정 3.채용공고삭제 4.채용전체리스트 5.채용접수자 검색 6.채용공고 검색 7.메인 => ");
			ch = Integer.parseInt(br.readLine());
		} while(ch < 1 || ch > 6);
		
		switch(ch) {
		case 1:  insert(); break;
		case 2:update(); break;
		case 3: delete(); break;
		case 4: listAll(); break;
		case 5: findByApName(); break;
		case 6: findByposNo(); break;
		case 7: // MainUI.mainmenu(); break;
	
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

		
	public void insert() {
		System.out.println("채용공고등록");
		try {
			RecruitDTO dto=new RecruitDTO();
			System.out.print("채용공고번호");
			dto.setPosNo(br.readLine());
			System.out.print("채용인원");
			dto.setPosNum(br.read());
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
			RecruitDTO dto=new RecruitDTO() ;
			System.out.print("채용공고번호");
			dto.setPosNo(br.readLine());
			System.out.print("채용인원");
			dto.setPosNum(br.read());
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
			
			 dao.updateRecruit(dto);
			 
			 System.out.println("채용공고가 수정 되었습니다.");
		} catch (Exception e) {
			System.out.println("수정실패");
		}
	}
	
	public void delete() {
		System.out.println("채용공고 삭제");
		String posNo;
		try {
			System.out.println("삭제할 채용공고");
			posNo=br.readLine();
			
			dao.deleteRecruit(posNo);
			
			System.out.println("회원 정보가 삭제되었습니다");
		} catch (Exception e) {
			System.out.println("삭제 실패!!!");
		}
		System.out.println();
	}
	
	public void listAll() {
		System.out.println("전체리스트");
		List<RecruitDTO>list=dao.listRecruit();
		for (RecruitDTO dto : list) {
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
			System.out.print(dto.getPosTitle()+"\t");
		}
		System.out.println();
	}
	
	public void findByApName() {
		System.out.println("이름으로검색");
		String apName;
		try {
			System.out.print("검색할 이름 ? ");
			apName = br.readLine();
			
			RecruitDTO dto=dao.readRecruit(apName);
			if(dto==null) {
				System.out.println("이름이 없습니다.");
				return;
			}
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
			System.out.print(dto.getPosTitle()+"\t");
			
		} catch (Exception e) {
			
		}
	}
	
	public void findByposNo() {
		System.out.println("채용공고번호로 검색");
		String posNo;
		try {
			System.out.print("검색할 이름 ? ");
			posNo = br.readLine();
			
			RecruitDTO dto=dao.readRecruit(posNo);
			if(dto==null) {
				System.out.println("이름이 없습니다.");
				return;
			}
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
			System.out.print(dto.getPosTitle()+"\t");
			
		} catch (Exception e) {
			
		}
	}
}
