package com.ssafy.yumyume.food.model.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.yumyume.food.model.dto.ReportDto;
import com.ssafy.yumyume.food.model.dto.ReportFoodDto;

public interface ReportDao {
	List<ReportDto> searchByTime(LocalDateTime start, LocalDateTime end, int offset, int limit) throws SQLException; // start~end 기간 내 검색
	
	List<ReportDto> getByUserId(String userId, int offset, int limit) throws SQLException; // 유저 id로 가져오기
	
	ReportDto getDtoByReportId(int reportId) throws SQLException; // report id로 가져오기
	
	int createReport(ReportDto dto, List<ReportFoodDto> foodList, double mealRatio) throws SQLException; // 등록
	
	List<ReportFoodDto> getReportFoodDtosByReportId(int reportId) throws SQLException; // report id로 담은 음식들 가져오기
	
	ReportDto updateReport(ReportDto dto) throws SQLException; // 수정
	
	int deleteReport(int reportId) throws SQLException; // 삭제
}
