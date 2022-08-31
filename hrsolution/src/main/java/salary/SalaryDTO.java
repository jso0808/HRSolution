package salary;

// 연봉 테이블 객체
public class SalaryDTO {
	private String name;
	private String id;
	private String dept;
	private String position; // 직급
	private String salNo; // 연봉 번호
	private String sal; // 연봉
	private String salStart;
	private String salEnd;
	private String memo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSalNo() {
		return salNo;
	}
	public void setSalNo(String salNo) {
		this.salNo = salNo;
	}
	public String getSal() {
		return sal;
	}
	public void setSal(String sal) {
		this.sal = sal;
	}
	public String getSalStart() {
		return salStart;
	}
	public void setSalStart(String salStart) {
		this.salStart = salStart;
	}
	public String getSalEnd() {
		return salEnd;
	}
	public void setSalEnd(String salEnd) {
		this.salEnd = salEnd;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	

}
