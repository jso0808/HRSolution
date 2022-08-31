package evaluation;

public interface EvaluationDAO {
	public EvaluationDTO readGrade(int year, String id);
	public EvaluationDTO insertGrade(EvaluationDTO dto);
	public EvaluationDTO updateGrade(EvaluationDTO dto);
	public EvaluationDTO DeleteGrade(int year, String id);
	
}


