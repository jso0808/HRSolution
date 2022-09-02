package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.util.DBConn;


public class EmployeeDAOImpl implements EmployeeDAO {
	
	private Connection conn = DBConn.getConnection();
	Scanner sc = new Scanner(System.in);

	// 로그인 기능에 사용
	@Override
	public EmployeeDTO readMember(String id) {
		EmployeeDTO empdto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT id, pwd, tel FROM logintest WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				empdto = new EmployeeDTO();

				empdto.setId(rs.getString("id"));
				empdto.setPwd(rs.getString("pwd"));
				empdto.setTel(rs.getString("tel"));
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
		
		return empdto;
	}
	
	
  //신입사원 등록
	@Override
	
	public int insertEmployee(EmployeeDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO employee(id,name,rrn,pwd,email,tel,ft,hireDate,leaveDate,noWorking) VALUES (?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getRrn());
			pstmt.setString(4, dto.getPwd());
			pstmt.setString(5, dto.getEmail());
			pstmt.setString(6, dto.getTel());
			pstmt.setString(7, dto.getFt());
			pstmt.setString(8, dto.getHireDate());
			pstmt.setString(9, dto.getLeaveDate());
			pstmt.setString(10, dto.getNoWorking());
	
			result = pstmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			
			if(e.getErrorCode() == 1) {
				System.out.println("아이디 중복으로 등록이 불가능합니다.");
			} else if(e.getErrorCode() == 1400) {
				System.out.println("필수 입력 사항을 입력 하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}
			
			throw e;
			
		} catch (SQLDataException e) {
		
			if(e.getErrorCode() == 1840 || e.getErrorCode() == 1861) {
				System.out.println("날짜 입력 형식 오류 입니다.");
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
	
//사원정보수정 
@Override
public int updateEmployee(EmployeeDTO dto) throws SQLException {
	int result = 0;
	PreparedStatement pstmt = null;
	String sql;

	try {
		// UPDATE 테이블명 SET 컬럼=값, 컬럼=값 WHERE 조건
		sql = "UPDATE Employee SET pwd = ?, name =?,rrn =?,email =?,tel=?,ft=?,hireDate=?,leaveDate=?,noWorking=? where id = ? "; //물음표 위치가 틀리면 안돼 

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,dto.getPwd());
		pstmt.setString(2,dto.getName());
		pstmt.setString(3,dto.getRrn());
		pstmt.setString(4,dto.getEmail());
		pstmt.setString(5,dto.getTel());
		pstmt.setString(6,dto.getFt());
		pstmt.setString(7,dto.getHireDate());
		pstmt.setString(8,dto.getLeaveDate());
		pstmt.setString(9,dto.getNoWorking());
		pstmt.setString(10,dto.getId());
		result = pstmt.executeUpdate();
		
	} catch (SQLIntegrityConstraintViolationException e) {
		if (e.getErrorCode() == 1400) {
			System.out.println("필수 입력 사항을 입력하지 않았습니다.");
		} else {
			System.out.println(e.toString());
		}

		throw e;
	} catch (SQLDataException e) {
		if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861) {
			System.out.println("날짜 입력 형식 오류 입니다.");
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
public EmployeeDTO readEmployee(String id) {
	List<EmployeeDTO> list = new ArrayList<>();
	EmployeeDTO dto = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql;

try {   

		sql =  "SELECT e.id,name,email,tel,dept,p.position,e.ft,hireDate,leaveDate,noWorking"
			 + " FROM (SELECT paNo, id, paDate, deptNo,positionNo,ROW_NUMBER() OVER(PARTITION BY id ORDER BY paDate DESC) as pd FROM Employee_history) emp_his"
			 + " JOIN Employee e ON e.id = emp_his.id"
			 + " LEFT OUTER JOIN department d ON d.deptNum = emp_his.deptNo"
			 + " LEFT OUTER JOIN position p ON p.positionNo = emp_his.positionNo"
			 + " WHERE pd=1";
								
	/*
				+ " FROM (Select positionNo,id,deptNo From employee_history)employee e"
				+ " JOIN employee_history h ON e.id=h.id"
				+ " JOIN department d ON d.deptNum=h.deptNo"
				+ " JOIN position p ON p.positionNo = h.positionNo"
				+ " WHERE e.id=? "
				+ " ORDER BY e.id DESC";
		*/
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			
			dto = new EmployeeDTO();

			dto.setId(rs.getString("Id"));
			dto.setName(rs.getString("name"));
			dto.setEmail(rs.getString("email"));
			dto.setTel(rs.getString("tel"));
			dto.setDept(rs.getString("dept"));
			dto.setPos(rs.getString("position"));
			dto.setFt(rs.getString("Ft"));
			dto.setHireDate(rs.getString("hireDate"));
			dto.setLeaveDate(rs.getString("leaveDate"));
			dto.setLeaveDate(rs.getString("noWorking"));
			
			list.add(dto);
			
			/*
	 -- 사원의 이름,사번,(최신)부서,(최신)직급 정보 가져오기
SELECT emp_his.id, name, dept, position
FROM(
    SELECT paNo, id, paDate, deptNo, positionNo, 
        ROW_NUMBER() OVER(PARTITION BY id ORDER BY paDate DESC) as pd
    FROM Employee_history
)emp_his JOIN Employee e ON e.id = emp_his.id
LEFT OUTER JOIN department d ON d.deptNum = emp_his.deptNo
LEFT OUTER JOIN position p ON p.positionNo = emp_his.positionNo
WHERE pd=1;
	 */
			
			
			
				
		}

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

	return dto;
}


@Override
public int deleteEmployee(String id) throws SQLException {
	//DELETE FROM 테이블명 WHERE 조건
	int result = 0;
	PreparedStatement pstmt = null;
	String sql;
	try {
	sql = "DELETE FROM Employee  Where id = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1,id);
    
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

public int insertWorker(EmployeeDTO dto) throws SQLException {
	int result = 0;
	PreparedStatement pstmt = null;
	String sql;

		try {
			sql = "INSERT INTO worker (workerNo,id,workNo,proTitle,proStart,proEnd,proRate,project) VALUES (WORKERNO_seq.nextval,?,?,?,?,?,?,?)";
			
			
			//workerNo , id , workNo, proTitle,proStart,proEnd,proRate,project
			pstmt = conn.prepareStatement(sql); 
		
			pstmt.setString(1,dto.getId());
			pstmt.setString(2,dto.getWorkNo());
			pstmt.setString(3,dto.getProTitle());
			pstmt.setString(4,dto.getProStart());
			pstmt.setString(5,dto.getProEnd());
			pstmt.setString(6,dto.getProRate());
			pstmt.setString(7,dto.getProject());
			
			
	
			result = pstmt.executeUpdate();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getErrorCode() == 1400) {
				System.out.println("필수 입력 사항을 입력하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}

			throw e;
		} catch (SQLDataException e) {
			if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861) {
				System.out.println("날짜 입력 형식 오류 입니다.");
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

public int updateWorker(EmployeeDTO dto) throws SQLException {
	int result = 0;
	PreparedStatement pstmt = null;
	String sql;

	try {
		// UPDATE 테이블명 SET 컬럼=값, 컬럼=값 WHERE 조건
		sql = "UPDATE worker SET workNo=?, proTitle=?,proStart=?,proEnd=?,proRate=?,project=? where id=?  "; //물음표 위치가 틀리면 안돼 
		//workNo, proTitle,proStart,proEnd,proRate,project
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,dto.getWorkNo());
		pstmt.setString(2,dto.getProTitle());
		pstmt.setString(3,dto.getProStart());
		pstmt.setString(4,dto.getProEnd());
		pstmt.setString(5,dto.getProject());
		pstmt.setString(6,dto.getId());
		
		result = pstmt.executeUpdate();
		pstmt.close();
		
		//커밋
		conn.commit();
		
		System.out.println("업무이력 수정 완료 !!! ");
		
	} catch (SQLIntegrityConstraintViolationException e) {
		try {
			conn.rollback();
		} catch (Exception e2) {
		}
		
		
		if(e.getErrorCode() == 1400) { //not null
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
	return result;
}

@Override
public List<EmployeeDTO> listWork() {

	// TODO Auto-generated method stub
	return null;
}

}
