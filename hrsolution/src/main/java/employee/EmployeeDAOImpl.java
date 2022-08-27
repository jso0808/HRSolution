package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class EmployeeDAOImpl implements EmployeeDAO{
	private Connection conn = DBConn.getConnection();

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
	

}
