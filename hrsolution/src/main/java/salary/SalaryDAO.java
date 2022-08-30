package salary;

import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO {
	public void insertPay(PayDTO pdto) throws SQLException; // 급여 지급
	public void updateSalary(String id); // 연봉 수정
	public List<SalaryDTO> listSalaryAll(); // 전체 사원 연봉 리스트
	public SalaryDTO listSalaryEmp(String id); // 특정 사원 연봉 정보
	public List<PayDTO> listPayAll(); // 전체 사원 급여 리스트
	public List<PayDTO> listPayEmp(String id); // 특정 사원 급여 리스트

}
