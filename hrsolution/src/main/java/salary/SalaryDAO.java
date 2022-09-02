package salary;

import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO {
	public void insertSal(SalaryDTO sdto) throws SQLException; // 연봉 협상
	public void insertPay(PayDTO pdto) throws SQLException; // 급여 지급
	
	public void updateSalary(SalaryDTO sdto) throws SQLException; // 연봉 수정
	public void updatePay(PayDTO pdto) throws SQLException; // 급여 수정
	
	public SalaryDTO listSalaryNowEmp(String id); // 특정 사원 현재 연봉 정보
	public List<SalaryDTO> listSalaryHisEmp(String id); // 특정 사원의 역대 연봉 리스트
	public List<SalaryDTO> listSalaryAll(); // 전체 사원 연봉 리스트
	public List<SalaryDTO> listSalaryDept(); // 부서별 연봉 리스트
	public List<SalaryDTO> listSalaryPos(); // 직급별 연봉리스트
	public List<PayDTO> listPayEmp(String id); // 특정 사원 급여 리스트
	public List<PayDTO> listPayMonth(String month); // 월별 전체 사원 급여 리스트
	
}
