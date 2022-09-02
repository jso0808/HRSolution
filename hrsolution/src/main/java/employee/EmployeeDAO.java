package employee;

import java.sql.SQLException;
import java.util.List;


public interface EmployeeDAO {
	
	public int insertEmployee(EmployeeDTO dto) throws SQLException;
	public int updateEmployee(EmployeeDTO dto) throws SQLException;
	public int deleteEmployee(String id) throws SQLException;
	public int insertWorker(EmployeeDTO dto) throws SQLException;
	public int updateWorker(EmployeeDTO dto) throws SQLException;
	public EmployeeDTO readEmployee(String id);
	public EmployeeDTO readMember(String id);
	public List<EmployeeDTO> listWork();
}




	