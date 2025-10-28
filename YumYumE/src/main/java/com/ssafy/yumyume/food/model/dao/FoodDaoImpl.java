package com.ssafy.yumyume.food.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.yumyume.food.model.dto.FoodDto;
import com.ssafy.yumyume.util.db.DbUtil;

public class FoodDaoImpl implements FoodDao {

	private static FoodDao foodDao = new FoodDaoImpl();
	
	private FoodDaoImpl() {}
	
	public static FoodDao getDao() {
		return foodDao;
	}
	
	// 조건 검색 
	@Override
	public List<FoodDto> search(String code, String name, String category, int offset, int limit) throws SQLException {
	    List<FoodDto> list = new ArrayList();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select code, name, category, standard, kcal, carb_g, protein_g, fat_g, sugar_g, natrium_g \n");
	        sql.append("from food \n");
	        sql.append("where 1=1 \n");
	        
	        // 동적 조건 추가
	        if(code != null && !code.isEmpty()) {
	            sql.append("and code like ? \n");
	        }
	        if(name != null && !name.isEmpty()) {
	            sql.append("and name like ? \n");
	        }
	        if(category != null && !category.isEmpty()) {
	            sql.append("and category like ? \n");
	        }
	        
	        sql.append("order by category, code limit ?, ?");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        
	        // 파라미터 바인딩
	        int idx = 1;
	        if(code != null && !code.isEmpty()) {
	            pstmt.setString(idx++, "%" + code + "%");
	        }
	        if(name != null && !name.isEmpty()) {
	            pstmt.setString(idx++, "%" + name + "%");
	        }
	        if(category != null && !category.isEmpty()) {
	            pstmt.setString(idx++, "%" + category + "%");
	        }
	        pstmt.setInt(idx++, offset);
	        pstmt.setInt(idx++, limit);
	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            FoodDto foodDto = new FoodDto();
	            foodDto.setCode(rs.getString("code"));
	            foodDto.setName(rs.getString("name"));
	            foodDto.setCategory(rs.getString("category"));
	            foodDto.setStandard(rs.getString("standard"));
	            foodDto.setKcal(rs.getDouble("kcal"));
	            foodDto.setProtein(rs.getDouble("protein_g"));
	            foodDto.setFat(rs.getDouble("fat_g"));
	            foodDto.setCarb(rs.getDouble("carb_g"));
	            foodDto.setSugar(rs.getDouble("sugar_g"));
	            foodDto.setNatrium(rs.getDouble("natrium_g"));
	            list.add(foodDto);
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return list;
	}
	
	// limit 없는 검색
	@Override
	public List<FoodDto> search(String code, String name, String category) throws SQLException {
	    List<FoodDto> list = new ArrayList();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select code, name, category, standard, kcal, carb_g, protein_g, fat_g, sugar_g, natrium_g \n");
	        sql.append("from food \n");
	        sql.append("where 1=1 \n");
	        
	        // 동적 조건 추가
	        if(code != null && !code.isEmpty()) {
	            sql.append("and code like ? \n");
	        }
	        if(name != null && !name.isEmpty()) {
	            sql.append("and name like ? \n");
	        }
	        if(category != null && !category.isEmpty()) {
	            sql.append("and category like ? \n");
	        }
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        
	        // 파라미터 바인딩
	        int idx = 1;
	        if(code != null && !code.isEmpty()) {
	            pstmt.setString(idx++, "%" + code + "%");
	        }
	        if(name != null && !name.isEmpty()) {
	            pstmt.setString(idx++, "%" + name + "%");
	        }
	        if(category != null && !category.isEmpty()) {
	            pstmt.setString(idx++, "%" + category + "%");
	        }

	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            FoodDto foodDto = new FoodDto();
	            foodDto.setCode(rs.getString("code"));
	            foodDto.setName(rs.getString("name"));
	            foodDto.setCategory(rs.getString("category"));
	            foodDto.setStandard(rs.getString("standard"));
	            foodDto.setKcal(rs.getDouble("kcal"));
	            foodDto.setProtein(rs.getDouble("protein_g"));
	            foodDto.setFat(rs.getDouble("fat_g"));
	            foodDto.setCarb(rs.getDouble("carb_g"));
	            foodDto.setSugar(rs.getDouble("sugar_g"));
	            foodDto.setNatrium(rs.getDouble("natrium_g"));
	            list.add(foodDto);
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return list;
	}
	
	// 코드로 가져오기
	@Override
	public FoodDto getDtoByCode(String code) throws SQLException {
	    FoodDto foodDto = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select code, name, category, standard, kcal, carb_g, protein_g, fat_g, sugar_g, natrium_g \n");
	        sql.append("from food \n");
	        sql.append("where code = ? \n");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        
	        pstmt.setString(1, code);
	        
	        rs = pstmt.executeQuery();
	        if(rs.next()) {
	            foodDto = new FoodDto();
	            foodDto.setCode(rs.getString("code"));
	            foodDto.setName(rs.getString("name"));
	            foodDto.setCategory(rs.getString("category"));
	            foodDto.setStandard(rs.getString("standard"));
	            foodDto.setKcal(rs.getDouble("kcal"));
	            foodDto.setProtein(rs.getDouble("protein_g"));
	            foodDto.setFat(rs.getDouble("fat_g"));
	            foodDto.setCarb(rs.getDouble("carb_g"));
	            foodDto.setSugar(rs.getDouble("sugar_g"));
	            foodDto.setNatrium(rs.getDouble("natrium_g"));
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return foodDto;
	}
	
	@Override
	public List<String> getCategoryList() throws SQLException {
	    List<String> list = new ArrayList();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DbUtil.getConnection();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select distinct category \n");
	        sql.append("from food \n");
	        
	        pstmt = conn.prepareStatement(sql.toString());
	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            list.add(rs.getString("category"));
	        }
	    } finally {
	        DbUtil.close(rs, pstmt, conn);
	    }
	    return list;
	}

	@Override
	public FoodDto updateFood(FoodDto dto) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteFood(String code) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertFood(FoodDto dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
