package com.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.util.DBConn;

// 시작메뉴, 메인메뉴 UI
// UI 꾸미기 필요
public class MainUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private Login login = new Login();
	private LoginProcess loginprocess = new LoginProcess(login);

	public void menu() {
		while(true) {
			if(login.login() == null) {
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
		case 1: loginprocess.loginmenu(); break;
		}
	}
	
	public void mainmenu() {
		int ch;
		
		try {
			System.out.println("\n[사용자: "+login.login().getId()+"] 님");
			do {
				System.out.println("1.사원관리 2.급여관리 3.채용공고 4.종합정보 5.평가관리 6.근태관리 7.로그아웃");
				ch = Integer.parseInt(br.readLine());
			} while(ch<1 || ch>7);
			
			switch(ch) {
			case 1: break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
			case 7: 
				login.logout(); 
				System.out.println(); 
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
}
