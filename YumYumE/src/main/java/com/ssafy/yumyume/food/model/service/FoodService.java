package com.ssafy.yumyume.food.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.yumyume.food.model.dto.FoodDto;

public interface FoodService {
	
	// 조건 검색
	List<FoodDto> search(String code, String name, String category, int pageNum, int limit) throws SQLException;
	List<FoodDto> search(String code, String name, String category) throws SQLException;
	
	// code로 가져오기
	FoodDto getDtoByCode(String code) throws SQLException;
	
	FoodDto updateFood(FoodDto dto) throws SQLException; // 수정
	
	int deleteFood(String code) throws SQLException; // 삭제
	
	int insertFood(FoodDto dto) throws SQLException; // 음식 등록
	
	// 카테고리 가져오기
	List<String> getCategoryList() throws SQLException;
}
