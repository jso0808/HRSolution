package attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class AttendanceDAOImpl implements AttendanceDAO{
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertAttendance(AttendanceDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO ATTENDANCE(ID, ATTNO, CIN, COUT, MEMO) "
					+ " VALUES(?, ATT_SEQ.NEXTVAL, TO_DATE(?,'YYYY-MM-DD hh24:mi') , TO_DATE(?,'YYYY-MM-DD hh24:mi'), ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getCIN());
			pstmt.setString(3, dto.getCOUT());
			pstmt.setString(4, dto.getMEMO());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			
			
		} catch (SQLDataException e) {
			if(e.getErrorCode() == 1840 || e.getErrorCode() == 1861) {
				System.out.println("형식에 맞게 날짜를 입력하세요.");
			} else {
				System.out.println(e.toString());
			}
			throw e;
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getErrorCode() == 1400) {
				System.out.println("필수 입력 사항을 입력 하지 않았습니다.");
			} else if (e.getErrorCode() == 2290) {
				System.out.println("퇴근시간을 출근시간 이후로 설정하세요.");
			}
			throw e;
		} catch (SQLException e) {
			if(e.getErrorCode() == 2291) {
				System.out.println("등록된 사번이 아닙니다.");
			} else {
				e.printStackTrace();
			}
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
	public int updateAttendance(AttendanceDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = " UPDATE ATTENDANCE SET CIN = TO_DATE(?, 'YYYY-MM-DD hh24:mi'), "
					+ " COUT = TO_DATE(?, 'YYYY-MM-DD hh24:mi'), MEMO = ? "
					+ " WHERE ATTNO = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCIN());
			pstmt.setString(2, dto.getCOUT());
			pstmt.setString(3, dto.getMEMO());
			pstmt.setString(4, dto.getAttNo());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode() == 14000) {
				System.out.println("필수 사항을 입력하세요.");
			} else {
				System.out.println(e.toString());
			}
			
			throw e;
		} catch (SQLDataException e) {
			if(e.getErrorCode() == 1840 || e.getErrorCode() == 1861) {
				System.out.println("형식에 맞게 날짜를 입력하세요.");
			} else {
				System.out.println(e.toString());
			}
			
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int deleteAttendance(String attNo) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			
			sql = " DELETE FROM ATTENDANCE WHERE attNo = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, attNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	@Override
	public List<AttendanceDTO> listAttendance(String date) throws SQLException {
		List<AttendanceDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT ATTNO, A.ID, E.name, TO_CHAR(CIN, 'YYYY-MM-DD HH24:MI') CIN, TO_CHAR(COUT, 'YYYY-MM-DD HH24:MI') COUT,"
					+ " NVL(MEMO, '-') MEMO"
					+ " FROM ATTENDANCE A " 
					+ " LEFT OUTER JOIN Employee E ON A.ID = E.id "
					+ " WHERE TO_CHAR(CIN, 'YYYY-MM') = ? "
					+ " ORDER BY A.ID, CIN " ;
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, date);
			
			rs = pstmt.executeQuery();
			// rs.next() 가 무엇인지 제대로 이해
			while(rs.next()) {
				AttendanceDTO dto = new AttendanceDTO();
				dto.setAttNo(rs.getString("ATTNO"));
				dto.setId(rs.getString("ID"));
				dto.setName(rs.getString("name"));
				dto.setCIN(rs.getString("CIN"));
				dto.setCOUT(rs.getString("COUT"));
				dto.setMEMO(rs.getString("MEMO"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
	public List<AttendanceDTO> readAttendacne(String id, String date) throws SQLException {
		List<AttendanceDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT ID, ATTNO, TO_CHAR(CIN, 'YYYY-MM-DD HH24:MI') CIN, TO_CHAR(COUT, 'YYYY-MM-DD HH24:MI') COUT, NVL(MEMO, '-') MEMO FROM ATTENDANCE "
					+ "WHERE id = ? AND TO_CHAR(CIN, 'YYYY-MM') = ?"
					+ "ORDER BY ATTNO" ; 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, date);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AttendanceDTO adto = new AttendanceDTO();
				adto.setAttNo(rs.getString("ATTNO"));
				adto.setId(rs.getString("ID"));
				adto.setCIN(rs.getString("CIN"));
				adto.setCOUT(rs.getString("COUT"));
				adto.setMEMO(rs.getString("MEMO"));
				
				list.add(adto);
			}
			
		} catch (SQLException e) {
			throw e;	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	@Override
	public int readWorkinghours(String id, String date) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql;
		int workingMins = 0;
		int working = 0;
		int lunch = 0;
		int afternoon = 0;
		
		try {
			// 정상출근
			sql = " SELECT ROUND(SUM(workingMins)) working "
					+ "	FROM ( "
					+ "	 SELECT id, ((COUT-CIN) * 24 * 60) workingMins FROM ATTENDANCE "
					+ "	 WHERE id = ? AND TO_CHAR(CIN,'YYYY-MM') = ? "
					+ " ) " ;
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, date);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) { // rs.next() 는 if/while 반드시 써야함 
				if(rs.getString("working")!= null) { // 문자열하고 null 비교
					working = Integer.parseInt(rs.getString("working"));
				}
			}
			
			pstmt.close();
			rs.close();
			
			// 점심시간 계산
			sql = " SELECT (COUNT(*) * 60) lunch FROM ATTENDANCE "
					+ " WHERE id = ? AND TO_CHAR(CIN,'YYYY-MM') = ? "
					+ " AND TO_CHAR(CIN, 'HH24:MI') <= '12:00' AND TO_CHAR(COUT, 'HH24:MI') >= '13:00' " ;
			
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, id);
				pstmt.setString(2, date);
				
				rs = pstmt.executeQuery();
				
			
				if (rs.next()) { 
					if (rs.getString("lunch") != null) {
						lunch = Integer.parseInt(rs.getString("lunch"));
					}
				}
				
			workingMins = working - lunch;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
		return workingMins;

	}

}
