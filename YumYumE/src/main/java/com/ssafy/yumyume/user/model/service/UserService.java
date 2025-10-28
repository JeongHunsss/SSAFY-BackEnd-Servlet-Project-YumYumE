package com.ssafy.yumyume.user.model.service;

import java.sql.SQLException;

import com.ssafy.yumyume.user.model.dto.UserDto;

public interface UserService {

	// 회원 가입
	void registUser(UserDto user) throws SQLException;
	
	// 아이디 중복 체크 - 회원 가입
	boolean idDuplicate(String id) throws SQLException;
	
	// 로그인
	UserDto login(String id, String password) throws SQLException;
	
	// 로그 아웃

	
	// 회원 정보 확인
	UserDto getUserInfobyId(String id) throws SQLException;
	
	// 회원 정보 수정
	void updateUser(UserDto user) throws SQLException;
	
	// 회원 탈퇴
	void deleteUser(String id) throws SQLException;
}
