package management;

import java.util.List;

public interface ManagementDAO {
	public List<ManagementDTO> totEmpList();
	public List<ManagementDTO> deptEmpList();
	public List<ManagementDTO> rankEmpList();
	public List<ManagementDTO> recList();
	public List<ManagementDTO> organ();
	public List<ManagementDTO> totWorking(String id);
	public List<ManagementDTO> nowWorking();
	
}