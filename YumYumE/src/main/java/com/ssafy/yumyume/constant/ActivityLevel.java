package com.ssafy.yumyume.constant;

public enum ActivityLevel {
    SEDENTARY("거의 안함 (운동 안함)", 1.2),
    LIGHT("가벼운 활동 (주 1~3회)", 1.375),
    MODERATE("보통 활동 (주 3~5회)", 1.55),
    ACTIVE("활발한 활동 (주 6~7회)", 1.725),
    VERY_ACTIVE("매우 활발 (육체노동 or 선수급)", 1.9);

    private final String description;
    private final double num;

    ActivityLevel(String description, double num) {
        this.description = description;
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public double getNum() {
        return num;
    }
}
