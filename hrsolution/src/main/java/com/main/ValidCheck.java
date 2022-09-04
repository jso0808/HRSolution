package com.main;

import java.util.regex.*;

// 입력 데이터의 유효성 검사
public class ValidCheck {
	//부서코드 검사
	public boolean isDept(String str) {
		return Pattern.matches("[1-9]00", str);
	}
	//직급코드 검사
	public boolean isPos(String str) {
		return Pattern.matches("[1-8]", str);
	}
	//고용형태 검사
	public boolean isFt(String str) {
		return Pattern.matches("정규직|계약직", str);
	}
	//날짜형태 검사 ? 어떤형식임?
	public boolean isDate(String str) {
		return Pattern.matches("[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-3]):([0-5][0-9]) ", str);
	}
	
	//근무형태 검사
	public boolean isWorking(String str) {
		return Pattern.matches("재직중|퇴사", str);
	}
	//업무진행 검사
	public boolean isRate(String str) {
		return Pattern.matches("진행중/완료", str);
	}
	// 숫자 검사
	public boolean isNumber(String str) {
		return Pattern.matches("^[0-9]*$", str);
	}
	//연도 검사
	public boolean isYear(String str) {
		return Pattern.matches("^[0-9]{4}$", str);
	}

	// 한글 검사
	public boolean isKorean(String str) {
		return Pattern.matches("^[가-힣]*$", str);
	}

	// 이메일 검사
	public boolean isEmail(String str) {
		return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", str);
	}

	/*
	 * // 주민번호 검사 public boolean isPersonalID(String str) { return
	 * Pattern.matches("\\d{6} \\- [1-4]\\d{6}", str); }
	 */
	public boolean isPersonalID(String str) {
		return Pattern.matches("\\d{2}([0]\\d|[1][0-2])([0][1-9]|[1-2]\\d|[3][0-1])[-]*[1-4]\\d{6}", str);
	}

	// 휴대폰번호 (- 포함) 검사
	public boolean isTel(String str) {
		return Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", str);
	}

}
