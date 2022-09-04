package employee;

import java.util.List;

// 사원 관리 DTO
public class EmployeeDTO {
	private String id;
	private String name;
	private String pwd;
	private String rrn;
	private String email;
	private String tel;
	private String ft;
	private String hireDate;
	private String leaveDate;
	private String noWorking;
	private String pos;
	private String dept;
    private String workNo;
    private String workerNo;
    private String paReason;
    
    public String getWorkerNo() {
		return workerNo;
	}
	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
	}
	private String proTitle;
    private String proStart;
    private String proEnd;
    private String proRate;
    private String project;
	

	public String getWorkNo() {
		return workNo;
	}
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	public String getProTitle() {
		return proTitle;
	}
	public void setProTitle(String proTitle) {
		this.proTitle = proTitle;
	}
	public String getProStart() {
		return proStart;
	}
	public void setProStart(String proStart) {
		this.proStart = proStart;
	}
	public String getProEnd() {
		return proEnd;
	}
	public void setProEnd(String proEnd) {
		this.proEnd = proEnd;
	}
	public String getProRate() {
		return proRate;
	}
	public void setProRate(String proRate) {
		this.proRate = proRate;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getFt() {
		return ft;
	}
	public void setFt(String ft) {
		this.ft = ft;
	}
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getNoWorking() {
		return noWorking;
	}
	public void setNoWorking(String noWorking) {
		this.noWorking = noWorking;
	}
	public List<EmployeeDTO> listWork() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getPaReason() {
		return paReason;
	}
	public void setPaReason(String paReason) {
		this.paReason = paReason;
	}
	
		
	


	
}
	
