package salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.util.DBConn;


public class SalaryDAOImpl implements SalaryDAO{
	private Connection conn = DBConn.getConnection();

	// 급여 지급
	@Override
	public void insertPay(PayDTO pdto) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = null;
		
		try {
			
			// 자동 커밋되지 않도록
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO Pay(id, payno, payDate, payNormal,nationpen,medicinsur,longinsur,employeeinsur) "
					// 사번,급여번호,급여년월일,기본급,국민연금,건강보험,장기요양보험,고용보험
					+ " VALUES() WHERE id=?";
			
			/*
			 nationpen 국민연금 = 월소득액 x 4.5%	
			 medicinsur 건강보험 = 월소득액 x 3.495%
			 longinsur 장기요양보험 = 건강보험 x 12.27%
			 employeeinsur 고용보험 = 월소득액 x 0.9%
			 */
			
			// 커밋 
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		
	}

	@Override
	public void updateSalary(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SalaryDTO> listSalaryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// 특정 사원 연봉 정보
	@Override
	public SalaryDTO listSalaryEmp(String id) {
		SalaryDTO saldto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT id,salNo,sal,salStart,selEnd,memo "
					+ " FROM Salary"
					+ " WHERE id=?"
					+ " ORDER BY salNo DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				saldto = new SalaryDTO();
				
				saldto.setId(rs.getString("id"));
				saldto.setSalNo(rs.getString("salNo"));
				saldto.setSal(rs.getString("sal"));
				saldto.setSalStart(rs.getString("salStart"));
				saldto.setSalEnd(rs.getString("salEnd"));
				saldto.setMemo(rs.getString("memo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return saldto;
	}

	@Override
	public List<PayDTO> listPayAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PayDTO> listPayEmp(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
