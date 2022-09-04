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

import salary.SalaryDTO;


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
			pstmt.close();
			pstmt = null;
			
			sql = "INSERT INTO employee_history(deptNo,posNo) values (?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getDept());
			pstmt.setString(2, dto.getPos());
			pstmt.close();

	
	
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
		sql = "UPDATE Employee SET name =?,rrn =?,email =?,tel=?,ft=?,hireDate=?,leaveDate=?,noWorking=? where id = ? "; //물음표 위치가 틀리면 안돼 

		pstmt = conn.prepareStatement(sql);
		
		
		pstmt.setString(1,dto.getName());
		pstmt.setString(2,dto.getRrn());
		pstmt.setString(3,dto.getEmail());
		pstmt.setString(4,dto.getTel());
		pstmt.setString(5,dto.getFt());
		pstmt.setString(6,dto.getHireDate());
		pstmt.setString(7,dto.getLeaveDate());
		pstmt.setString(8,dto.getNoWorking());
		pstmt.setString(9,dto.getId());
		result = pstmt.executeUpdate();
		pstmt.close();
		pstmt = null;
		
		sql = "UPDATE Employee_history SET deptNo=?,positionNo=? where id = ? "; //물음표 위치가 틀리면 안돼 
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getDept());
		pstmt.setString(2, dto.getPos());
		pstmt.setString(3, dto.getId());
		
		result=pstmt.executeUpdate();
		pstmt.close();
		
		
		
		
		
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
			 + " WHERE e.id=?";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
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
			dto.setNoWorking(rs.getString("noworking"));
			
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
		
		conn.setAutoCommit(false);
		// 업무리스트 수정하기 
		sql = "UPDATE worker SET workNo=?, proTitle=?,proStart=?,proEnd=?,proRate=?,project=? where workerNo=?  "; //물음표 위치가 틀리면 안돼 
		//workNo, proTitle,proStart,proEnd,proRate,project
		// 담당 업무 수정. workNo, workerNo 혼동 주의
	
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,dto.getWorkNo());
		pstmt.setString(2,dto.getProTitle());
		pstmt.setString(3,dto.getProStart());
		pstmt.setString(4,dto.getProEnd());
		pstmt.setString(5,dto.getProRate());
		pstmt.setString(6,dto.getProject());
		pstmt.setString(7,dto.getWorkerNo());
		
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
		
		 if (e.getErrorCode() == 1400) { // NOT NULL
			System.out.println("필수 입력 사항을 입력하지 않았습니다. ");
		} else if(e.getErrorCode() == 1840 || e.getErrorCode() == 1861) { // 날짜 입력 오류
			System.out.println("날짜 형식 오류입니다. ");
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

@Override //업무이력리스트 
public List<EmployeeDTO> listWork(String id) {
		List<EmployeeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
		
			sql = "SELECT workerNo,workNo,e.id,name,PROTITLE,prostart,proend,proRate"
					+ " From Worker w "
					+  " JOIN employee e ON w.id = e.id "
					+ " WHERE w.id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
			    EmployeeDTO dto = new EmployeeDTO();
				
				dto.setWorkerNo(rs.getString("workerNo"));
				dto.setWorkNo(rs.getString("workNo"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setProTitle(rs.getString("protitle"));
				dto.setProStart(rs.getString("proStart"));
				dto.setProEnd(rs.getString("proEnd"));
				dto.setProRate(rs.getString("proRate"));
				
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;

}
public List<EmployeeDTO> listWork() {
	List<EmployeeDTO> list = new ArrayList<>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql;
	
	try {
	
		sql = "SELECT workerNo,id,protitle,prostart,proend,project"
				+ " From Worker ";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			
		    EmployeeDTO dto = new EmployeeDTO();
			
			dto.setWorkerNo(rs.getString("workerNo"));
			dto.setId(rs.getString("id"));
			dto.setProTitle(rs.getString("protitle"));
			dto.setProStart(rs.getString("proStart"));
			dto.setProEnd(rs.getString("proEnd"));
			dto.setProject(rs.getString("project"));
			
			list.add(dto);
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e2) {
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e2) {
			}
		}
	}

	return list;

}
}
