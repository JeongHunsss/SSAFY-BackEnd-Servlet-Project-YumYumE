package com.ssafy.yumyume.user.model.dto;

import java.time.LocalDateTime;

public class UserDto {

	private String id; // 사용자 아이디
	private String name; // 사용자 이름
	private String email; // 사용자 이메일
	private String emailDomain;// 사용자 이메일 도메인
	private String password; // 사용자 비밀번호
	private double weight; // 사용자 몸무게
	private double height; // 사용자 키
	private String gender; // 사용자 성별
	private String role; // 사용자 권한?
	private int age; // 사용자 나이
	private String activityLevel; // 사용자 활동계수
	private double stdWeight; // 표준 몸무게
	private double stdKcal; // 표준 섭취 칼로리
	private double stdCarb; // 표준 섭취 탄수화물
	private double stdProtein; // 표준 섭취 단백질
	private double stdFat; // 표준 섭취 지방
	private double stdSugar; // 표준 섭취 당류
	private double stdNatrium; // 표준 섭취 나트륨
	private LocalDateTime created_at; // 등록 날짜

	public UserDto() {
	}

	public UserDto(String id, String name, String email, String emailDomain, String password, double weight,
			double height, String gender, String role, int age, String activityLevel, double stdWeight, double stdKcal,
			double stdCarb, double stdProtein, double stdFat, double stdSugar, double stdNatrium,
			LocalDateTime created_at) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.emailDomain = emailDomain;
		this.password = password;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
		this.role = role;
		this.age = age;
		this.activityLevel = activityLevel;
		this.stdWeight = stdWeight;
		this.stdKcal = stdKcal;
		this.stdCarb = stdCarb;
		this.stdProtein = stdProtein;
		this.stdFat = stdFat;
		this.stdSugar = stdSugar;
		this.stdNatrium = stdNatrium;
		this.created_at = created_at;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

	public double getStdWeight() {
		return stdWeight;
	}

	public void setStdWeight(double stdWeight) {
		this.stdWeight = stdWeight;
	}

	public double getStdKcal() {
		return stdKcal;
	}

	public void setStdKcal(double stdKcal) {
		this.stdKcal = stdKcal;
	}

	public double getStdCarb() {
		return stdCarb;
	}

	public void setStdCarb(double stdCarb) {
		this.stdCarb = stdCarb;
	}

	public double getStdProtein() {
		return stdProtein;
	}

	public void setStdProtein(double stdProtein) {
		this.stdProtein = stdProtein;
	}

	public double getStdFat() {
		return stdFat;
	}

	public void setStdFat(double stdFat) {
		this.stdFat = stdFat;
	}

	public double getStdSugar() {
		return stdSugar;
	}

	public void setStdSugar(double stdSugar) {
		this.stdSugar = stdSugar;
	}

	public double getStdNatrium() {
		return stdNatrium;
	}

	public void setStdNatrium(double stdNatrium) {
		this.stdNatrium = stdNatrium;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", emailDomain=" + emailDomain
				+ ", password=" + password + ", weight=" + weight + ", height=" + height + ", gender=" + gender
				+ ", role=" + role + ", age=" + age + ", activityLevel=" + activityLevel + ", stdWeight=" + stdWeight
				+ ", stdKcal=" + stdKcal + ", stdCarb=" + stdCarb + ", stdProtein=" + stdProtein + ", stdFat=" + stdFat
				+ ", stdSugar=" + stdSugar + ", stdNatrium=" + stdNatrium + ", created_at=" + created_at + "]";
	}

}
