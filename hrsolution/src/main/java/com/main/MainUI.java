package com.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.util.DBConn;
import com.main.LoginDTO; 
import attendance.AttendanceUI;
import employee.EmployeeDAO;
import employee.EmployeeDAOImpl;
import employee.EmployeeUI;
import evaluation.EvaluationUI;
import management.ManagementUI;

import recruit.RecruitDAO;
import recruit.RecruitDAOImpl;
import recruit.RecruitDTO;
import recruit.RecruitUI;
import salary.SalaryUI;
// 시작메뉴, 메인메뉴 UI
// UI 꾸미기 필요
public class MainUI {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private LoginProcess loginprocess = new LoginProcess();
	
	private EmployeeUI employeeUI = new EmployeeUI();
	private AttendanceUI attendanceUI = new AttendanceUI();
	private EvaluationUI evaluationUI = new EvaluationUI();
	private ManagementUI managementUI = new ManagementUI();
	private RecruitUI recruitUI = new RecruitUI();
	private SalaryUI salaryUI = new SalaryUI();
	private RecruitDAO dao=new RecruitDAOImpl();
	private LoginDTO loginEmp = null;

	//private LoginDTO loginEmp = new LoginDTO();
	
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
				/*
				System.out.print("1.로그인 2.종료 => ");
				ch = Integer.parseInt(br.readLine());	
				*/
				  
				System.out.print("1.로그인 2.입사 지원하기 3.종료 => ");
				ch = Integer.parseInt(br.readLine());
				//2 게스트 UI만들어서 해야할듯 	
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while(ch<1 || ch>3);
		
		if(ch==3) {
			DBConn.close();
			System.exit(0);
		}
		
		switch(ch) {
		case 1: login();  break;
		
		case 2: applicant();  break;
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

			LoginDTO logindto = loginprocess.readEmp(id,pwd);

			if (logindto == null) {
				System.out.println("사번 또는 패스워드가 틀립니다. ");
				return;
			}
			
			loginEmp = logindto;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void mainmenu() {
		int ch;
		
		try {
			System.out.println("\n사용자: ["+loginEmp.getId()+"] 님");
			do {
				System.out.println("1.사원관리 2.급여관리 3.채용공고 4.종합정보 5.평가관리 6.근태관리 7.로그아웃");
				ch = Integer.parseInt(br.readLine());
			} while(ch<1 || ch>7);
			
			switch(ch) {
			case 1: 
				//employeeUI.employeemenu(loginEmp);
				break;
			case 2: 
				salaryUI.salarymenu(loginEmp);
				break;
			case 3: 
				recruitUI.recruitmenu(loginEmp);
				break;
			case 4: 
				managementUI.managementmenu(loginEmp);
				break;
			case 5: 
				evaluationUI.evaluationmenu(loginEmp);
				break;
			case 6: 
				//attendanceUI.attendancemenu(loginEmp);
				break;
			case 7: 
				loginEmp = null;
				System.out.println(); 
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public void applicant() {
		System.out.println("입사 지원 등록");
		
		try {
			RecruitDTO dto=new RecruitDTO();
			//System.out.println("접수번호");
			//dto.setApNo(br.readLine());
			
			System.out.println("이름");
			dto.setApName(br.readLine());
			
			System.out.println("생년월일");
			dto.setApBirth(br.readLine());
			
			System.out.println("접수일자");
			dto.setApDate(br.readLine());
			
			System.out.println("지원경로");
			dto.setApRoute(br.readLine());
			
			System.out.println("채용공고번호");
			dto.setPosNo(br.readLine());
			
			System.out.println("전화번호");
			dto.setApTel(br.readLine());
			
			dao.insertApplicant(dto);
					
			System.out.println("접수가 완료되었습니다");
		} catch (Exception e) {
			System.out.println("접수가 실패했습니다");
		}
		System.out.println();
	}
}
