package recruit;

import java.sql.SQLException;
import java.util.List;


public interface RecruitDAO {

public int inserRecruit(RecruitDTO dto) throws SQLException;	
public int updateRecruit(RecruitDTO dto)throws SQLException;
public int deleteRecruit(String posNo)throws SQLException;
public int insertApplicant(RecruitDTO dto) throws SQLException;


//public  RecruitDTO readRecruit(String posTitle);
public List<RecruitDTO> listRecruit();
public List<RecruitDTO> listRecruit(String apName);
public List<RecruitDTO> listRecruitTitle(String posTitle);

}
