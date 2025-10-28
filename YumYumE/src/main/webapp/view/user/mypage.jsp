<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/view/common/header.jsp" %>

<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="${root}/user?action=login"/>
</c:if>

<main>
	<c:if test="${empty update }">
	    <div class="container">
	        <div class="card" style="max-width: 800px; margin: 2rem auto;">
	            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
	                <h2 class="card-title" style="margin-bottom: 0;">내 정보</h2>
	                <a href="${root}/user?action=updateMypage&id=${userInfo.id}" 
	                   class="btn btn-primary" style="padding: 0.75rem 1.5rem;">
	                    내 정보 수정
	                </a>
	            </div>
	            
	            <!-- 기본 정보 -->
	            <div style="background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%); 
	                        padding: 2rem; border-radius: 15px; margin-bottom: 2rem;">
	                <h3 style="margin-bottom: 1.5rem; color: #333;">기본 정보</h3>
	                <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 1rem;">
	                    <div>
	                        <p style="color: #888; margin-bottom: 0.3rem;">아이디</p>
	                        <p style="font-weight: 600; font-size: 1.1rem;">${userInfo.id}</p>
	                    </div>
	                    <div>
	                        <p style="color: #888; margin-bottom: 0.3rem;">이름</p>
	                        <p style="font-weight: 600; font-size: 1.1rem;">${userInfo.name}</p>
	                    </div>
	                    <div>
	                        <p style="color: #888; margin-bottom: 0.3rem;">이메일</p>
	                        <p style="font-weight: 600; font-size: 1.1rem;">${userInfo.email}@${userInfo.emailDomain}</p>
	                    </div>
	                    <div>
	                        <p style="color: #888; margin-bottom: 0.3rem;">나이</p>
	                        <p style="font-weight: 600; font-size: 1.1rem;">${userInfo.age}세</p>
	                    </div>
	                </div>
	            </div>
	            
	            <!-- 신체 정보 -->
	            <div style="margin-bottom: 2rem;">
	                <h3 style="margin-bottom: 1.5rem; color: #333;">신체 정보</h3>
	                <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 1.5rem;">
	                    <div style="text-align: center; padding: 1.5rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">성별</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">${userInfo.gender}</p>
	                    </div>
	                    <div style="text-align: center; padding: 1.5rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">키</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">${userInfo.height} cm</p>
	                    </div>
	                    <div style="text-align: center; padding: 1.5rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">몸무게</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">${userInfo.weight} kg</p>
	                    </div>
	                    <div style="text-align: center; padding: 1.5rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">표준 체중</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdWeight} kg
	                        </p>
	                    </div>
	                </div>
	            </div>
	            
	            <!-- 활동 수준 -->
	            <div style="margin-bottom: 2rem;">
	                <h3 style="margin-bottom: 1rem; color: #333;">활동 수준</h3>
	                <div style="padding: 1.5rem; background: #f5f5f5; border-radius: 10px;">
	                    <c:choose>
	                        <c:when test="${userInfo.activityLevel == 'SEDENTARY'}">
	                            <p style="font-size: 1.2rem; font-weight: 600; color: #667eea;">거의 안함 (운동 안함)</p>
	                        </c:when>
	                        <c:when test="${userInfo.activityLevel == 'LIGHT'}">
	                            <p style="font-size: 1.2rem; font-weight: 600; color: #667eea;">가벼운 활동 (주 1~3회)</p>
	                        </c:when>
	                        <c:when test="${userInfo.activityLevel == 'MODERATE'}">
	                            <p style="font-size: 1.2rem; font-weight: 600; color: #667eea;">보통 활동 (주 3~5회)</p>
	                        </c:when>
	                        <c:when test="${userInfo.activityLevel == 'ACTIVE'}">
	                            <p style="font-size: 1.2rem; font-weight: 600; color: #667eea;">활발한 활동 (주 6~7회)</p>
	                        </c:when>
	                        <c:when test="${userInfo.activityLevel == 'VERY_ACTIVE'}">
	                            <p style="font-size: 1.2rem; font-weight: 600; color: #667eea;">매우 활발 (육체노동 or 선수급)</p>
	                        </c:when>
	                    </c:choose>
	                </div>
	            </div>
	            
	            <!-- 권장 영양소 -->
	            <div style="margin-bottom: 2rem;">
	                <h3 style="margin-bottom: 1.5rem; color: #333;">일일 권장 영양소</h3>
	                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1.5rem;">
	                    <div style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">칼로리</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdKcal} kcal
	                        </p>
	                    </div>
	                    <div style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">탄수화물</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdCarb} g
	                        </p>
	                    </div>
	                    <div style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">단백질</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdProtein} g
	                        </p>
	                    </div>
	                    <div style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">지방</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdFat} g
	                        </p>
	                    </div>
	                    <div style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">당류</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdSugar} g
	                        </p>
	                    </div>
	                    <div style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
	                        <p style="color: #888; margin-bottom: 0.5rem;">나트륨</p>
	                        <p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
	                            ${userInfo.stdNatrium} g
	                        </p>
	                    </div>
	                </div>
	            </div>
	            
	            <!-- 버튼 -->
	            <div style="display: flex; gap: 1rem; justify-content: center; margin-top: 2rem;">
	                <a href="${root}/report?action=list&userId=${userInfo.id}" 
	                   class="btn btn-primary" style="padding: 1rem 2rem;">
	                    내 리포트 보기
	                </a>
	                <form action="${root}/user" method="post" 
	                      onsubmit="return confirm('정말 탈퇴하시겠습니까?');">
	                    <input type="hidden" name="action" value="delete">
	                    <input type="hidden" name="id" value="${userInfo.id}">
	                    <button type="submit" class="btn btn-secondary" style="padding: 1rem 2rem;">
	                        회원 탈퇴
	                    </button>
	                </form>
	            </div>
	        </div>
	    </div>
	</c:if>
	
	<c:if test="${!empty update }">
		<div class="container">
            <div class="card" style="max-width: 800px; margin: 2rem auto;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                    <h2 class="card-title" style="margin-bottom: 0;">내 정보 수정</h2>
                </div>
                
                <form action="${root}/user" method="post" onsubmit="return validateUpdateForm()">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="${userInfo.id}">
                    
                    <!-- 기본 정보 -->
                    <div style="background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%); 
                                padding: 2rem; border-radius: 15px; margin-bottom: 2rem;">
                        <h3 style="margin-bottom: 1.5rem; color: #333;">기본 정보</h3>
                        <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 1rem;">
                            <div>
                                <p style="color: #888; margin-bottom: 0.3rem;">아이디</p>
                                <p style="font-weight: 600; font-size: 1.1rem;">${userInfo.id}</p>
                            </div>
                            <div class="form-group">
                                <label for="name">이름 *</label>
                                <input type="text" id="name" name="name" class="form-control"
                                       value="${userInfo.name}" required>
                            </div>
                            <div class="form-group" style="grid-column: span 2;">
                                <label for="email">이메일 *</label>
                                <div style="display: flex; gap: 0.5rem; align-items: center;">
                                    <input type="text" id="email" name="email" class="form-control" 
                                           value="${userInfo.email}" placeholder="이메일" required>
                                    <span>@</span>
                                    <input type="text" id="email_domain" name="email_domain" class="form-control" 
                                           value="${userInfo.emailDomain}" placeholder="도메인" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="age">나이 *</label>
                                <input type="number" id="age" name="age" class="form-control" 
                                       value="${userInfo.age}" min="1" max="150" required>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 신체 정보 -->
                    <div style="margin-bottom: 2rem;">
                        <h3 style="margin-bottom: 1.5rem; color: #333;">신체 정보</h3>
                        <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 1.5rem;">
                            <div class="form-group">
                                <label>성별 *</label>
                                <div style="display: flex; gap: 2rem;">
                                    <label style="cursor: pointer;">
                                        <input type="radio" name="gender" value="남" 
                                               ${userInfo.gender == '남' ? 'checked' : ''} required>
                                        남성
                                    </label>
                                    <label style="cursor: pointer;">
                                        <input type="radio" name="gender" value="여" 
                                               ${userInfo.gender == '여' ? 'checked' : ''} required>
                                        여성
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="height">키 (cm) *</label>
                                <input type="number" id="height" name="height" class="form-control" 
                                       value="${userInfo.height}" min="80" max="250" step="0.1" required>
                            </div>
                            <div class="form-group">
                                <label for="weight">몸무게 (kg) *</label>
                                <input type="number" id="weight" name="weight" class="form-control" 
                                       value="${userInfo.weight}" min="20" max="300" step="0.1" required>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 활동 수준 -->
                    <div style="margin-bottom: 2rem;">
                        <h3 style="margin-bottom: 1rem; color: #333;">활동 수준</h3>
                        <div class="form-group">
                            <label for="activity_level">활동 수준 *</label>
                            <select id="activity_level" name="activity_level" class="form-control" required>
                                <option value="">활동 수준을 선택하세요</option>
                                <option value="SEDENTARY" ${userInfo.activityLevel == 'SEDENTARY' ? 'selected' : ''}>
                                    거의 안함 (운동 안함)
                                </option>
                                <option value="LIGHT" ${userInfo.activityLevel == 'LIGHT' ? 'selected' : ''}>
                                    가벼운 활동 (주 1~3회)
                                </option>
                                <option value="MODERATE" ${userInfo.activityLevel == 'MODERATE' ? 'selected' : ''}>
                                    보통 활동 (주 3~5회)
                                </option>
                                <option value="ACTIVE" ${userInfo.activityLevel == 'ACTIVE' ? 'selected' : ''}>
                                    활발한 활동 (주 6~7회)
                                </option>
                                <option value="VERY_ACTIVE" ${userInfo.activityLevel == 'VERY_ACTIVE' ? 'selected' : ''}>
                                    매우 활발 (육체노동 or 선수급)
                                </option>
                            </select>
                        </div>
                    </div>
                    
                    <!-- 버튼 -->
                    <div style="margin-top: 2rem; display: flex; gap: 1rem; justify-content: center;">
                        <button type="submit" class="btn btn-primary" style="padding: 1rem 2rem;">
                            정보 수정 완료
                        </button>
                        <a href="${root}/user?action=mypage&id=${userInfo.id}" 
                           class="btn btn-secondary" style="padding: 1rem 2rem;">
                            취소
                        </a>
                    </div>
                </form>
            </div>
        </div>
        
        <script>
        function validateUpdateForm() {
            return true;
        }
        </script>
	</c:if>
</main>

<%@ include file="/view/common/footer.jsp" %>