package recruit;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


import com.util.DBConn;






public  class RecruitDAOImpl implements RecruitDAO{
	private Connection conn = DBConn.getConnection();
	@Override
	public int inserRecruit(RecruitDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO posting(posNo,posNum,pos,posStart,posEnd,posMeans,PosPro,deptNo,id,posTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPosNo());
			pstmt.setInt(2, dto.getPosNum());
			pstmt.setString(3, dto.getPos());
			pstmt.setString(4, dto.getPosStart());
			pstmt.setString(5, dto.getPosEnd());
			pstmt.setString(6, dto.getPosMeans());
			pstmt.setString(7, dto.getPosPro());
			pstmt.setString(8, dto.getDeptNo());
			pstmt.setString(9, dto.getId());
			pstmt.setString(10, dto.getPosTitle());
			
			result += pstmt.executeUpdate();
		
		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode()==1400){ 
				System.out.println("필수 입력사항을 입력하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;
		} catch (SQLDataException e) {
			
			if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
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
	public int updateRecruit(RecruitDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE  posting SET posNum=?, pos=?, posStart=?, posEnd=?, posMeans=?, PosPro=?, deptNo=?, id=?, posTitle=? WHERE posNo=?";

			pstmt = conn.prepareStatement(sql);
		
			    pstmt.setInt(1, dto.getPosNum());
			    pstmt.setString(2, dto.getPos());
			    pstmt.setString(3, dto.getPosStart());
			    pstmt.setString(4, dto.getPosEnd());
			    pstmt.setString(5, dto.getPosMeans());
			    pstmt.setString(6, dto.getPosPro());
			    pstmt.setString(7, dto.getDeptNo());
			    pstmt.setString(8, dto.getId());
			    pstmt.setString(9, dto.getPosTitle());
				pstmt.setString(10, dto.getPosNo());

			
			

			result += pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			if(e.getErrorCode()==1400){ 
				System.out.println("필수 입력사항을 입력하지 않았습니다.");
			} else {
				System.out.println(e.toString());
			}
			throw e;
		} catch (SQLDataException e) {
			
			if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
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
	public int deleteRecruit(String posNo) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM  posting WHERE posNo=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, posNo);
			

			result = pstmt.executeUpdate();

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
	public RecruitDTO readRecruit(String posNo) {
		RecruitDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT posNo,posNum,pos,posStart,posEnd,posMeans,PosPro,deptNo,id,posTitle From Posting WHERE posNo=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, posNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new RecruitDTO();

				dto.setPosNo(rs.getString("posNo"));
				dto.setPosNum(rs.getInt("posNum"));
				dto.setPos(rs.getString("pos"));
				dto.setPosStart(rs.getString("posStart"));
				dto.setPosEnd(rs.getString("posEnd"));
				dto.setPosMeans(rs.getString("posMeans"));
				dto.setPosPro(rs.getString("posPro"));
				dto.setDeptNo(rs.getString("deptNo"));
				dto.setId(rs.getString("id"));
				dto.setPosTitle(rs.getString("posTitle"));
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

		return dto;
	
	}

	@Override
	public List<RecruitDTO> listRecruit() {
		List<RecruitDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT posNo,posNum,pos,posStart,posEnd,posMeans,PosPro,deptNo,id,posTitle From Posting";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecruitDTO dto = new RecruitDTO();

				dto.setPosNo(rs.getString("posNo"));
				dto.setPosNum(rs.getInt("posNum"));
				dto.setPos(rs.getString("pos"));
				dto.setPosStart(rs.getString("posStart"));
				dto.setPosEnd(rs.getString("posEnd"));
				dto.setPosMeans(rs.getString("posMeans"));
				dto.setPosPro(rs.getString("posPro"));
				dto.setDeptNo(rs.getString("deptNo"));
				dto.setId(rs.getString("id"));
				dto.setPosTitle(rs.getString("posTitle"));
				list.add(dto);
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

		return list;
	}

	@Override
	public List<RecruitDTO> listRecruit(String id) {
		List<RecruitDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT posNo,posNum,pos,posStart,posEnd,posMeans,PosPro,deptNo,id,posTitle From Posting where posting.id=? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecruitDTO dto = new RecruitDTO();

				dto.setPosNo(rs.getString("posNo"));
				dto.setPosNum(rs.getInt("posNum"));
				dto.setPos(rs.getString("pos"));
				dto.setPosStart(rs.getString("posStart"));
				dto.setPosEnd(rs.getString("posEnd"));
				dto.setPosMeans(rs.getString("posMeans"));
				dto.setPosPro(rs.getString("posPro"));
				dto.setDeptNo(rs.getString("deptNo"));
				dto.setId(rs.getString("id"));
				dto.setPosTitle(rs.getString("posTitle"));

				list.add(dto);
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

		return list;
	}
	
	

	

}