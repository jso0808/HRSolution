package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ManagementDAOImpl implements ManagementDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public List<ManagementDTO> totEmpList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT dept,em.id, name,tel,email, ft"
					+ "	FROM Employee em "
					+ "	JOIN employee_history eh ON eh.id = em.id "
					+ "	JOIN department dp ON dp.deptnum = eh.deptno "
					+ "	JOIN POSITION pos ON pos.positionno = eh.positionno "
					+ "	WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id ,MAX(pano) FROM employee_history GROUP BY employee_history.id)"
					+ " ORDER BY dept";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ManagementDTO dto = new ManagementDTO();
				
				dto.setDept(rs.getString("dept"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setFt(rs.getString("ft"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체사원리스트 에러");
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
	public List<ManagementDTO> deptEmpList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT dept, em.id, name,tel,email, ft, position "
					+ " FROM Employee em "
					+ " JOIN employee_history eh ON eh.id = em.id "
					+ " JOIN department dp ON dp.deptnum = eh.deptno "
					+ " JOIN POSITION pos ON pos.positionno = eh.positionno "
					+ " WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id ,MAX(pano) FROM employee_history GROUP BY employee_history.id)";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ManagementDTO dto = new ManagementDTO();
				 
				dto.setDept(rs.getString("dept"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setPos(rs.getString("position"));
				dto.setEmail(rs.getString("email"));
				dto.setFt(rs.getString("ft"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("부서별 사원리스트 에러");
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
	public List<ManagementDTO> rankEmpList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT position, em.id, name,dept,tel, email,ft "
					+ " FROM Employee em "
					+ " JOIN employee_history eh ON eh.id = em.id"
					+ " JOIN department dp ON dp.deptnum = eh.deptno"
					+ " JOIN POSITION pos ON pos.positionno = eh.positionno"
					+ " WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id , MAX(pano) FROM employee_history GROUP BY employee_history.id)"
					+ " ORDER BY pos.positionno desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ManagementDTO dto = new ManagementDTO();
				 
				dto.setPos(rs.getString("position"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setDept(rs.getString("dept"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setFt(rs.getString("ft"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("직급별 사원리스트 에러");
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
	public List<ManagementDTO> recList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT position, dept, em.id, name, tel,email, ft "
					+ " FROM Employee em "
					+ " JOIN employee_history eh ON eh.id = em.id "
					+ " JOIN recruiter rc ON rc.idre = em.id "
					+ " JOIN department dp ON dp.deptnum = eh.deptno "
					+ " JOIN POSITION pos ON pos.positionno = eh.positionno "
					+ " WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id ,MAX(pano) FROM employee_history GROUP BY employee_history.id) "
					+ " ORDER BY pos.positionno desc ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ManagementDTO dto = new ManagementDTO();
				 
				dto.setPos(rs.getString("position"));
				dto.setDept(rs.getString("dept"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setFt(rs.getString("ft"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("채용담당자 리스트 에러");
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
	public List<ManagementDTO> organ() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT num, LPAD('  ', (LEVEL-1)*4) || dept dept "
					+ "    FROM Department "
					+ "    START WITH num =1 "
					+ "    CONNECT BY PRIOR num = deptparent ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ManagementDTO dto = new ManagementDTO();
				 
				dto.setDept(rs.getString("dept"));

				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("조직도 리스트 에러");
		}finally {
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
	
}
