<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/view/common/header.jsp" %>

<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="/view/user?action=login"/>
</c:if>

<main>
    <div class="container">
        <div class="card" style="max-width: 1000px; margin: 2rem auto;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <h2 class="card-title" style="margin-bottom: 0;">영양 분석 결과</h2>
                <div style="display: flex; gap: 0.5rem;">
                    <a href="${root}/report?action=list&userId=${sessionScope.userInfo.id}"
                       class="btn btn-secondary">목록</a>
                    <a href="${root}/food?action=list"
                       class="btn btn-primary">새 분석</a>
                </div>
            </div>

            <div style="text-align: center; margin-bottom: 1rem;">
			    <span style="display: inline-block; padding: 0.5rem 1.5rem; background: white; color: #667eea;
			                 border-radius: 25px; font-weight: 600; font-size: 1.1rem; border: 1px solid #eee;">
			        <c:choose>
			            <c:when test="${report.mealType == 'BREAKFAST'}">🌅 아침</c:when>
			            <c:when test="${report.mealType == 'LUNCH'}">☀️ 점심</c:when>
			            <c:when test="${report.mealType == 'DINNER'}">🌙 저녁</c:when>
			            <c:when test="${report.mealType == 'SNACK'}">🍪 간식</c:when>
			            <c:otherwise>식사</c:otherwise>
			        </c:choose>
			    </span>
			</div>

            <div style="text-align: center; padding: 3rem; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 20px; color: white; margin-bottom: 2rem;">
                <p style="font-size: 1.2rem; margin-bottom: 0.5rem; opacity: 0.9;">영양 점수</p>
                <p style="font-size: 4rem; font-weight: bold; margin: 0;">
                    <fmt:formatNumber value="${report.score}" pattern="#.#"/>
                </p>
                <p style="font-size: 1.5rem; margin-top: 0.5rem; opacity: 0.9;">/ 100점</p>

                <%-- AI 코멘트가 있으면 AI 코멘트를 표시 --%>
                <c:if test="${not empty report.aiComment }">
                    <div style="padding: 1rem 0 0 0; background: none; border-radius: 10px; margin-top: 1.5rem;
                                font-size: 1.1rem; line-height: 1.6; border-top: 1px solid rgba(255, 255, 255, 0.3);">
                        🤖 AI 코멘트 🤖 <br> ${report.aiComment}
                    </div>
                </c:if>

                <%-- AI 코멘트가 없는 경우 --%>
                <c:if test="${empty report.aiComment }" >
                    <div style="padding: 1rem 0 0 0; background: none; border-radius: 10px; margin-top: 1.5rem;
                                font-size: 1.1rem; line-height: 1.6; border-top: 1px solid rgba(255, 255, 255, 0.3);">
                        🤖 <br> AI 코멘트가 존재하지 않습니다.
                    </div>
                </c:if>
            </div> <%-- 점수 표시 div 끝 --%>
            
            <%-- 섭취한 음식 목록 섹션 --%>
			<div style="margin-top: 3rem; margin-bottom: 3rem; border-top: 2px solid #e0e0e0; padding-top: 2rem;">
                <%-- 토글 버튼 --%>
                <div style="display: flex; justify-content: start; align-items: center; cursor: pointer; margin-bottom: 1.5rem;"
                     onclick="toggleFoodList()">
                    <h3 style="color: #333; margin: 0;">🍽️ 섭취한 음식 목록</h3>
                    <span id="toggleIcon" style="margin-left: 1rem; font-size: 1.5rem; color: #667eea; transition: transform 0.6s;">▼</span>
                </div>

                <%-- 토글될 음식 목록 (기본 숨김) --%>
                <div id="foodListContainer" style="display: none;">
                    <c:choose>
                        <c:when test="${not empty foodList}">
                            <div style="display: grid; gap: 0.8rem; max-width: 600px; margin: 0 auto;">
                                <c:forEach var="foodItem" items="${foodList}" varStatus="loop">
                                    <div style="display: flex; justify-content: space-between; align-items: center;
                                                padding: 1rem 1.5rem; border-radius: 12px;
                                                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                                                background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);">
                                        <span style="font-weight: 600; color: #ffffff; font-size: 1.1rem;">
                                            ${foodItem.foodName}
                                        </span>
                                        <span style="font-weight: 600; color: rgb(74, 44, 109); font-size: 1.05rem;
                                                   background: rgba(255, 255, 255, 0.85); padding: 0.2rem 0.6rem; border-radius: 8px;">
                                            <fmt:formatNumber value="${foodItem.foodWeight}" pattern="#.#"/> g
                                        </span>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p style="text-align: center; color: #888; padding: 1rem;">섭취한 음식 목록 정보가 없습니다.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <%-- 섭취한 음식 목록 끝 --%>

            <h3 style="margin-bottom: 1.5rem; color: #333;">
			    영양소 비교 (섭취량 /
			    <c:choose>
			        <c:when test="${report.mealType == 'SNACK'}">간식 권장량</c:when>
			        <c:otherwise>한 끼 권장량</c:otherwise>
			    </c:choose>
			    )
			</h3>

            <div style="margin-bottom: 2rem;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                    <span style="font-weight: 600;">칼로리</span>
                    <span style="color: #667eea; font-weight: 600;">
                        <fmt:formatNumber value="${report.kcalSum}" pattern="#.#"/> /
                        <fmt:formatNumber value="${report.userDto.stdKcal}" pattern="#.#"/> kcal
                    </span>
                </div>
                <div style="width: 100%; height: 30px; background: #e0e0e0; border-radius: 15px; overflow: hidden;">
                    <div style="width: ${report.kcalSum / report.userDto.stdKcal * 100 > 100 ? 100 : (report.kcalSum / report.userDto.stdKcal * 100)}%;
                                height: 100%; background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
                                transition: width 0.5s;">
                    </div>
                </div>
                <p style="text-align: center; margin-top: 0.5rem; color: #888; font-size: 0.9rem;">
                    <fmt:formatNumber value="${report.kcalSum / report.userDto.stdKcal * 100}" pattern="#.#"/>%
                </p>
            </div>

            <div style="margin-bottom: 2rem;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                    <span style="font-weight: 600;">탄수화물</span>
                    <span style="color: #667eea; font-weight: 600;">
                        <fmt:formatNumber value="${report.carbSum}" pattern="#.#"/> /
                        <fmt:formatNumber value="${report.userDto.stdCarb}" pattern="#.#"/> g
                    </span>
                </div>
                <div style="width: 100%; height: 30px; background: #e0e0e0; border-radius: 15px; overflow: hidden;">
                    <div style="width: ${report.carbSum / report.userDto.stdCarb * 100 > 100 ? 100 : (report.carbSum / report.userDto.stdCarb * 100)}%;
                                height: 100%; background: linear-gradient(90deg, #f093fb 0%, #f5576c 100%);
                                transition: width 0.5s;">
                    </div>
                </div>
                <p style="text-align: center; margin-top: 0.5rem; color: #888; font-size: 0.9rem;">
                    <fmt:formatNumber value="${report.carbSum / report.userDto.stdCarb * 100}" pattern="#.#"/>%
                </p>
            </div>

            <div style="margin-bottom: 2rem;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                    <span style="font-weight: 600;">단백질</span>
                    <span style="color: #667eea; font-weight: 600;">
                        <fmt:formatNumber value="${report.proteinSum}" pattern="#.#"/> /
                        <fmt:formatNumber value="${report.userDto.stdProtein}" pattern="#.#"/> g
                    </span>
                </div>
                <div style="width: 100%; height: 30px; background: #e0e0e0; border-radius: 15px; overflow: hidden;">
                    <div style="width: ${report.proteinSum / report.userDto.stdProtein * 100 > 100 ? 100 : (report.proteinSum / report.userDto.stdProtein * 100)}%;
                                height: 100%; background: linear-gradient(90deg, #4facfe 0%, #00f2fe 100%);
                                transition: width 0.5s;">
                    </div>
                </div>
                <p style="text-align: center; margin-top: 0.5rem; color: #888; font-size: 0.9rem;">
                    <fmt:formatNumber value="${report.proteinSum / report.userDto.stdProtein * 100}" pattern="#.#"/>%
                </p>
            </div>

            <div style="margin-bottom: 2rem;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                    <span style="font-weight: 600;">지방</span>
                    <span style="color: #667eea; font-weight: 600;">
                        <fmt:formatNumber value="${report.fatSum}" pattern="#.#"/> /
                        <fmt:formatNumber value="${report.userDto.stdFat}" pattern="#.#"/> g
                    </span>
                </div>
                <div style="width: 100%; height: 30px; background: #e0e0e0; border-radius: 15px; overflow: hidden;">
                    <div style="width: ${report.fatSum / report.userDto.stdFat * 100 > 100 ? 100 : (report.fatSum / report.userDto.stdFat * 100)}%;
                                height: 100%; background: linear-gradient(90deg, #fa709a 0%, #fee140 100%);
                                transition: width 0.5s;">
                    </div>
                </div>
                <p style="text-align: center; margin-top: 0.5rem; color: #888; font-size: 0.9rem;">
                    <fmt:formatNumber value="${report.fatSum / report.userDto.stdFat * 100}" pattern="#.#"/>%
                </p>
            </div>

            <div style="margin-bottom: 2rem;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                    <span style="font-weight: 600;">당류</span>
                    <span style="color: #667eea; font-weight: 600;">
                        <fmt:formatNumber value="${report.sugarSum}" pattern="#.#"/> /
                        <fmt:formatNumber value="${report.userDto.stdSugar}" pattern="#.#"/> g
                    </span>
                </div>
                <div style="width: 100%; height: 30px; background: #e0e0e0; border-radius: 15px; overflow: hidden;">
                    <div style="width: ${report.sugarSum / report.userDto.stdSugar * 100 > 100 ? 100 : (report.sugarSum / report.userDto.stdSugar * 100)}%;
                                height: 100%; background: linear-gradient(90deg, #ff6b9d 0%, #c06c84 100%);
                                transition: width 0.5s;">
                    </div>
                </div>
                <p style="text-align: center; margin-top: 0.5rem; color: #888; font-size: 0.9rem;">
                    <fmt:formatNumber value="${report.sugarSum / report.userDto.stdSugar * 100}" pattern="#.#"/>%
                </p>
            </div>

            <div style="margin-bottom: 2rem;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                    <span style="font-weight: 600;">나트륨</span>
                    <span style="color: #667eea; font-weight: 600;">
                        <fmt:formatNumber value="${report.natriumSum}" pattern="#.#"/> /
                        <fmt:formatNumber value="${report.userDto.stdNatrium}" pattern="#.#"/> g
                    </span>
                </div>
                <div style="width: 100%; height: 30px; background: #e0e0e0; border-radius: 15px; overflow: hidden;">
                    <div style="width: ${report.natriumSum / report.userDto.stdNatrium * 100 > 100 ? 100 : (report.natriumSum / report.userDto.stdNatrium * 100)}%;
                                height: 100%; background: linear-gradient(90deg, #30cfd0 0%, #330867 100%);
                                transition: width 0.5s;">
                    </div>
                </div>
                <p style="text-align: center; margin-top: 0.5rem; color: #888; font-size: 0.9rem;">
                    <fmt:formatNumber value="${report.natriumSum / report.userDto.stdNatrium * 100}" pattern="#.#"/>%
                </p>
            </div>

            <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 2rem; border-top: 2px solid #e0e0e0;">
                <p style="color: #888;">
                    분석 날짜: ${report.createdAt.year}년 ${report.createdAt.monthValue}월 ${report.createdAt.dayOfMonth}일
                </p>
                <form action="${root}/report" method="post"
                      onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="reportId" value="${report.id}">
                    <button type="submit" class="btn btn-secondary">삭제</button>
                </form>
            </div>
        </div>
    </div>
</main>

<%-- 토글 JavaScript --%>
<script>
    function toggleFoodList() {
        const listContainer = document.getElementById('foodListContainer');
        const icon = document.getElementById('toggleIcon');
        if (listContainer.style.display === 'none') {
            listContainer.style.display = 'block'; // 또는 'grid'
            icon.style.transform = 'rotate(180deg)'; // 아이콘 회전
        } else {
            listContainer.style.display = 'none';
            icon.style.transform = 'rotate(0deg)'; // 아이콘 원래대로
        }
    }
</script>

<%@ include file="/view/common/footer.jsp" %>