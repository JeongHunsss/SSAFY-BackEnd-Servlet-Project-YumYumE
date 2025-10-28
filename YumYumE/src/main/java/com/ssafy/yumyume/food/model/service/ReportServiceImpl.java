package com.ssafy.yumyume.food.model.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.yumyume.food.model.dao.ReportDao;
import com.ssafy.yumyume.food.model.dao.ReportDaoImpl;
import com.ssafy.yumyume.food.model.dto.ReportDto;
import com.ssafy.yumyume.food.model.dto.ReportFoodDto;
import com.ssafy.yumyume.util.ai.AiUtil;
import com.ssafy.yumyume.util.helper.ServiceHelper;

public class ReportServiceImpl implements ReportService, ServiceHelper {
	
	private static ReportService reportService = new ReportServiceImpl();
	
	private ReportServiceImpl() {}
	
	public static ReportService getService() {
		return reportService;
	}
	
	private ReportDao reportDao = ReportDaoImpl.getDao();

	@Override
	public List<ReportDto> searchByTime(LocalDateTime start, LocalDateTime end, int pageNum, int limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportDto> getByUserId(String userId, int pageNum, int limit) throws SQLException {
		int offset = getOffset(pageNum, limit);
		return reportDao.getByUserId(userId, offset, limit);
	}

	@Override
	public ReportDto getDtoByReportId(int reportId) throws SQLException {
		return reportDao.getDtoByReportId(reportId);
	}
	
	@Override
	public List<ReportFoodDto> getReportFoodDtosByReportId(int reportId) throws SQLException {
		return reportDao.getReportFoodDtosByReportId(reportId);
	}
	
	@Override
	public int createReport(ReportDto dto, List<ReportFoodDto> foodList) throws SQLException {
	    // 끼니별 권장량 비율
	    double mealRatio;
	    switch(dto.getMealType()) {
	        case "BREAKFAST":
	        case "LUNCH":
	        case "DINNER":
	            mealRatio = 1.0 / 3.0; // 3끼 기준
	            break;
	        case "SNACK":
	            mealRatio = 1.0 / 6.0; // 간식은 하루 권장량의 1/6
	            break;
	        default:
	            mealRatio = 1.0 / 3.0;
	    }
		
		// 점수 계산
		double score = calculateScore(dto);
		dto.setScore(score);
		
		// AI API를 호출하여 코멘트 생성
        String comment;
        try {
            comment = getAiCommentFromApi(dto, foodList, mealRatio);
        } catch (Exception e) {
            e.printStackTrace();
            // API 호출에 실패하더라도 리포트 생성은 계속 진행
            comment = null; 
        }
        dto.setAiComment(comment);
        
		return reportDao.createReport(dto, foodList, mealRatio);
	}

	@Override
	public ReportDto updateReport(ReportDto dto) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteReport(int reportId) throws SQLException {
		return reportDao.deleteReport(reportId);
	}
	
	// 식단 점수 계산 (나트륨은 계산에서 제외)
	public double calculateScore(ReportDto dto) {
	    // 끼니별 권장량 비율
	    double mealRatio;
	    switch(dto.getMealType()) {
	        case "BREAKFAST":
	        case "LUNCH":
	        case "DINNER":
	            mealRatio = 1.0 / 3.0; // 3끼 기준
	            break;
	        case "SNACK":
	            mealRatio = 1.0 / 6.0; // 간식은 하루 권장량의 1/6
	            break;
	        default:
	            mealRatio = 1.0 / 3.0;
	    }
		
	    double standardKcal = dto.getUserDto().getStdKcal() * mealRatio;
	    double standardCarb = dto.getUserDto().getStdCarb() * mealRatio;
	    double standardProtein = dto.getUserDto().getStdProtein() * mealRatio;
	    double standardFat = dto.getUserDto().getStdFat() * mealRatio;
	    double standardSugar = dto.getUserDto().getStdSugar() * mealRatio;
	    
	    // 각 영양소별 점수 (80~120% 범위를 100점으로)
	    double kcalScore = calculateNutrientScore(dto.getKcalSum(), standardKcal);
	    double carbScore = calculateNutrientScore(dto.getCarbSum(), standardCarb);
	    double proteinScore = calculateNutrientScore(dto.getProteinSum(), standardProtein);
	    double fatScore = calculateNutrientScore(dto.getFatSum(), standardFat);
	    double sugarScore = calculateSugarScore(dto.getSugarSum(), standardSugar); // 적을수록 좋음
	    
	    // 가중 평균 (중요도에 따라 조정 가능)
	    double totalScore = (kcalScore * 0.3 + 
	                        carbScore * 0.2 + 
	                        proteinScore * 0.25 + 
	                        fatScore * 0.15 + 
	                        sugarScore * 0.1);
	    
	    return Math.round(totalScore * 10) / 10.0;
	}

	// 영양소 점수
	private double calculateNutrientScore(double actual, double standard) {
	    if (standard == 0) return 0;

	    double ratio = actual / standard;

	    if (ratio >= 0.9 && ratio <= 1.1) { // 90% ~ 110%: 매우 우수
	        return 100.0;
	    } else if (ratio >= 0.8 && ratio < 0.9) { // 80% ~ 90%: 우수
	        return 90.0 + (ratio - 0.8) * 100; // 90점 ~ 100점 사이
	    } else if (ratio > 1.1 && ratio <= 1.2) { // 110% ~ 120%: 우수
	        return 90.0 + (1.2 - ratio) * 100; // 90점 ~ 100점 사이
	    } else if (ratio >= 0.7 && ratio < 0.8) { // 70% ~ 80%: 양호
	        return 80.0 + (ratio - 0.7) * 100; // 80점 ~ 90점 사이
	    } else if (ratio > 1.2 && ratio <= 1.3) { // 120% ~ 130%: 양호
	        return 80.0 + (1.3 - ratio) * 100; // 80점 ~ 90점 사이
	    } else if (ratio > 0.6 && ratio < 0.7) { // 60% ~ 70%: 보통
	        return 70.0 + (ratio - 0.6) * 100; // 70점 ~ 80점 사이
	    } else if (ratio > 1.3 && ratio <= 1.4) { // 130% ~ 140%: 보통
	        return 70.0 + (1.4 - ratio) * 100; // 70점 ~ 80점 사이
	    } else if (ratio > 1.4) { // 140% 초과
	        return Math.max(0, 70 - (ratio - 1.4) * 100);
	    } else { // 60% 미만
	        return Math.max(0, 70 - (0.6 - ratio) * 100);
	    }
	}

	// 당분 점수
	private double calculateSugarScore(double actual, double standard) {
	    if (standard == 0) return 100;

	    double ratio = actual / standard;

	    if (ratio <= 1.0) { // 100% 이하: 매우 우수
	        return 100.0;
	    } else if (ratio <= 1.2) { // 100% ~ 120%: 우수
	        return 90.0 + (1.2 - ratio) * 50; // 90점 ~ 100점 사이
	    } else if (ratio <= 1.4) { // 120% ~ 140%: 양호
	        return 80.0 + (1.4 - ratio) * 50; // 80점 ~ 90점 사이
	    } else { // 140% 초과
	        return Math.max(0, 80 - (ratio - 1.4) * 100);
	    }
	}
	
	/**
     * Upstage (Solar) API를 호출하여 식단 분석 코멘트를 생성합니다.
     * (프롬프트 강화 및 파싱 로직 수정)
     */
    private String getAiCommentFromApi(ReportDto dto, List<ReportFoodDto> foodList, double mealRatio) throws Exception {
        
    	// 음식 목록을 JSON 배열 문자열로 변환
        StringBuilder foodsJsonArray = new StringBuilder("[");
        if (foodList != null && !foodList.isEmpty()) {
            for (int i = 0; i < foodList.size(); i++) {
                ReportFoodDto food = foodList.get(i);
                String foodName = (food.getFoodName() != null && !food.getFoodName().isEmpty()) ? food.getFoodName() : food.getFoodCode();
                String escapedFoodName = foodName.replace("\"", "\\\"");

                foodsJsonArray.append(String.format("{\"name\":\"%s\", \"weight\":%.1f}", escapedFoodName, food.getFoodWeight()));
                if (i < foodList.size() - 1) {
                    foodsJsonArray.append(",");
                }
            }
        }
        foodsJsonArray.append("]");
    	
        // 1. 데이터 포맷팅 (동일)
        String inputData = String.format(
                // 기존 JSON 객체 끝나는 '}' 앞에 쉼표(,)와 foods 배열 추가
        		"{\"score\": %.1f, "
        		+ "\"mealType\": \"%s\", "
        		+ "\"kcal\": {\"actual\": %.1f, \"standard\": %.1f}, "
        		+ "\"carb\": {\"actual\": %.1f, \"standard\": %.1f}, "
        		+ "\"protein\": {\"actual\": %.1f, \"standard\": %.1f}, "
        		+ "\"fat\": {\"actual\": %.1f, \"standard\": %.1f}, "
        		+ "\"sugar\": {\"actual\": %.1f, \"standard\": %.1f}, "
        		+ "\"foods\": %s}",
                dto.getScore(), dto.getMealType(),
                dto.getKcalSum(), dto.getUserDto().getStdKcal() * mealRatio,
                dto.getCarbSum(), dto.getUserDto().getStdCarb() * mealRatio,
                dto.getProteinSum(), dto.getUserDto().getStdProtein() * mealRatio,
                dto.getFatSum(), dto.getUserDto().getStdFat() * mealRatio,
                dto.getSugarSum(), dto.getUserDto().getStdSugar() * mealRatio,
                foodsJsonArray.toString()
            );
        
        /// 디버깅 ///
//        System.out.println("inputData : " + inputData);
//        System.out.println("foods : " + foodsJsonArray.toString());
        /////

     // 2. AI에게 보낼 프롬프트 (섭취 음식 목록 활용)
        String prompt = "당신은 데이터 기반 영양 코치 'YumYumE'입니다. "
                + "사용자의 식단 데이터를 꼼꼼하게 분석하세요. 특히 사용자가 먹은 'foods' 목록에 주의를 기울이세요: " + inputData + ". "
                + "도움이 되고 객관적인 톤으로 2~3 문장 이내의 피드백을 *한국어*로 제공하세요. "
                + "다음 규칙을 엄격히 따르세요: "
                + "1. 각 영양소의 'actual'(섭취량)과 'standard'(권장량) 비율을 분석하고, 섭취한 'foods'의 기여도를 고려하세요. "
                + "2. 특정 영양소가 현저히 높거나(예: 140% 초과) 낮다면(예: 60% 미만), *반드시* 가장 두드러진 문제를 지적하세요. 가능하다면 어떤 음식(들)이 불균형의 원인이 되었는지 간략히 언급하세요(예: '고구마를 드셨지만 탄수화물이 조금 부족했네요.' 또는 '팝콘 때문에 나트륨이 다소 높았을 수 있어요.'). '든든한 식사였어요.' 같은 모호한 표현 대신 구체적으로 지적해야 합니다. "
                + "3. 대부분의 영양소가 잘 균형 잡혀 있다면(예: 90% ~ 110% 사이), 선택한 음식들로 좋은 균형을 이룬 점을 언급하며 긍정적인 코멘트를 하세요. "
                + "4. 분석 결과와 섭취한 음식을 바탕으로, 사용자의 다음 식사를 위한 간단하고 실천 가능한 팁을 특정 음식 종류를 포함하여 제안하세요(예: '다음 식사에는 잎채소를 추가해 비타민을 보충하면 좋겠어요.'). "
                + "중요: *오직* 순수한 한국어 텍스트로만 응답하세요. 자연스러운 문장 흐름을 사용하세요. "
                + "영어, 인용 부호(\"), 괄호 안의 설명(), 목록 형식(1., 2. 등)은 절대 포함하지 마세요.";

        // 3. Upstage API 요청 본문 생성
        String apiKey = AiUtil.AI_API_KEY;
        String apiEndpoint = "https://api.upstage.ai/v1/chat/completions";
        
        String escapedPrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
        
        String requestBody = String.format(
            "{\"model\": \"solar-pro2\", \"messages\": [ {\"role\": \"system\", \"content\": \"%s\"} ], \"max_tokens\": 300, \"temperature\": 0.7}",
            escapedPrompt
        );

        // 4. API 호출
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey) 
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // 5. API 응답 수신
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 6. Upstage 응답 파싱
        try {
            String responseBody = response.body();

            // 6-1. 응답에 "choices" 블록이 있는지 확인
            if (responseBody == null || !responseBody.contains("\"choices\"")) {
                throw new Exception("API 응답이 비정상적이거나 에러를 포함하고 있습니다.");
            }

            // 6-2. "content" 키워드를 찾아 내용 추출
            String contentKey = "\"content\":\""; // 공백 없는 키
            int contentStartIdx = responseBody.indexOf(contentKey);

            if (contentStartIdx == -1) {
                // 공백 있는 키도 한번 더 확인
                contentKey = "\"content\": \"";
                contentStartIdx = responseBody.indexOf(contentKey);
                if (contentStartIdx == -1) {
                    throw new Exception("AI가 응답 생성을 거부했습니다 (content 키 없음).");
                }
            }
            
            // 키의 끝 위치로 이동 (키 + 따옴표)
            contentStartIdx += contentKey.length(); 
            
            // "message" 객체가 닫히는 '}'를 찾음
            int messageEndIdx = responseBody.indexOf("}", contentStartIdx);
            if (messageEndIdx == -1) {
                 throw new Exception("API 응답 형식이 올바르지 않습니다 (message 끝 없음).");
            }
            
            // "content"의 닫는 따옴표를 "message" 닫히기 직전에서 찾음
            int contentEndIdx = responseBody.lastIndexOf("\"", messageEndIdx);
            if (contentEndIdx == -1 || contentEndIdx < contentStartIdx) {
                 throw new Exception("API 응답 형식이 올바르지 않습니다 (content 끝 없음).");
            }

            // 6-3. 실제 내용 추출
            String aiResponse = responseBody.substring(contentStartIdx, contentEndIdx);
            
            // 6-4. 혹시 모를 괄호(...) 코멘트 제거
            int parenthesisIdx = aiResponse.indexOf("(");
            if (parenthesisIdx != -1) {
                aiResponse = aiResponse.substring(0, parenthesisIdx);
            }

            // 6-5. 이스케이프 문자 제거 및 공백 제거
            return aiResponse.replace("\\n", " ").replace("\\t", " ").replace("\\\"", "\"").trim();

        } catch (Exception e) {
            System.err.println("AI 응답 파싱 실패: " + response.body());
            throw e;
        }
    }

}
