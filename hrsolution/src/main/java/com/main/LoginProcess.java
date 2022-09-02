package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.util.DBConn;

public class LoginProcess {
	private Connection conn = DBConn.getConnection();
	
	public LoginDTO readEmp(String id, String pwd) throws SQLIntegrityConstraintViolationException {
		LoginDTO empdto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql, sql2;
		
		try {
			
			sql = "SELECT id, pwd FROM employee WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				empdto = new LoginDTO();

				empdto.setId(rs.getString("id"));
				empdto.setPwd(rs.getString("pwd"));
			}
			
			if( ! (pwd.equals(rs.getString("pwd"))) ) {
				System.out.println("패스워드가 일치하지 않습니다. ");
				return null;
			}
			rs.close();
			pstmt.close();
			
			
			sql2 = "SELECT e.id, pwd, h.deptNo, h.positionNo, position"
					+ " FROM employee e"
					+ " JOIN employee_history h ON e.id=h.id"
					+ " JOIN position p ON p.positionNo = h.positionNo"
					+ " JOIN department d ON d.deptNum=h.deptNo"
					+ " WHERE e.id=? "
					+ " ORDER BY h.positionNo DESC";
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
	
			
			if (rs.next()) {
				empdto = new LoginDTO();

				empdto.setId(rs.getString("id"));
				empdto.setPwd(rs.getString("pwd"));
				empdto.setDeptno(rs.getString("Deptno"));
				empdto.setPositionno(rs.getString("positionno"));
			}
			
			rs.close();
			pstmt.close();
			

		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			
			// 기본키 제약 위반, NOT NULL 등의 제약 위반 - 무결성 제약 위반시 발생
			if(e.getErrorCode() == 1) { // ORA-00001, 기본키 중복
				System.out.println("이미 지급된 급여입니다. ");
			} else if(e.getErrorCode() == 1400) { // NOT NULL
				System.out.println("필수 입력 사항을 입력하지 않았습니다. ");
			} else {
				System.out.println(e.toString());
			}
			
			throw e;
				
		}  catch (SQLException e) {
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

}
