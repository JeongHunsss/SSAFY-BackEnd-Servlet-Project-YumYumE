package com.ssafy.yumyume.food.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.yumyume.food.model.dto.FoodDto;
import com.ssafy.yumyume.food.model.service.FoodService;
import com.ssafy.yumyume.food.model.service.FoodServiceImpl;
import com.ssafy.yumyume.util.helper.ControllerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/food")
public class FoodController extends HttpServlet implements ControllerHelper {
	private static final long serialVersionUID = 1L;
	
	private final FoodService foodService = FoodServiceImpl.getService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response);
		String path = "/view/food";
		
		if("list".equals(action)) { // 리스트 검색
			List<FoodDto> foodList = null;
			try {
				foodList = getFoodList(request, response); // 페이지네이션 검색
				int searchCount = getSearchCount(request, response); // 전체 검색 수
				List<String> categoryList = getCategoryList(request, response); // 카테고리 리스트
				int limit = getLimitParameter(request, response);
				int totalPages = searchCount / limit + 1;
				
				request.setAttribute("foodList", foodList);
				request.setAttribute("categoryList", categoryList);
				request.setAttribute("totalSearchCount", searchCount);
				request.setAttribute("totalPages", totalPages);

				path += "/list.jsp";
				forward(request, response, path);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendError(500);
			}
		} else if("view".equals(action)) { // 상세보기
			try {
				FoodDto foodDto = getFoodDto(request, response);
				
				if(foodDto != null) {
					path += "/food_detail.jsp";
					request.setAttribute("foodDto", foodDto);
					forward(request, response, path);
				} else {
					System.out.println("foodDto is null");
					response.sendError(500);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendError(500);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String path = "/index.jsp";
		
		if("list".equals(action)) {
//			System.out.println("!!!");
		}
	}
	
	private List<FoodDto> getFoodList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<FoodDto> foodList = new ArrayList<>();
	    // pageNum 파라미터 처리 (null 체크)
	    String pageNumStr = request.getParameter("pageNum");
	    int pageNum = (pageNumStr != null && !pageNumStr.isEmpty()) ? Integer.parseInt(pageNumStr) : 1;
	    
//		int pageNum = 1; // 임시
		int limit = getLimitParameter(request, response);
		
	    // 검색 파라미터 받기
	    String code = request.getParameter("code");
	    String name = request.getParameter("name");
	    String category = request.getParameter("category");
	    
        foodList = foodService.search(code, name, category, pageNum, limit);
       
        ///// 디버깅 /////
//		if(foodList.isEmpty()) {
//			System.out.println("foodList 비어있음");
//		} else {
//			for(int i = 0 ; i < foodList.size(); i++) {
//				System.out.println("code: " + foodList.get(i).getCode());
//				System.out.println("name: " + foodList.get(i).getName());
//				System.out.println("category: " + foodList.get(i).getCategory());
//			}
//		}
		////////////////

		return foodList;
	}
	
	private int getSearchCount(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<FoodDto> foodList = new ArrayList();
	    // 검색 파라미터 받기
	    String code = request.getParameter("code");
	    String name = request.getParameter("name");
	    String category = request.getParameter("category");
	    
	    /// 디버깅 ///
//	    System.out.println("category: " + category);
	    /////
	    
        foodList = foodService.search(code, name, category);
        
        return foodList.size();
	}
	
	private List<String> getCategoryList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		return foodService.getCategoryList();
	}
	
	private FoodDto getFoodDto(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		FoodDto foodDto = new FoodDto();
		
	    // 파라미터 받기
	    String code = request.getParameter("code");
	   
        foodDto = foodService.getDtoByCode(code);
       
        ///// 디버깅 /////
//		System.out.println("code: " + foodDto.getCode());
//		System.out.println("name: " + foodDto.getName());
//		System.out.println("category: " + foodDto.getCategory());
		////////////////

		return foodDto;
	}

}
