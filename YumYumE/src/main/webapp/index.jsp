<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/view/common/header.jsp"%>

<main>
	<div class="container">
		<!-- 히어로 섹션 -->
		<div class="card" style="text-align: center; padding: 4rem 2rem;">
			<h1 style="font-size: 3rem; margin-bottom: 1rem; color: #667eea;">
				🍽️ YumYumE</h1>
			<p style="font-size: 1.5rem; color: #333; margin-bottom: 2rem;">
				당신의 영양 밸런스를 관리해드립니다</p>
			<p style="font-size: 1.1rem; color: #666; margin-bottom: 3rem;">
				음식을 선택하고 영양 점수를 확인하세요</p>

			<div style="display: flex; gap: 1rem; justify-content: center;">
				<a href="${root}/food?action=list" class="btn btn-primary"
					style="font-size: 1.2rem; padding: 1rem 2rem;"> 식단 분석하기 </a>
			</div>
		</div>

		<!-- 기능 소개 -->
		<div
			style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem; margin-top: 3rem;">

			<%-- '리포트 관리' 카드 --%>
			<a
				href="${root}/report?action=list&userId=${sessionScope.userInfo.id}"
				style="text-decoration: none; color: inherit; transition: all 0.3s ease;"
				onmouseover="this.style.transform='translateY(-20px)'; this.style.boxShadow='0 20px 60px rgba(102, 126, 234, 0.4)';"
				onmouseout="this.style.transform='translateY(0)'; this.style.boxShadow='none';">
				<div class="card" style="text-align: center; height: 100%;">
					<div style="font-size: 3rem; margin-bottom: 1rem;">📝</div>
					<h3 style="margin-bottom: 1rem; color: #333;">리포트 관리</h3>
					<p style="color: #666;">나의 식습관 리포트를 저장하고 관리하세요</p>
				</div>
			</a>

			<%-- '영양 분석' 카드 --%>
			<a href="${root}/food?action=list"
				style="text-decoration: none; color: inherit; transition: all 0.3s ease;"
				onmouseover="this.style.transform='translateY(-20px)'; this.style.boxShadow='0 20px 60px rgba(102, 126, 234, 0.4)';"
				onmouseout="this.style.transform='translateY(0)'; this.style.boxShadow='none';">
				<div class="card" style="text-align: center; height: 100%;">
					<div style="font-size: 3rem; margin-bottom: 1rem;">📊</div>
					<h3 style="margin-bottom: 1rem; color: #333;">영양 분석</h3>
					<p style="color: #666;">먹은 음식의 영양소를 분석하고 점수를 확인하세요</p>
				</div>
			</a>

			<%-- '마이페이지' 카드 --%>
			<c:if test="${not empty sessionScope.userInfo}">
				<a href="${root}/user?action=mypage&id=${sessionScope.userInfo.id}"
					style="text-decoration: none; color: inherit; transition: all 0.3s ease;"
					onmouseover="this.style.transform='translateY(-20px)'; this.style.boxShadow='0 20px 60px rgba(102, 126, 234, 0.4)';"
					onmouseout="this.style.transform='translateY(0)'; this.style.boxShadow='none';">
					<div class="card" style="text-align: center; height: 100%;">
						<div style="font-size: 3rem; margin-bottom: 1rem;">🔍</div>
						<h3 style="margin-bottom: 1rem; color: #333;">내 정보</h3>
						<p style="color: #666;">내 정보와 일일 권장 영양소를 확인하세요</p>
					</div>
				</a>
			</c:if>
		</div>

		<!-- 사용자별 정보 -->
		<c:if test="${not empty sessionScope.userInfo}">
			<div class="card" style="margin-top: 3rem;">
				<h2 class="card-title">나의 권장 영양 정보</h2>
				<div
					style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1.5rem;">
					<div
						style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
						<p style="color: #888; margin-bottom: 0.5rem;">칼로리</p>
						<p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
							${sessionScope.userInfo.stdKcal} kcal</p>
					</div>
					<div
						style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
						<p style="color: #888; margin-bottom: 0.5rem;">탄수화물</p>
						<p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
							${sessionScope.userInfo.stdCarb}g</p>
					</div>
					<div
						style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
						<p style="color: #888; margin-bottom: 0.5rem;">단백질</p>
						<p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
							${sessionScope.userInfo.stdProtein}g</p>
					</div>
					<div
						style="text-align: center; padding: 1rem; background: #f5f5f5; border-radius: 10px;">
						<p style="color: #888; margin-bottom: 0.5rem;">지방</p>
						<p style="font-size: 1.5rem; font-weight: bold; color: #667eea;">
							${sessionScope.userInfo.stdFat}g</p>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</main>

<%@ include file="/view/common/footer.jsp"%>