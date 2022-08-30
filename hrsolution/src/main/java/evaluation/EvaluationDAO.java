package evaluation;

import java.util.List;

public interface EvaluationDAO {
	public EvaluationDTO readGrade(int year ,String id);
	public EvaluationDTO insertGrade(int year,String id);
	public EvaluationDTO updateGrade(int year,String id);
	
}


