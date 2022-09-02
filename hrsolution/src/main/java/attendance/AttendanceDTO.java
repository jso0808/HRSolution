package attendance;

public class AttendanceDTO {
	private String id;
	private String attNo;
	private String CIN;
	private String COUT;
	private String Name;
	private String MEMO;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttNo() {
		return attNo;
	}
	public void setAttNo(String attNo) {
		this.attNo = attNo;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getCOUT() {
		return COUT;
	}
	public void setCOUT(String cOUT) {
		COUT = cOUT;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	
	
}