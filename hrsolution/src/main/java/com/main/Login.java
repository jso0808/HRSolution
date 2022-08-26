package com.main;

import employee.EmployeeDTO;

public class Login {
	private EmployeeDTO login = null;
	
	public EmployeeDTO login() {
		return login;
		
	}
	
	public void login(EmployeeDTO login) {
		this.login = login;
	}
	
	public void logout() {
		login = null;
	}

}
