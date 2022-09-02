package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;


//년월일 단위로 column 넣어야 함
//date형식이 아니라

public class EvaluationDAOImpl implements EvaluationDAO {
	private Connection conn = DBConn.getConnection();
	@Override
	public EvaluationDTO readGrade(int year, String id) {
		EvaluationDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql=" SELECT perYear, em.id, name, perGrade, perContent,perDate, perid FROM evaluation ev "
					+ "	JOIN employee em ON em.id = ev.id "
					+ " WHERE perYear = ? AND em.id = ?";
			pstmt = conn.prepareStatement(sql);	
			
			pstmt.setInt(1, year);
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new EvaluationDTO();
				
				dto.setYear(Integer.parseInt(rs.getString("perYear")));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setGrade(Integer.parseInt(rs.getString("perGrade")));
				dto.setContent(rs.getString("perContent"));
				dto.setDate(rs.getString("perDate"));
				dto.setPerid(rs.getString("perid"));
				System.out.println("진행중");
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
		return dto;
	}
	
	@Override
	public EvaluationDTO insertGrade(EvaluationDTO dto) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "INSERT INTO evaluation(perYear, id, perGrade, perContent,perDate, perid) "
					+ "VALUES (?, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getYear());
			pstmt.setString(2, dto.getId());
			pstmt.setInt(3, dto.getGrade());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getDate());
			pstmt.setString(6, dto.getPerid());
			
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			System.out.println("평가입력 에러");
			e.printStackTrace();
			
		}finally {
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
		
		return null;
		
	}

	@Override
	public EvaluationDTO updateGrade(EvaluationDTO dto) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "UPDATE evaluation SET "
					+ " perGrade = ?, perContent = ?,"
					+ " perDate =TO_DATE(?,'YYYY-MM-DD') , perid = ? "
					+ " WHERE perYear = ? AND id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getGrade());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getDate());
			pstmt.setString(4, dto.getPerid());
			pstmt.setInt(5, dto.getYear());
			pstmt.setString(6, dto.getId());
			
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			System.out.println("평가수정 에러");
			e.printStackTrace();
			
		}finally {
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
		return null;
	}

	@Override
	public EvaluationDTO DeleteGrade(int year, String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "DELETE FROM evaluation WHERE perYear = ? AND id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, year);
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			
		} catch (Exception e) {
			System.out.println("평가삭제 에러");
			e.printStackTrace();
		}finally {
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
		return null;
	}
	
	@Override
	public List<EvaluationDTO> GradeList(String  id) {
		List<EvaluationDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT  peryear, id, perGrade, percontent,perdate, perid FROM evaluation WHERE id = ? "
					+ "order by peryear desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				EvaluationDTO dto = new EvaluationDTO();
				
				dto.setYear(Integer.parseInt(rs.getString("peryear")));
				dto.setId(rs.getString("id"));
				dto.setGrade(Integer.parseInt(rs.getString("pergrade")));
				dto.setContent(rs.getString("percontent"));
				dto.setContent(rs.getString("perdate"));
				dto.setPerid(rs.getString("perid"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("평가삭제 에러");
			e.printStackTrace();
		}finally {
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
