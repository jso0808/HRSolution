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

	// 연봉 수정
	@Override
	public void updateSalary(SalaryDTO sdto) throws SQLException {
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = null;
		
		
		try {	
			// 자동 커밋되지 않도록
			conn.setAutoCommit(false);
			// 업데이트 전 마지막 연봉 정보 가져와서 연봉종료일을 업데이트 연봉시작일로.
			sql = "UPDATE Salary SET salEnd=? "
					+ "WHERE salno = (SELECT MAX(salNo) FROM Salary WHERE id=?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sdto.getSalStart());
			pstmt.setString(2, sdto.getId());
			
			rs = pstmt.executeUpdate();
			pstmt.close();
			
			
			// 새로운 연봉 정보 추가
			sql = "INSERT INTO Salary(id,salNo,sal,salStart,memo) VALUES(?,sal_seq.NEXTVAL,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sdto.getId());
			pstmt.setString(2, sdto.getSal());
			pstmt.setString(3, sdto.getSalStart());
			pstmt.setString(4, sdto.getMemo());
			
			rs = pstmt.executeUpdate();
			pstmt.close();
			
			// 커밋 
			conn.commit();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			
			// 기본키 제약 위반, NOT NULL 등의 제약 위반 - 무결성 제약 위반시 발생
			if(e.getErrorCode() == 1400) { // NOT NULL
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

	// 전체 연봉 리스트
	@Override
	public List<SalaryDTO> listSalaryAll() {
		List<SalaryDTO> findList = new ArrayList<>();
		SalaryDTO saldto = new SalaryDTO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			/*
			sql = "SELECT name, s.id, salNo, sal, TO_CHAR(salStart,'YYYY-MM-DD')salstart, memo"
					+" FROM (SELECT id, ROW_NUMBER() OVER(PARTITION BY id ORDER BY Salstart DESC) as rn,"
					+" salNo, sal, salStart, NVL(memo,' ')memo FROM Salary) s JOIN Employee e ON e.id = s.id" 
					+" WHERE rn=1";
			*/
			
			sql = " SELECT name, s.id, position, dept, salNo, sal, TO_CHAR(salstart,'YYYY-MM')salstart, memo"
					+ " FROM("
					+ "    SELECT id, ROW_NUMBER() OVER(PARTITION BY id ORDER BY Salstart DESC) as st,"
					+ "    salNo, sal, salstart, NVL(memo, ' ')memo"
					+ "    FROM Salary"
					+ " ) s "
					+ " LEFT OUTER JOIN (SELECT paNo, id, paDate, deptNo, positionNo, "
					+ "        ROW_NUMBER() OVER(PARTITION BY id ORDER BY paDate DESC) as pd"
					+ "        FROM Employee_history"
					+ "        )emp_his ON emp_his.id = s.id"
					+ " LEFT OUTER JOIN Employee e ON e.id=s.id"
					+ " LEFT OUTER JOIN position p ON p.positionNo = emp_his.positionNo"
					+ " LEFT OUTER JOIN department d ON d.deptNum = emp_his.deptNo"
					+ " WHERE pd=1 AND st=1";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				saldto = new SalaryDTO();
				
				saldto.setName(rs.getString("name"));
				saldto.setId(rs.getString("id"));
				saldto.setDept(rs.getString("dept"));
				saldto.setPosition(rs.getString("position"));
				saldto.setSalNo(rs.getString("salNo"));
				saldto.setSal(rs.getString("sal"));
				saldto.setSalStart(rs.getString("salStart"));
				saldto.setMemo(rs.getString("memo"));
				
				findList.add(saldto);
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
		return findList;
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

	// 전체 사원 월별 급여 리스트
	@Override
	public List<PayDTO> listPayAll(String month) { 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int tot;
		
		try {
			
			sql = "SELECT emp_his.id, name, dept, position, paDate, payNo,paydate,paynormal,"
					+ " NVL(nationpen,0)nationpen, NVL(medicinsur,0)medicinsur,"
					+ " NVL(longinsur,0)longinsur,NVL(employeeinsur,0)employeeinsur,NVL(paymeal,0)paymeal,"
					+ " NVL(paywelfare,0)paywelfare,NVL(payextra,0)payextra, NVL(bonus,0)bonus,"
					+ " NVL(payover,0)payover,NVL(gapfee,0)gapfee,"
					+ " NVL(citizenfee,0)citizenfee, NVL(accidantinsur,0)accidantinsur"
					+ " FROM("
					+ "    SELECT paNo, id, paDate, deptNo, positionNo,"
					+ "        ROW_NUMBER() OVER(PARTITION BY id ORDER BY paDate DESC) as pd"
					+ "    FROM Employee_history"
					+ " )emp_his JOIN Employee e ON e.id = emp_his.id"
					+ " JOIN department d ON d.deptNum = emp_his.deptNo"
					+ " JOIN position p ON p.positionNo = emp_his.positionNo"
					+ " RIGHT OUTER JOIN Pay pay ON pay.id = emp_his.id"
					+ " WHERE pd=1 AND TO_CHAR(payDate,'YYYY-MM')= ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, month);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				
				paydto.setId(rs.getString("id"));
				paydto.setName(rs.getString("name"));
				paydto.setPosition(rs.getString("position"));
				paydto.setDept(rs.getString("dept"));
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
				// 총 지급 금액 계산
				// + 기본급,식대,복리후생비,기타지급,상여금,시간외수당 (6ea)
				// - 갑근세(소득세),4대보험,산재보험,주민세 (7ea)
				tot = paydto.getPaynormal()+paydto.getPaymeal()+paydto.getPaywelfare()+paydto.getPayextra()
					+paydto.getBonus()+paydto.getPayover() - paydto.getGapfee()-paydto.getMedicinsur()-paydto.getNationpen()
					-paydto.getEmployeeinsur()-paydto.getAccidantinsur()-paydto.getLonginsur()-paydto.getCitizenfee();
				
				paydto.setTot(tot);
				
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
		
		return plist;
	}

	// 특정 사원 전체 급여 리스트
	@Override
	public List<PayDTO> listPayEmp(String id) {
		List<PayDTO> findList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int tot;
		
		try {
			/*
			sql = "SELECT id, payno, payDate, payNormal,nationpen,medicinsur,longinsur,employeeinsur,"
					+ " paymeal,paywelfare,payextra,bonus,payover,gapfee,citizenfee,accidantinsur"
					+ " FROM Pay WHERE id=?";
			*/
			/*
			sql = "SELECT id, payno, payDate, payNormal,NVL(nationpen,0)nationpen, NVL(medicinsur,0)medicinsur, "
					+ " NVL(longinsur,0)longinsur,NVL(employeeinsur,0)employeeinsur,NVL(paymeal,0)paymeal,"
					+ " NVL(paywelfare,0)paywelfare,NVL(payextra,0)payextra, NVL(bonus,0)bonus,"
					+ " NVL(payover,0)payover ,NVL(gapfee,0)gapfee,"
					+ " NVL(citizenfee,0)citizenfee, NVL(accidantinsur,0)accidantinsur"
					+ " FROM pay WHERE id=? ";
			*/
			sql = "SELECT emp_his.id, name, dept, position, paDate, payNo,paydate,paynormal,"
					+ " NVL(nationpen,0)nationpen, NVL(medicinsur,0)medicinsur,"
					+ " NVL(longinsur,0)longinsur,NVL(employeeinsur,0)employeeinsur,NVL(paymeal,0)paymeal,"
					+ " NVL(paywelfare,0)paywelfare,NVL(payextra,0)payextra, NVL(bonus,0)bonus,"
					+ " NVL(payover,0)payover,NVL(gapfee,0)gapfee,"
					+ " NVL(citizenfee,0)citizenfee, NVL(accidantinsur,0)accidantinsur"
					+ " FROM("
					+ "    SELECT paNo, id, paDate, deptNo, positionNo,"
					+ "        ROW_NUMBER() OVER(PARTITION BY id ORDER BY paDate DESC) as pd"
					+ "    FROM Employee_history"
					+ " )emp_his JOIN Employee e ON e.id = emp_his.id"
					+ " JOIN department d ON d.deptNum = emp_his.deptNo"
					+ " JOIN position p ON p.positionNo = emp_his.positionNo"
					+ " RIGHT OUTER JOIN Pay pay ON pay.id = emp_his.id"
					+ " WHERE pd=1 AND emp_his.id=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				
				paydto.setId(rs.getString("id"));
				paydto.setName(rs.getString("name"));
				paydto.setPosition(rs.getString("position"));
				paydto.setDept(rs.getString("dept"));
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
				// 총 지급 금액 계산
				// + 기본급,식대,복리후생비,기타지급,상여금,시간외수당 (6ea)
				// - 갑근세(소득세),4대보험,산재보험,주민세 (7ea)
				tot = paydto.getPaynormal()+paydto.getPaymeal()+paydto.getPaywelfare()+paydto.getPayextra()
					+paydto.getBonus()+paydto.getPayover() - paydto.getGapfee()-paydto.getMedicinsur()-paydto.getNationpen()
					-paydto.getEmployeeinsur()-paydto.getAccidantinsur()-paydto.getLonginsur()-paydto.getCitizenfee();
				
				paydto.setTot(tot);
				
				findList.add(paydto);
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
