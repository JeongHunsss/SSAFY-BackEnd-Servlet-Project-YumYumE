<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/view/common/header.jsp" %>

<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="/view/user?action=login"/>
</c:if>

<main>
    <div class="container">
        <div class="card">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <h2 class="card-title" style="margin-bottom: 0;">나의 영양 분석 리포트</h2>
                <a href="${root}/food?action=list" 
                   class="btn btn-primary">새 분석 시작</a>
            </div>
            
            <c:choose>
                <c:when test="${empty reportList}">
                    <div style="text-align: center; padding: 3rem; color: #888;">
                        <p style="font-size: 1.5rem; margin-bottom: 1rem;">📊</p>
                        <p style="font-size: 1.2rem;">아직 분석한 리포트가 없습니다.</p>
                        <p>음식을 선택하고 영양 분석을 시작해보세요!</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="display: grid; gap: 1.5rem;">
                        <c:forEach var="report" items="${reportList}">
                            <div style="border: 2px solid #e0e0e0; border-radius: 15px; padding: 1.5rem; 
                                        transition: all 0.3s; cursor: pointer;"
                                 onclick="location.href='${root}/report?action=view&reportId=${report.id}'"
                                 onmouseover="this.style.borderColor='#667eea'; this.style.boxShadow='0 5px 15px rgba(102, 126, 234, 0.2)'"
                                 onmouseout="this.style.borderColor='#e0e0e0'; this.style.boxShadow='none'">
                                
                                <div style="display: flex; justify-content: space-between; align-items: center;">
                                    <div style="flex: 1;">
                                        <div style="display: flex; align-items: center; gap: 1rem; margin-bottom: 1rem;">
                                            <div style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); 
                                                        color: white; padding: 0.5rem 1rem; border-radius: 10px; 
                                                        font-size: 1.5rem; font-weight: bold;">
                                                <fmt:formatNumber value="${report.score}" pattern="#.#"/>점
                                            </div>
                                            <div>
                                                <p style="color: #888; font-size: 0.9rem; margin-bottom: 0.3rem;">
                                                     ${report.createdAt.year}년 ${report.createdAt.monthValue}월 ${report.createdAt.dayOfMonth}일 
                                                </p>
                                                
                                                <p style="color: #667eea; font-size: 0.85rem; margin-bottom: 0.3rem;">
												    <c:choose>
												        <c:when test="${report.mealType == 'BREAKFAST'}">🌅 아침</c:when>
												        <c:when test="${report.mealType == 'LUNCH'}">☀️ 점심</c:when>
												        <c:when test="${report.mealType == 'DINNER'}">🌙 저녁</c:when>
												        <c:when test="${report.mealType == 'SNACK'}">🍪 간식</c:when>
												        <c:otherwise>식사</c:otherwise>
												    </c:choose>
												</p>
												
                                                <c:choose>
                                                    <c:when test="${report.score >= 90}">
                                                        <p style="color: #667eea; font-weight: 600;">🌟 매우 우수</p>
                                                    </c:when>
                                                    <c:when test="${report.score >= 80}">
                                                        <p style="color: #667eea; font-weight: 600;">😊 우수</p>
                                                    </c:when>
                                                    <c:when test="${report.score >= 70}">
                                                        <p style="color: #667eea; font-weight: 600;">👍 양호</p>
                                                    </c:when>
                                                    <c:when test="${report.score >= 50}">
                                                        <p style="color: #667eea; font-weight: 600;">😐 보통</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p style="color: #f5576c; font-weight: 600;">😢 개선 필요</p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        
                                        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); gap: 1rem;">
                                            <div style="text-align: center; padding: 0.5rem; background: #f5f5f5; border-radius: 8px;">
                                                <p style="color: #888; font-size: 0.8rem;">칼로리</p>
                                                <p style="font-weight: 600; color: #333;">
                                                    <fmt:formatNumber value="${report.kcalSum}" pattern="#"/>kcal
                                                </p>
                                            </div>
                                            <div style="text-align: center; padding: 0.5rem; background: #f5f5f5; border-radius: 8px;">
                                                <p style="color: #888; font-size: 0.8rem;">탄수화물</p>
                                                <p style="font-weight: 600; color: #333;">
                                                    <fmt:formatNumber value="${report.carbSum}" pattern="#.#"/>g
                                                </p>
                                            </div>
                                            <div style="text-align: center; padding: 0.5rem; background: #f5f5f5; border-radius: 8px;">
                                                <p style="color: #888; font-size: 0.8rem;">단백질</p>
                                                <p style="font-weight: 600; color: #333;">
                                                    <fmt:formatNumber value="${report.proteinSum}" pattern="#.#"/>g
                                                </p>
                                            </div>
                                            <div style="text-align: center; padding: 0.5rem; background: #f5f5f5; border-radius: 8px;">
                                                <p style="color: #888; font-size: 0.8rem;">지방</p>
                                                <p style="font-weight: 600; color: #333;">
                                                    <fmt:formatNumber value="${report.fatSum}" pattern="#.#"/>g
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div style="margin-left: 1rem;">
										<button onclick="event.stopPropagation(); 
										        if(confirm('정말 삭제하시겠습니까?')) {
										            const form = document.createElement('form');
										            form.method = 'post';
										            form.action = '${root}/report';
										            form.innerHTML = '<input type=\'hidden\' name=\'action\' value=\'delete\'>' +
										                           '<input type=\'hidden\' name=\'reportId\' value=\'${report.id}\'>';
										            document.body.appendChild(form);
										            form.submit();
										        }" 
										        class="btn btn-secondary" style="padding: 0.5rem 1rem;">
										    삭제
										</button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>

<%@ include file="/view/common/footer.jsp" %>