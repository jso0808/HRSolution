package com.main;

import java.util.regex.*;

// 입력 데이터의 유효성 검사
public class ValidCheck {

	// 숫자 검사
	public boolean isNumber(String str) {
		return Pattern.matches("^[0-9]*$", str);
	}

	// 년도 검사
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
