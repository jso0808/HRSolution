package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class LoginProcess {
	private Connection conn = DBConn.getConnection();
	
	public LoginDTO readEmp(String id, String pwd) {
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
			System.out.println(empdto.getPositionno());
			System.out.println(empdto.getDeptno());
			
			rs.close();
			pstmt.close();

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

}
