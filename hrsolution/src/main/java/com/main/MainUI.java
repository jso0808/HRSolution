package com.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.util.DBConn;

import employee.EmployeeDAO;
import employee.EmployeeDAOImpl;
import employee.EmployeeDTO;
import employee.EmployeeUI;

// 시작메뉴, 메인메뉴 UI
// UI 꾸미기 필요
public class MainUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	// private Login login = new Login();
	// private LoginProcess loginprocess = new LoginProcess(login);
	private EmployeeDAO empdao = new EmployeeDAOImpl();
	private EmployeeUI employeemenu = new EmployeeUI();
	// private EmployeeDAO empDAO = new EmployeeUI();
	private EmployeeDTO loginEmp = null;
	
	

	public void menu() {
		while(true) {
			if(loginEmp == null) {
				startmenu();
			} else {
				mainmenu();
			}
		}
	}
	
	
	public void startmenu() {
		System.out.println("시작 메뉴");
		
		int ch;
		
		do {
			ch = 0;
			try {
				System.out.print("1.로그인 2.종료 => ");
				ch = Integer.parseInt(br.readLine());	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while(ch<1 || ch>2);
		
		if(ch==2) {
			DBConn.close();
			System.exit(0);
		}
		
		switch(ch) {
		case 1: login();  break;
		}
	}
	
	public void login() {
		System.out.println("로그인 메뉴 실행");
		String id, pwd;

		try {
			System.out.println("사번 ? ");
			id = br.readLine();

			System.out.println("패스워드 ? ");
			pwd = br.readLine();

			EmployeeDTO empdto = empdao.readMember(id);

			if (empdto == null) {
				System.out.println("사번이 존재하지 않습니다. ");
				return;
			} else if (!empdto.getPwd().equals(pwd)) {
				System.out.println("패스워드가 일치하지 않습니다. ");
				return;
			}
			
			loginEmp = empdto;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void mainmenu() {
		int ch;
		
		try {
			System.out.println("\n[사용자: "+loginEmp.getId()+"] 님");
			do {
				System.out.println("1.사원관리 2.급여관리 3.채용공고 4.종합정보 5.평가관리 6.근태관리 7.로그아웃");
				ch = Integer.parseInt(br.readLine());
			} while(ch<1 || ch>7);
			
			switch(ch) {
			case 1: 
				employeemenu.employeemenu(loginEmp);
				break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
			case 7: 
				loginEmp = null;
				System.out.println(); 
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
}
