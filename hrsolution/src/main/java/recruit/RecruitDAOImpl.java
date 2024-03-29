package recruit;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


import com.util.DBConn;






public  class RecruitDAOImpl implements RecruitDAO{
	private Connection conn = DBConn.getConnection();
	@Override
	public int inserRecruit(RecruitDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO posting(posNo,posNum,pos,posStart,posEnd,posMeans,PosPro,deptNo,id,posTitle) VALUES (POS_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//TO_CHAR(posStart,'YYYY-MM-DD'),TO_CHAR(posEnd,'YYYY-MM-DD') pos_seq.NEXTVL <-?
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, dto.getPosNo());
			pstmt.setInt(1, dto.getPosNum());
			pstmt.setString(2, dto.getPos());
			pstmt.setString(3, dto.getPosStart());
			pstmt.setString(4, dto.getPosEnd());
			pstmt.setString(5, dto.getPosMeans());
			pstmt.setString(6, dto.getPosPro());
			pstmt.setString(7, dto.getDeptNo());
			pstmt.setString(8, dto.getId());
			pstmt.setString(9, dto.getPosTitle());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLIntegrityConstraintViolationException e) {
			// 기본키 제약 위반, NOT NULL 등의 제약 위반 - 무결성 제약 위반시 발생
						if(e.getErrorCode() == 1) { // ORA-00001, 기본키 중복
							System.out.println("  중복으로 등록 불가능합니다. ");
						}else if(e.getErrorCode()==1400){ 
				System.out.println("필수 입력사항을 입력하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;
		} catch (SQLDataException e) {
			
			if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
		
	}

	@Override
	public int updateRecruit(RecruitDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE  posting SET posNum=?, pos=?, posStart=?,posEnd=?, posMeans=?, PosPro=?, deptNo=?, id=?, posTitle=? WHERE posNo=?";

			pstmt = conn.prepareStatement(sql);
		
			    pstmt.setInt(1, dto.getPosNum());
			    pstmt.setString(2, dto.getPos());
			    pstmt.setString(3, dto.getPosStart());
			    pstmt.setString(4, dto.getPosEnd());
			    pstmt.setString(5, dto.getPosMeans());
			    pstmt.setString(6, dto.getPosPro());
			    pstmt.setString(7, dto.getDeptNo());
			    pstmt.setString(8, dto.getId());
			    pstmt.setString(9, dto.getPosTitle());
				pstmt.setString(10, dto.getPosNo());

			
			

			result = pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode() == 1) { // ORA-00001, 기본키 중복
				System.out.println(" 중복으로 등록 불가능합니다. ");
			}else if(e.getErrorCode()==1400){ 
				System.out.println("필수 입력사항을 입력하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;
		} catch (SQLDataException e) {
			
			if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int deleteRecruit(String posNo) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM  posting WHERE posNo=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, posNo);
			

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
   @Override
 
   public   List<RecruitDTO> listRecruitTitle(String posTitle) {
		//posNo
	   List<RecruitDTO> list = new ArrayList<>();
		RecruitDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
	
		try {
		
			
			sql = "SELECT po.posNo, posNum, pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,TO_CHAR(posEnd,'YYYY-MM-DD')posEnd, posMeans, posPro, deptNo, po.id poid, posTitle, "
					+ " ap.apNo, apName, TO_CHAR(apBirth,'YYYY-MM-DD')apBirth, TO_CHAR(apDate,'YYYY-MM-DD')apDate, TO_CHAR(apInterview,'YYYY-MM-DD')apInterview, apRoute, NVL(apResult1 , 'x')apResult1,NVL(apResult2 , 'x')apResult2, NVL(apResult3 , 'x')apResult3, NVL(apTel , 'x')apTel, "
					+ " NVL(evNo , 'x')evNo, NVL(ev.id , 'x')evid, evGrade1,evGrade2, evGrade3, NVL(evReason , 'x')evReason"
					+ " From Posting po"
					+ " LEFT OUTER JOIN Applicant ap ON po.posNo=ap.posNo"
					+ " LEFT OUTER JOIN Evaluator ev ON ap.apNo=ev.apNo"
			        + " WHERE INSTR(posTitle, ?)>=1";
			
			/*
			sql = "SELECT posno, dept, postitle, posnum, NVL(pos,' '), TO_CHAR(posend,'YYYY-MM-DD') posmeans, pospro"
					+ " FROM posting p"
					+ " LEFT OUTER JOIN department d ON d.deptNum=p.deptNo";
					*/
			/* "SELECT po.posNo, posNum, pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,TO_CHAR(posEnd,'YYYY-MM-DD')posEnd, posMeans, posPro, deptNo, po.id poid, posTitle, "
					+ " ap.apNo, apName, TO_CHAR(apBirth,'YYYY-MM-DD')apBirth, TO_CHAR(apDate,'YYYY-MM-DD')apDate, TO_CHAR(apInterview,'YYYY-MM-DD')apInterview, apRoute, NVL(apResult1 , 'x')apResult1,NVL(apResult2 , 'x')apResult2, NVL(apResult3 , 'x')apResult3, NVL(apTel , 'x')apTel, "
					+ " NVL(evNo , 'x')evNo, NVL(ev.id , 'x')evid, evGrade1,evGrade2, evGrade3, NVL(evReason , 'x')evReason"
					+ " From Posting po"
					+ " LEFT OUTER JOIN Applicant ap ON po.posNo=ap.posNo"
					+ " LEFT OUTER JOIN Evaluator ev ON ap.apNo=ev.apNo"
					*/
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, posTitle);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new RecruitDTO();

				dto.setPosNo(rs.getString("posNo"));
				dto.setPosNum(rs.getInt("posNum"));
				dto.setPos(rs.getString("pos"));
				dto.setPosStart(rs.getString("posStart"));
				dto.setPosEnd(rs.getString("posEnd"));
				dto.setPosMeans(rs.getString("posMeans"));
				dto.setPosPro(rs.getString("posPro"));
				dto.setDeptNo(rs.getString("deptNo"));
				dto.setId(rs.getString("Poid"));
				dto.setPosTitle(rs.getString("posTitle"));
				//위에는 채용공고테이블 
				dto.setApNo(rs.getString("apNo"));
				dto.setApName(rs.getString("apName"));
				dto.setApBirth(rs.getString("apBirth"));
				dto.setApDate(rs.getString("apDate"));
				dto.setApInterview(rs.getString("apInterview"));
				dto.setApRoute(rs.getString("apRoute"));
				dto.setApResult1(rs.getString("apResult1"));
				dto.setApResult2(rs.getString("apResult2"));
				dto.setApResult3(rs.getString("apResult2"));
				dto.setApTel(rs.getString("apTel"));
				
				dto.setEvNo(rs.getString("EvNo"));
				dto.setId(rs.getString("Evid"));				
			    dto.setEvGrade1(rs.getInt("EvGrade1"));
				dto.setEvGrade2(rs.getInt("EvGrade1"));
				dto.setEvGrade3(rs.getInt("EvGrade1"));
				dto.setEvReason(rs.getString("EvReason"));
				
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		
		return list;
	
	}

	@Override
	public List<RecruitDTO> listRecruit() {
		List<RecruitDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			/*
			 sql = "SELECT po.posNo, posNum, pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,TO_CHAR(posEnd,'YYYY-MM-DD')posEnd, posMeans, posPro, deptNo, po.id poid, posTitle, "
					+ " ap.apNo, apName, TO_CHAR(apBirth,'YYYY-MM-DD')apBirth, TO_CHAR(apDate,'YYYY-MM-DD')apDate, TO_CHAR(apInterview,'YYYY-MM-DD')apInterview, apRoute, apResult1, apResult2, apResult3, apTel, "
					+ " evNo, ev.id evid, evGrade1, evGrade2, evGrade3, evReason"
					+ " From Posting po"
					+ " LEFT OUTER JOIN Applicant ap ON po.posNo=ap.posNo"
					+ " LEFT OUTER JOIN Evaluator ev ON ap.apNo=ev.apNo";
			 */
			/*
			 sql = "SELECT po.posNo,posNum, pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,TO_CHAR(posEnd,'YYYY-MM-DD')posEnd, posMeans, posPro, deptNo, po.id poid, posTitle, " 			            
						+ " ap.apNo, NVL(apName , 'x')apName, TO_CHAR(apBirth,'YYYY-MM-DD')apBirth, TO_CHAR(apDate,'YYYY-MM-DD')apDate, TO_CHAR(apInterview,'YYYY-MM-DD')apInterview, NVL(apRoute, 'x')apRoute, NVL(apResult1 , 'x')apResult1,NVL(apResult2 , 'x')apResult2, NVL(apResult3 , 'x')apResult3, NVL(apTel, 'x')apTel, "
						+ " NVL(evNo , 'x')evNo, NVL(ev.id , 'x')evid, evGrade1, evGrade2, evGrade3, NVL(evReason , 'x')evReason"
						+ " From Posting po"
						+ " LEFT OUTER JOIN Applicant ap ON po.posNo=ap.posNo"
						+ " LEFT OUTER JOIN Evaluator ev ON ap.apNo=ev.apNo";
					*/
			
			sql = "SELECT posno, dept, postitle, posnum, NVL(pos,' ')pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,"
					+ " TO_CHAR(posend,'YYYY-MM-DD')posend, posmeans, pospro"
					+ " FROM posting p"
					+ " LEFT OUTER JOIN department d ON d.deptNum=p.deptNo";
			
			pstmt = conn.prepareStatement(sql);
		
			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecruitDTO dto = new RecruitDTO();

				dto.setPosNo(rs.getString("posNo"));
				dto.setPosNum(rs.getInt("posNum"));
				dto.setPos(rs.getString("pos"));
				dto.setPosStart(rs.getString("posStart"));
				dto.setPosEnd(rs.getString("posEnd"));
				dto.setPosMeans(rs.getString("posMeans"));
				dto.setPosPro(rs.getString("posPro"));
				dto.setPosTitle(rs.getString("posTitle"));
				dto.setDept(rs.getString("dept"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

	@Override
	public List<RecruitDTO> listRecruit(String apName) {
		List<RecruitDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			/*
			sql = "SELECT po.posNo, posNum, pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,TO_CHAR(posEnd,'YYYY-MM-DD')posEnd, posMeans, posPro, deptNo, po.id poid, posTitle, "
						+ " ap.apNo, apName, TO_CHAR(apBirth,'YYYY-MM-DD')apBirth, TO_CHAR(apDate,'YYYY-MM-DD')apDate, TO_CHAR(apInterview,'YYYY-MM-DD')apInterview, apRoute, apResult1, apResult2, apResult3, apTel, "
						+ " evNo, ev.id evid, evGrade1, evGrade2, evGrade3, evReason"
						+ " From Posting po"
						+ " LEFT OUTER JOIN Applicant ap ON po.posNo=ap.posNo"
						+ " LEFT OUTER JOIN Evaluator ev ON ap.apNo=ev.apNo"
			            + " WHERE INSTR(apName, ?)>=1";*/
			 
			sql = "SELECT po.posNo, posNum, pos, TO_CHAR(posStart,'YYYY-MM-DD')posStart,TO_CHAR(posEnd,'YYYY-MM-DD')posEnd, posMeans, posPro, deptNo, po.id poid, posTitle, "
					+ " ap.apNo, apName, TO_CHAR(apBirth,'YYYY-MM-DD')apBirth, TO_CHAR(apDate,'YYYY-MM-DD')apDate, TO_CHAR(apInterview,'YYYY-MM-DD')apInterview, apRoute, NVL(apResult1 , 'x')apResult1,NVL(apResult2 , 'x')apResult2, NVL(apResult3 , 'x')apResult3, NVL(apTel , 'x')apTel, "
					+ " NVL(evNo , 'x')evNo, NVL(ev.id , 'x')evid, evGrade1,evGrade2, evGrade3, NVL(evReason , 'x')evReason"
					+ " From Posting po"
					+ " LEFT OUTER JOIN Applicant ap ON po.posNo=ap.posNo"
					+ " LEFT OUTER JOIN Evaluator ev ON ap.apNo=ev.apNo"
		            + " WHERE INSTR(apName, ?)>=1";
			//NVL(evNo , '1'),ev.id evid
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, apName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecruitDTO dto = new RecruitDTO();
	           
				dto.setPosNo(rs.getString("posNo"));
				dto.setPosNum(rs.getInt("posNum"));
				dto.setPos(rs.getString("pos"));
				dto.setPosStart(rs.getString("posStart"));
				dto.setPosEnd(rs.getString("posEnd"));
				dto.setPosMeans(rs.getString("posMeans"));
				dto.setPosPro(rs.getString("posPro"));
				dto.setDeptNo(rs.getString("deptNo"));
				dto.setId(rs.getString("Poid"));
				dto.setPosTitle(rs.getString("posTitle"));
				//위에는 채용공고테이블 
			    dto.setApNo(rs.getString("apNo"));
				dto.setApName(rs.getString("apName"));
				dto.setApBirth(rs.getString("apBirth"));
				dto.setApDate(rs.getString("apDate"));
				dto.setApInterview(rs.getString("apInterview"));
				dto.setApRoute(rs.getString("apRoute"));
				dto.setApResult1(rs.getString("apResult1"));
				dto.setApResult2(rs.getString("apResult2"));
				dto.setApResult3(rs.getString("apResult2"));
				dto.setApTel(rs.getString("apTel"));
				
				dto.setEvNo(rs.getString("EvNo"));
			    dto.setId(rs.getString("Evid"));				
				dto.setEvGrade1(rs.getInt("EvGrade1"));
				dto.setEvGrade2(rs.getInt("EvGrade1"));
				dto.setEvGrade3(rs.getInt("EvGrade1"));
				dto.setEvReason(rs.getString("EvReason"));
				
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

	@Override
	public int insertApplicant(RecruitDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO applicant(apNo, apName, apBirth, apDate, apRoute, posNo, apTel) VALUES ( APP_SEQ.NEXTVAL, ?, ?, ?, ?, ?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getApName());
			pstmt.setString(2, dto.getApBirth());
			pstmt.setString(3, dto.getApDate());
			pstmt.setString(4, dto.getApRoute());
			pstmt.setString(5, dto.getPosNo());
			pstmt.setString(6, dto.getApTel());
			
			
			result = pstmt.executeUpdate();
		
		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode() == 1) { // ORA-00001, 기본키 중복
				System.out.println(" 중복으로 등록 불가능합니다. ");
			}else if(e.getErrorCode()==1400){ 
				System.out.println("필수 입력사항을 입력하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;
		} catch (SQLDataException e) {
			
			if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
		
	}
	
	

	

}