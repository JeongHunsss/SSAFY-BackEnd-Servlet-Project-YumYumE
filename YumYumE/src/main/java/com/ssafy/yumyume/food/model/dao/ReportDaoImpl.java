package com.ssafy.yumyume.food.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.yumyume.food.model.dto.ReportDto;
import com.ssafy.yumyume.food.model.dto.ReportFoodDto;
import com.ssafy.yumyume.user.model.dto.UserDto;
import com.ssafy.yumyume.util.db.DbUtil;

public class ReportDaoImpl implements ReportDao {
	
	private static ReportDao reportDao = new ReportDaoImpl();
	
	private ReportDaoImpl() {}
	
	public static ReportDao getDao() {
		return reportDao;
	}

	@Override
	public List<ReportDto> getByUserId(String userId, int offset, int limit) throws SQLException {
		List<ReportDto> list = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	    	list = new ArrayList<>();
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select report_id, user_id, meal_type, score, kcal_sum_g, carb_sum_g, protein_sum_g, fat_sum_g, sugar_sum_g, natrium_sum_g, "
	        		+ "user_age, user_height, user_weight, user_gender, user_activity_level, "
	        		+ "user_std_weight, user_one_meal_std_kcal, user_one_meal_std_carb_g, user_one_meal_std_protein_g, user_one_meal_std_fat_g, user_one_meal_std_sugar_g, user_one_meal_std_natrium_g, created_at \n");
	        sql.append("from report \n");
	        sql.append("where user_id = ? \n");	        
	        sql.append("order by report_id desc limit ?, ?");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        
	        // 파라미터 바인딩
	        int idx = 1;
	        pstmt.setString(idx++, userId);
	        pstmt.setInt(idx++, offset);
	        pstmt.setInt(idx++, limit);
	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            ReportDto reportDto = new ReportDto();
	            reportDto.setId(rs.getInt("report_id"));
	            reportDto.setMealType(rs.getString("meal_type"));
	            reportDto.setScore(rs.getDouble("score"));
	            reportDto.setKcalSum(rs.getDouble("kcal_sum_g"));
	            reportDto.setCarbSum(rs.getDouble("carb_sum_g"));
	            reportDto.setProteinSum(rs.getDouble("protein_sum_g"));
	            reportDto.setFatSum(rs.getDouble("fat_sum_g"));
	            reportDto.setSugarSum(rs.getDouble("sugar_sum_g"));
	            reportDto.setNatriumSum(rs.getDouble("natrium_sum_g"));
	            reportDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
	            
	            UserDto userDto = new UserDto();
	            userDto.setId(rs.getString("user_id"));
	            userDto.setAge(rs.getInt("user_age"));
	            userDto.setHeight(rs.getDouble("user_height"));
	            userDto.setWeight(rs.getDouble("user_weight"));
	            userDto.setGender(rs.getString("user_gender"));
	            userDto.setActivityLevel(rs.getString("user_activity_level"));
	            userDto.setStdWeight(rs.getDouble("user_std_weight"));
	            userDto.setStdKcal(rs.getDouble("user_one_meal_std_kcal"));
	            userDto.setStdCarb(rs.getDouble("user_one_meal_std_carb_g"));
	            userDto.setStdProtein(rs.getDouble("user_one_meal_std_protein_g"));
	            userDto.setStdFat(rs.getDouble("user_one_meal_std_fat_g"));
	            userDto.setStdSugar(rs.getDouble("user_one_meal_std_sugar_g"));
	            userDto.setStdNatrium(rs.getDouble("user_one_meal_std_natrium_g"));
	            
	            reportDto.setUserDto(userDto);

	            list.add(reportDto);
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return list;
	}

	@Override
	public ReportDto getDtoByReportId(int reportId) throws SQLException {
	    ReportDto reportDto = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select report_id, user_id, meal_type, score, ai_comment, kcal_sum_g, carb_sum_g, protein_sum_g, fat_sum_g, sugar_sum_g, natrium_sum_g, \n");
	        sql.append("       user_age, user_height, user_weight, user_gender, user_activity_level, \n");
	        sql.append("       user_std_weight, user_one_meal_std_kcal, user_one_meal_std_carb_g, user_one_meal_std_protein_g, user_one_meal_std_fat_g, user_one_meal_std_sugar_g, user_one_meal_std_natrium_g, created_at \n");
	        sql.append("from report \n");
	        sql.append("where report_id = ?");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setInt(1, reportId);
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	            reportDto = new ReportDto();
	            reportDto.setId(rs.getInt("report_id"));
	            reportDto.setMealType(rs.getString("meal_type"));
	            reportDto.setScore(rs.getDouble("score"));
	            reportDto.setAiComment(rs.getString("ai_comment"));
	            reportDto.setKcalSum(rs.getDouble("kcal_sum_g"));
	            reportDto.setCarbSum(rs.getDouble("carb_sum_g"));
	            reportDto.setProteinSum(rs.getDouble("protein_sum_g"));
	            reportDto.setFatSum(rs.getDouble("fat_sum_g"));
	            reportDto.setSugarSum(rs.getDouble("sugar_sum_g"));
	            reportDto.setNatriumSum(rs.getDouble("natrium_sum_g"));
	            reportDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
	            
	            UserDto userDto = new UserDto();
	            userDto.setId(rs.getString("user_id"));
	            userDto.setAge(rs.getInt("user_age"));
	            userDto.setHeight(rs.getDouble("user_height"));
	            userDto.setWeight(rs.getDouble("user_weight"));
	            userDto.setGender(rs.getString("user_gender"));
	            userDto.setActivityLevel(rs.getString("user_activity_level"));
	            userDto.setStdWeight(rs.getDouble("user_std_weight"));
	            userDto.setStdKcal(rs.getDouble("user_one_meal_std_kcal"));
	            userDto.setStdCarb(rs.getDouble("user_one_meal_std_carb_g"));
	            userDto.setStdProtein(rs.getDouble("user_one_meal_std_protein_g"));
	            userDto.setStdFat(rs.getDouble("user_one_meal_std_fat_g"));
	            userDto.setStdSugar(rs.getDouble("user_one_meal_std_sugar_g"));
	            userDto.setStdNatrium(rs.getDouble("user_one_meal_std_natrium_g"));
	            
	            reportDto.setUserDto(userDto);
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return reportDto;
	}
	
	@Override
	public List<ReportFoodDto> getReportFoodDtosByReportId(int reportId) throws SQLException {
		List<ReportFoodDto> foodList = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT rf.report_id, rf.food_code, rf.food_weight, f.name \n");
	        sql.append("       FROM report_foods rf  \n");
	        sql.append("       JOIN food f ON rf.food_code = f.code \n");
	        sql.append("	   WHERE rf.report_id = ?");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setInt(1, reportId);
	        rs = pstmt.executeQuery();
	        
	        foodList = new ArrayList<>();
	        while(rs.next()) {
	            ReportFoodDto food = new ReportFoodDto();
	            food.setFoodCode(rs.getString("food_code"));
	            food.setFoodName(rs.getString("name"));
	            food.setFoodWeight(rs.getDouble("food_weight"));

	            foodList.add(food);
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return foodList;
	}

	@Override
	public int createReport(ReportDto dto, List<ReportFoodDto> foodList, double mealRatio) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        conn = DbUtil.getConnection();
	        
	        // report 만들기
	        StringBuilder sql = new StringBuilder();
	        sql.append("insert into report (user_id, meal_type, score, ai_comment, kcal_sum_g, carb_sum_g, protein_sum_g, fat_sum_g, sugar_sum_g, natrium_sum_g, \n");
	        sql.append("                    user_age, user_height, user_weight, user_gender, user_activity_level, \n");
	        sql.append("                    user_std_weight, user_one_meal_std_kcal, user_one_meal_std_carb_g, user_one_meal_std_protein_g, user_one_meal_std_fat_g, user_one_meal_std_sugar_g, user_one_meal_std_natrium_g) \n");
	        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        UserDto user = dto.getUserDto();
	        
	        int idx = 1;
	        pstmt.setString(idx++, user.getId());
	        pstmt.setString(idx++, dto.getMealType());
	        pstmt.setDouble(idx++, dto.getScore());
	        pstmt.setString(idx++, dto.getAiComment());
	        pstmt.setDouble(idx++, dto.getKcalSum());
	        pstmt.setDouble(idx++, dto.getCarbSum());
	        pstmt.setDouble(idx++, dto.getProteinSum());
	        pstmt.setDouble(idx++, dto.getFatSum());
	        pstmt.setDouble(idx++, dto.getSugarSum());
	        pstmt.setDouble(idx++, dto.getNatriumSum());
	        pstmt.setInt(idx++, user.getAge());
	        pstmt.setDouble(idx++, user.getHeight());
	        pstmt.setDouble(idx++, user.getWeight());
	        pstmt.setString(idx++, user.getGender());
	        pstmt.setString(idx++, user.getActivityLevel());
	        pstmt.setDouble(idx++, user.getStdWeight());
	        pstmt.setDouble(idx++, user.getStdKcal() * mealRatio);
	        pstmt.setDouble(idx++, user.getStdCarb() * mealRatio);
	        pstmt.setDouble(idx++, user.getStdProtein() * mealRatio);
	        pstmt.setDouble(idx++, user.getStdFat() * mealRatio);
	        pstmt.setDouble(idx++, user.getStdSugar() * mealRatio);
	        pstmt.setDouble(idx++, user.getStdNatrium() * mealRatio);
	        
	        result = pstmt.executeUpdate();
	        
	        // report 만들기에 성공했다면 reportFoods 만들기
	        if(result == 1) {
	        	result = 0;
	        	
	        	sql = new StringBuilder();
		        sql.append("select report_id from report where user_id = ? order by report_id DESC limit 1");
		        
		        pstmt = conn.prepareStatement(sql.toString());
		        
		        idx = 1;
		        pstmt.setString(idx++, user.getId());
		        
		        ResultSet rs = null;
		        rs = pstmt.executeQuery();
		        if(rs.next()) {
		        	for(ReportFoodDto reportFoodDto : foodList) {
				        sql = new StringBuilder();
				        sql.append("insert into report_foods (report_id, food_code, food_weight) \n");
				        sql.append("values (?, ?, ?)");
				        
				        pstmt = conn.prepareStatement(sql.toString());
				        idx = 1;
				        pstmt.setString(idx++, rs.getString("report_id"));
				        pstmt.setString(idx++, reportFoodDto.getFoodCode());
				        pstmt.setDouble(idx++, reportFoodDto.getFoodWeight());
				        
				        result = pstmt.executeUpdate();
		        	}
		        }
		    }
	    } finally {
	        DbUtil.close(pstmt, conn);
	    }
	    
	    return result;
	}
	
	@Override
	public int deleteReport(int reportId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("delete from report where report_id = ?");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setInt(1, reportId);

	        result = pstmt.executeUpdate();
	    } finally {
	        DbUtil.close(pstmt, conn);
	    }
	    
	    return result;
	}

	@Override
	public ReportDto updateReport(ReportDto dto) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ReportDto> searchByTime(LocalDateTime start, LocalDateTime end, int offset, int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
