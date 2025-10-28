package com.ssafy.yumyume.food.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.yumyume.food.model.dto.ReportDto;
import com.ssafy.yumyume.food.model.dto.ReportFoodDto;
import com.ssafy.yumyume.food.model.service.ReportService;
import com.ssafy.yumyume.food.model.service.ReportServiceImpl;
import com.ssafy.yumyume.user.model.dto.UserDto;
import com.ssafy.yumyume.util.helper.ControllerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/report")
public class ReportController extends HttpServlet implements ControllerHelper {
	private static final long serialVersionUID = 1L;
	
	private final ReportService reportService = ReportServiceImpl.getService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response);
		String path = "/view/report";
		
		if("list".equals(action)) { // 리스트 검색
			try {
				List<ReportDto> reportList = getReportList(request, response);
				if(reportList == null) {
					request.setAttribute("alertMsg", "로그인 후 이용해주세요.");
					forward(request, response, "/view/user/login.jsp");
				} else {
					request.setAttribute("reportList", reportList);
					
					path += "/list.jsp";
					forward(request, response, path);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendError(500);
			}
		} else if("view".equals(action)) { // 결과지 조회
			try {
				ReportDto reportDto = getReportDto(request, response);
				List<ReportFoodDto> foodList = getReportFoodDtos(request, response);
				
				if(reportDto != null && foodList != null) {
					path += "/report.jsp";
					request.setAttribute("report", reportDto);
					request.setAttribute("foodList", foodList);
					forward(request, response, path);
				} else {
					System.out.println("reportDto is null");
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
		String action = getActionParameter(request, response);
		String path = "/view/food";
		UserDto userDto = (UserDto) request.getSession().getAttribute("userInfo");
		
		if("create".equals(action)) { // 결과지 만들기
			try {
				int result = createReport(request, response);

//				System.out.println("result: " + result);
				
				if(result == 1) {
					path = "/report?action=list&userId=" + userDto.getId();
					redirect(request, response, path);
				} else {
					System.out.println("결과지 생성 실패");
					response.sendError(500);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendError(500);
			}
		} else if("delete".equals(action)) { // 결과지 삭제
			try {
				int result = deleteReport(request, response);
				
				if(result == 1) {
//					System.out.println("삭제 성공");
					path = "/report?action=list&userId=" + userDto.getId();
					redirect(request, response, path);
				} else {
					System.out.println("결과지 삭제 실패");
					response.sendError(500);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendError(500);
			}
		}
	}

	private int createReport(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userInfo");
		
		if(userDto == null) { // 로그인 필요
			return 0;
		}
		
		ReportDto reportDto = new ReportDto();
		
		reportDto.setMealType(request.getParameter("meal_type"));
		reportDto.setCarbSum(Double.parseDouble(request.getParameter("carb_sum")));
		reportDto.setProteinSum(Double.parseDouble(request.getParameter("protein_sum")));
		reportDto.setFatSum(Double.parseDouble(request.getParameter("fat_sum")));
		reportDto.setKcalSum(Double.parseDouble(request.getParameter("kcal_sum")));
		reportDto.setSugarSum(Double.parseDouble(request.getParameter("sugar_sum")));
		reportDto.setNatriumSum(Double.parseDouble(request.getParameter("natrium_sum")));
		reportDto.setUserDto(userDto);
		
		// 담은 음식 목록 파싱
        String[] foodCodes = request.getParameterValues("food_code");
        String[] foodWeightsStr = request.getParameterValues("food_weight");
        String[] foodNames = request.getParameterValues("food_name");

        List<ReportFoodDto> foodList = new ArrayList<>();
        // 배열이 null이 아니고 길이가 같을 때만 처리
        if (foodCodes != null && foodWeightsStr != null && foodNames != null && foodCodes.length == foodWeightsStr.length && foodCodes.length == foodNames.length) {
            for (int i = 0; i < foodCodes.length; i++) {
                 // 빈 값이나 null은 건너뛰기
                if (foodCodes[i] == null || foodCodes[i].isEmpty() ||
                    foodWeightsStr[i] == null || foodWeightsStr[i].isEmpty() ||
                    foodNames[i] == null || foodNames[i].isEmpty()) {
                    continue;
                }

                ReportFoodDto foodDto = new ReportFoodDto();
                foodDto.setFoodCode(foodCodes[i]); // 배열의 i번째 food_code 설정
                foodDto.setFoodName(foodNames[i]); // 이름
                
                try {
                    // 배열의 i번째 food_weight를 double로 변환하여 설정
                    foodDto.setFoodWeight(Double.parseDouble(foodWeightsStr[i]));
                    foodList.add(foodDto);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing food weight: " + foodWeightsStr[i] + " for code: " + foodCodes[i]);
                    // 에러 발생 시 해당 음식은 제외하고 계속 진행
                }
            }
        }
		
		return reportService.createReport(reportDto, foodList);
	}
	
	private int deleteReport(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int reportId = Integer.parseInt(request.getParameter("reportId"));
		int result = reportService.deleteReport(reportId);
		
		return result;
	}
	
	private ReportDto getReportDto(HttpServletRequest request, HttpServletResponse response) throws SQLException {		
	    // 파라미터 받기
	    int reportId = Integer.parseInt(request.getParameter("reportId"));
	   
	    ReportDto reportDto = reportService.getDtoByReportId(reportId);
       
        ///// 디버깅 /////
//		System.out.println("id: " + reportDto.getId());
//		System.out.println("score: " + reportDto.getScore());
//		System.out.println("user_id: " + reportDto.getUserDto().getId());
//		System.out.println("user_std_kcal: " + reportDto.getUserDto().getStdKcal());
//		System.out.println("created: " + reportDto.getCreatedAt());
		////////////////

		return reportDto;
	}
	
	private List<ReportFoodDto> getReportFoodDtos(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int reportId = Integer.parseInt(request.getParameter("reportId"));
		
		List<ReportFoodDto> foodList = reportService.getReportFoodDtosByReportId(reportId);
		return foodList;
	}
	
	private List<ReportDto> getReportList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String userId = request.getParameter("userId");
		
		if(userId.isEmpty()) {
			return null;
		} else {
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			int pageNum = 1; // 임시
			
			int limit = getLimitParameter(request, response);
			
			List<ReportDto> reportList = reportService.getByUserId(userId, pageNum, limit);
			
			/////// 디버깅 //////
//		for(ReportDto dto : reportList) {
//			System.out.println("id: " + dto.getId());
//		}
			///////////////////
			return reportList;
		}
	}

}
