package management;

public class ManagementDTO {
	private String id;
	private String recruit;
	private String dept;
	private String rank;
	private String name;
	private String tel;
	private String pos;
	private String email;
	private String ft;
	private String workNo;
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
	//전체사원리스트 : id, name,tel, email, ft
	//부서별 사원리스트 : dept, id, name, pos,tel,email, ft
	//직급별 사원리스트 : pos, id, name, dept tel, email, ft
	//
	public String getRecruit() {
		return recruit;
	}
	public void setRecruit(String recruit) {
		this.recruit = recruit;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFt() {
		return ft;
	}
	public void setFt(String ft) {
		this.ft = ft;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
}
