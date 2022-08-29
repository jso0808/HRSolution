package salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;


public class SalaryDAOImpl implements SalaryDAO{
	private Connection conn = DBConn.getConnection();
	private List<PayDTO> plist = new ArrayList<>();

	// 급여 지급
	@Override
	public void insertPay(PayDTO pdto) throws SQLException{
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = null;
		
		try {	
			// 자동 커밋되지 않도록
			conn.setAutoCommit(false);
			// 컬럼16개.
			sql = "INSERT INTO Pay(id, payNo, payDate, payNormal,nationpen,medicinsur,longinsur,employeeinsur,"
					// 1.사번,2.급여번호,3.급여년월일,4.기본급, 5.국민연금,6.건강보험,7.장기요양보험,8.고용보험
					+ " paymeal,paywelfare,payextra,bonus,payover,gapfee,citizenfee,accidantinsur) "
					// 9.식대,10.복리후생비,11.기타지급,12.상여금, 13.시간외수당,14.갑근세,15.주민세,16.산재보험
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			// 급여번호 시퀀스 만들고 초기값 컬럼갯수만큼 설정해서 넣거나,
			// 컬럼 갯수+1로 설정해서 넣기
			pstmt.setString(1, pdto.getId());
			pstmt.setString(2, pdto.getPayno());
			pstmt.setString(3, pdto.getPayDate());
			pstmt.setInt(4, pdto.getPaynormal());
			pstmt.setInt(5, pdto.getNationpen());
			pstmt.setInt(6, pdto.getMedicinsur());
			pstmt.setInt(7, pdto.getLonginsur());
			pstmt.setInt(8, pdto.getEmployeeinsur());
			pstmt.setInt(9, pdto.getPaymeal());
			pstmt.setInt(10, pdto.getPaywelfare());
			pstmt.setInt(11, pdto.getPayextra());
			pstmt.setInt(12, pdto.getBonus());
			pstmt.setInt(13, pdto.getPayover());
			pstmt.setInt(14, pdto.getGapfee());
			pstmt.setInt(15, pdto.getCitizenfee());
			pstmt.setInt(16, pdto.getAccidantinsur());
			
			rs = pstmt.executeUpdate();
			pstmt.close();
			
			// 커밋 
			conn.commit();
			
			System.out.println("지급 완료 !!! ");
			
		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			
			// 기본키 제약 위반, NOT NULL 등의 제약 위반 - 무결성 제약 위반시 발생
			if(e.getErrorCode() == 1) { // ORA-00001, 기본키 중복
				System.out.println("급여 번호 중복으로 등록 불가능합니다. ");
			} else if(e.getErrorCode() == 1400) { // NOT NULL
				System.out.println("필수 입력 사항을 입력하지 않았습니다. ");
			} else {
				System.out.println(e.toString());
			}
			
			throw e;
				
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
			sql = "SELECT id,salNo,sal,salStart,salEnd,memo "
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
		return plist;
	}

	@Override
	public List<PayDTO> listPayEmp(String id) {
		List<PayDTO> findList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			/*
			sql = "SELECT id, payno, payDate, payNormal,nationpen,medicinsur,longinsur,employeeinsur,"
					+ " paymeal,paywelfare,payextra,bonus,payover,gapfee,citizenfee,accidantinsur"
					+ " FROM Pay WHERE id=?";
			*/
			
			sql = "SELECT id, payno, payDate, payNormal,NVL(nationpen,0)nationpen, NVL(medicinsur,0)medicinsur, "
					+ " NVL(longinsur,0)longinsur,NVL(employeeinsur,0)employeeinsur,NVL(paymeal,0)paymeal,"
					+ " NVL(paywelfare,0)paywelfare,NVL(payextra,0)payextra, NVL(bonus,0)bonus,"
					+ " NVL(payover,0)payover ,NVL(gapfee,0)gapfee,"
					+ " NVL(citizenfee,0)citizenfee, NVL(accidantinsur,0)accidantinsur"
					+ " FROM pay WHERE id=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				System.out.println(rs.getString("payNo"));
				// 가져와짐. 리스트에 추가가 안되는 듯
				
				paydto.setId(rs.getString("id"));
				paydto.setPayno(rs.getString("payNo"));
				paydto.setPayDate(rs.getString("payDate"));
				paydto.setPaynormal(rs.getInt("paynormal"));
				paydto.setPaymeal(rs.getInt("paymeal"));
				paydto.setPaywelfare(rs.getInt("paywelfare"));
				paydto.setPayextra(rs.getInt("payextra"));
				paydto.setBonus(rs.getInt("bonus"));
				paydto.setPayover(rs.getInt("payover"));
				paydto.setGapfee(rs.getInt("gapfee"));
				paydto.setMedicinsur(rs.getInt("medicinsur"));
				paydto.setNationpen(rs.getInt("nationpen"));
				paydto.setEmployeeinsur(rs.getInt("employeeinsur"));
				paydto.setAccidantinsur(rs.getInt("accidantinsur"));
				paydto.setLonginsur(rs.getInt("longinsur"));
				paydto.setCitizenfee(rs.getInt("citizenfee"));
				
				plist.add(paydto);
			}

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
		
		return findList;
	}


}
