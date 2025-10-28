package com.ssafy.yumyume.food.model.dto;

public class ReportFoodDto {
	private int reportId;      // 레포트 id
	private String foodCode;   // 음식 코드
    private double foodWeight; // 먹은 중량
    private String foodName; // 음식 이름

    public ReportFoodDto() {}
    
    public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getFoodCode() {
		return foodCode;
	}

	public void setFoodCode(String foodCode) {
		this.foodCode = foodCode;
	}

	public double getFoodWeight() {
		return foodWeight;
	}

	public void setFoodWeight(double foodWeight) {
		this.foodWeight = foodWeight;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	
}
