package com.ssafy.yumyume.food.model.dto;

import java.time.LocalDateTime;

import com.ssafy.yumyume.user.model.dto.UserDto;

public class ReportDto {
	private int id; // 결과지 id
	private String mealType; // 끼니 타입 (BREAKFAST, LUNCH, DINNER, SNACK)
	private double score; // 결과 점수
	private String aiComment; // AI 한마디
	private double kcalSum; // 칼로리 합산
	private double carbSum; // 탄수화물 합산
	private double proteinSum; // 단백질 합산
	private double fatSum; // 지방 합산
	private double sugarSum; // 당류 합산
	private double natriumSum; // 엽분 합산
	private LocalDateTime createdAt; // 생성일
	
	private UserDto userDto; // 유저 정보 (id, age, height, weight, gender, activity_level, std_*)

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getAiComment() {
		return aiComment;
	}
	public void setAiComment(String aiComment) {
		this.aiComment = aiComment;
	}
	public double getKcalSum() {
		return kcalSum;
	}
	public void setKcalSum(double kcalSum) {
		this.kcalSum = kcalSum;
	}
	public double getCarbSum() {
		return carbSum;
	}
	public void setCarbSum(double carbSum) {
		this.carbSum = carbSum;
	}
	public double getProteinSum() {
		return proteinSum;
	}
	public void setProteinSum(double proteinSum) {
		this.proteinSum = proteinSum;
	}
	public double getFatSum() {
		return fatSum;
	}
	public void setFatSum(double fatSum) {
		this.fatSum = fatSum;
	}
	public double getSugarSum() {
		return sugarSum;
	}
	public void setSugarSum(double sugarSum) {
		this.sugarSum = sugarSum;
	}
	public double getNatriumSum() {
		return natriumSum;
	}
	public void setNatriumSum(double natriumSum) {
		this.natriumSum = natriumSum;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
