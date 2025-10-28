package com.ssafy.yumyume.food.model.dto;

public class FoodDto {
	private String code; // 음식 식별 코드
	private String name; // 음식 이름
	private String category; // 음식 종류
	private String standard; // 영양소 기준 중량
	private double kcal; // 칼로리
	private double protein; // 단백질
	private double fat; // 지방
	private double carb; // 탄수화물
	private double sugar; // 당
	private double natrium; // 염분
	
	public FoodDto() {}
	
	public FoodDto(String code, String name, String category, String standard, double kcal, double protein, double fat,
			double carb, double sugar, double natrium) {
		super();
		this.code = code;
		this.name = name;
		this.category = category;
		this.standard = standard;
		this.kcal = kcal;
		this.protein = protein;
		this.fat = fat;
		this.carb = carb;
		this.sugar = sugar;
		this.natrium = natrium;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public double getKcal() {
		return kcal;
	}
	public void setKcal(double kcal) {
		this.kcal = kcal;
	}
	public double getProtein() {
		return protein;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public double getCarb() {
		return carb;
	}
	public void setCarb(double carb) {
		this.carb = carb;
	}
	public double getSugar() {
		return sugar;
	}
	public void setSugar(double sugar) {
		this.sugar = sugar;
	}
	public double getNatrium() {
		return natrium;
	}
	public void setNatrium(double natrium) {
		this.natrium = natrium;
	}
	
	
}
