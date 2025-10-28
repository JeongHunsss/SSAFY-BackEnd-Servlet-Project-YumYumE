package com.ssafy.yumyume.food.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.yumyume.food.model.dao.FoodDao;
import com.ssafy.yumyume.food.model.dao.FoodDaoImpl;
import com.ssafy.yumyume.food.model.dto.FoodDto;
import com.ssafy.yumyume.util.helper.ServiceHelper;

public class FoodServiceImpl implements FoodService, ServiceHelper {
	
	private static FoodService foodService = new FoodServiceImpl();
	
	private FoodServiceImpl() {}
	
	public static FoodService getService() {
		return foodService;
	}
	
	private FoodDao foodDao = FoodDaoImpl.getDao();

	// 조건 검색
	@Override
	public List<FoodDto> search(String code, String name, String category, int pageNum, int limit) throws SQLException {
		int offset = getOffset(pageNum, limit);
		return foodDao.search(code, name, category, offset, limit);
	}
	
	@Override
	public List<FoodDto> search(String code, String name, String category) throws SQLException {
		return foodDao.search(code, name, category);
	}
	
	// code로 가져오기
	@Override
	public FoodDto getDtoByCode(String code) throws SQLException {
		return foodDao.getDtoByCode(code);
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

	@Override
	public List<String> getCategoryList() throws SQLException {
		return foodDao.getCategoryList();
	}
	
}
