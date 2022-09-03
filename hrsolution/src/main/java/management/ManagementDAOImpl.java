package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
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
			sql = "SELECT dept,em.id, name,tel,email, ft" + "	FROM Employee em "
					+ "	JOIN employee_history eh ON eh.id = em.id " + "	JOIN department dp ON dp.deptnum = eh.deptno "
					+ "	JOIN POSITION pos ON pos.positionno = eh.positionno "
					+ "	WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id ,MAX(pano) FROM employee_history GROUP BY employee_history.id)"
					+ " ORDER BY dept";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ManagementDTO dto = new ManagementDTO();

				dto.setDept(rs.getString("dept"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setFt(rs.getString("ft"));

				list.add(dto);
			}
			pstmt.close();
		} catch (Exception e) {
			System.out.println("전체사원리스트 에러");
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
	public List<ManagementDTO> deptEmpList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT dept, em.id, name,tel,email, ft, position " + " FROM Employee em "
					+ " JOIN employee_history eh ON eh.id = em.id " + " JOIN department dp ON dp.deptnum = eh.deptno "
					+ " JOIN POSITION pos ON pos.positionno = eh.positionno "
					+ " WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id ,MAX(pano) FROM employee_history GROUP BY employee_history.id)"
					+ "ORDER BY dept";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
			pstmt.close();

		} catch (Exception e) {
			System.out.println("부서별 사원리스트 에러");
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
	public List<ManagementDTO> rankEmpList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT position, em.id, name,dept,tel, email,ft " + " FROM Employee em "
					+ " JOIN employee_history eh ON eh.id = em.id" + " JOIN department dp ON dp.deptnum = eh.deptno"
					+ " JOIN POSITION pos ON pos.positionno = eh.positionno"
					+ " WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id , MAX(pano) FROM employee_history GROUP BY employee_history.id)"
					+ " ORDER BY pos.positionno desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
			pstmt.close();
		} catch (Exception e) {
			System.out.println("직급별 사원리스트 에러");
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
	public List<ManagementDTO> recList() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT position, dept, em.id, name, tel,email, ft " + " FROM Employee em "
					+ " JOIN employee_history eh ON eh.id = em.id " + " JOIN recruiter rc ON rc.idre = em.id "
					+ " JOIN department dp ON dp.deptnum = eh.deptno "
					+ " JOIN POSITION pos ON pos.positionno = eh.positionno "
					+ " WHERE noWorking ='재직중' AND (eh.id ,pano) IN  (SELECT em.id ,MAX(pano) FROM employee_history GROUP BY employee_history.id) "
					+ " ORDER BY pos.positionno desc ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
			pstmt.close();

		} catch (Exception e) {
			System.out.println("채용담당자 리스트 에러");
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
	public List<ManagementDTO> totWorking(String id) {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = " SELECT em.id, name, position, w.proStart, NVL(TO_CHAR(w.proEnd,'YY/MM/DD'),'진행중') proend, w.protitle, w.prorate, w.workerno, NVL(w.project,'프로젝트정보없음') project "
					+ " FROM Employee em " + " JOIN employee_history eh ON eh.id = em.id "
					+ " JOIN department dp ON dp.deptnum = eh.deptno "
					+ " JOIN position pos ON pos.positionno = eh.positionno " + " JOIN worker w ON w.id = em.id "
					+ " WHERE eh.id = ? AND (eh.id ,pano) IN  (SELECT id , MAX(pano) FROM employee_history GROUP BY employee_history.id)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ManagementDTO dto = new ManagementDTO();

				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPos(rs.getString("position"));
				dto.setProStart(rs.getString("proStart"));
				dto.setProEnd(rs.getString("proend"));
				dto.setProTitle(rs.getString("protitle"));
				dto.setProRate(rs.getString("prorate"));
				dto.setWorkNo(rs.getString("workerno"));
				dto.setProject(rs.getString("project"));

				list.add(dto);
			}
			pstmt.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode() == 1) 
				System.out.println("기본키 중복 오류입니다. ");
			if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861) // 날짜 입력 형식 오류
				System.out.println("날짜 입력 형식 오류입니다. ");
			// 기본키 제약 위반, NOT NULL 등의 제약 위반 - 무결성 제약 위반시 발생
			if (e.getErrorCode() == 1400) {
				System.out.println("필수 입력 사항을 입력하지 않았습니다. ");
			} else {
				System.out.println(e.toString());
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력가능");
		} catch (Exception e) {
			System.out.println("평가입력 에러");
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<ManagementDTO> nowWorking() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT em.id, name, position, w.proStart,w.protitle,NVL( w.prorate,0) prorate, NVL(w.workerno,0) workerno,  NVL(w.project,'프로젝트정보없음') project "
					+ "		FROM Employee em " 
					+ "		JOIN employee_history eh ON eh.id = em.id "
					+ "		JOIN department dp ON dp.deptnum = eh.deptno "
					+ "		JOIN position pos ON pos.positionno = eh.positionno "
					+ "		JOIN worker w ON w.id = em.id " + "		WHERE w.proRate = '진행중' AND em.noWorking = '재직중' "
					+ "		AND (eh.id ,pano) IN  (SELECT id , MAX(pano)  FROM employee_history GROUP BY employee_history.id) ";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ManagementDTO dto = new ManagementDTO();

				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPos(rs.getString("position"));
				dto.setProStart(rs.getString("proStart"));
				dto.setProTitle(rs.getString("protitle"));
				dto.setProRate(rs.getString("prorate"));
				dto.setProject(rs.getString("project"));
				dto.setWorkNo(rs.getString("workerno"));

				list.add(dto);
			}
			pstmt.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode() == 1) 
				System.out.println("기본키 중복 오류입니다. ");
			if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861) // 날짜 입력 형식 오류
				System.out.println("날짜 입력 형식 오류입니다. ");
			// 기본키 제약 위반, NOT NULL 등의 제약 위반 - 무결성 제약 위반시 발생
			if (e.getErrorCode() == 1400) {
				System.out.println("필수 입력 사항을 입력하지 않았습니다. ");
			} else {
				System.out.println(e.toString());
			} 
		}catch (Exception e) {
			System.out.println("현재 프로젝트 담당리스트 에러");
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
	public List<ManagementDTO> organ() {
		List<ManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT num, LPAD('  ', (LEVEL-1)*4) || dept dept " + "    FROM Department "
					+ "    START WITH num =1 " + "    CONNECT BY PRIOR num = deptparent ";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ManagementDTO dto = new ManagementDTO();

				dto.setDept(rs.getString("dept"));

				list.add(dto);
			}
			pstmt.close();
		} catch (Exception e) {
			System.out.println("조직도 리스트 에러");
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
}
