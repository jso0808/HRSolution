package com.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import employee.EmployeeDAO;
import employee.EmployeeDAOImpl;
import employee.EmployeeDTO;

public class LoginProcess {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private EmployeeDAO empdao = new EmployeeDAOImpl();
	private Login login = null;
	
	public LoginProcess(Login login) {
		this.login = login;
	}
	
	public void loginmenu() {
		System.out.println("[로그인]");
		
		String id, pwd;
		
		try {
			System.out.println("사번 ? ");
			id = br.readLine();
			
			System.out.println("패스워드 ? ");
			pwd = br.readLine();
			
			EmployeeDTO empdto = empdao.readMember(id);
			
			if(empdto==null) {
				System.out.println("사번이 존재하지 않습니다. ");
				return;
			} else if(! empdto.getPwd().equals(pwd)) {
				System.out.println("패스워드가 일치하지 않습니다. ");
				return;
			}
			
			login.login(empdto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
}
