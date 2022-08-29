package com.main;

import java.util.ArrayList;
import java.util.List;

public class LoginDTO {
	private String id;
	private String pwd;
	private String deptno;
	private String positionno;

	private List<LoginDTO> emphisList = new ArrayList<>(); // 사원 이력 리스트

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getPositionno() {
		return positionno;
	}

	public void setPositionno(String positionno) {
		this.positionno = positionno;
	}

	public List<LoginDTO> getEmphisList() {
		return emphisList;
	}

	public void setEmphisList(List<LoginDTO> emphisList) {
		this.emphisList = emphisList;
	}

	// 사원 이력 리스트
	public List<LoginDTO> emphisList() {
		return emphisList;
	}

	// 마지막 사원이력 내역
	public LoginDTO lastemphisList() {
		if (emphisList.size() > 0) {
			return emphisList.get(emphisList.size() - 1);
		}
		return null;
	}

}
