package com.ssafy.yumyume.util.ai;

import java.io.InputStream;
import java.util.Properties;

/**
 * AI API 관련 설정을 관리하는 유틸리티 클래스
 */
public final class AiUtil {

    public static String AI_API_KEY;

    static {
        try {
            Properties prop = new Properties();
            // resources 폴더에서 config.properties 읽기
            InputStream is = AiUtil.class.getClassLoader()
                .getResourceAsStream("config.properties");
            prop.load(is);

            // AI API 키 로드
            AI_API_KEY = prop.getProperty("ai.api.key");
            
            if (AI_API_KEY == null || AI_API_KEY.isEmpty()) {
                System.err.println("config.properties에서 ai.api.key를 찾을 수 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("AI API 키 로드 실패");
        }
    }

    // 유틸리티 클래스이므로 인스턴스화 방지
    private AiUtil() {}
}
