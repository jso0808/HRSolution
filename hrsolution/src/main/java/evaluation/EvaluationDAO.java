package evaluation;

import java.sql.SQLException;
import java.util.List;

public interface EvaluationDAO {
	public EvaluationDTO readGrade(int year, String id);
	public int insertGrade(EvaluationDTO dto) throws SQLException;
	public int updateGrade(EvaluationDTO dto) throws SQLException;
	public int DeleteGrade(int year, String id) throws SQLException;
	public List<EvaluationDTO> GradeList(String id);
	
}


