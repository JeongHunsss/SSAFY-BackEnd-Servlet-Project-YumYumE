<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/view/common/header.jsp" %>

<main>
    <div class="container">
        <div class="card" style="max-width: 800px; margin: 2rem auto;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <h2 class="card-title" style="margin-bottom: 0;">${foodDto.name}</h2>
                <button onclick="history.back()" class="btn btn-secondary">돌아가기</button>
            </div>
            
            <!-- 기본 정보 -->
            <div style="background: #f5f5f5; padding: 1.5rem; border-radius: 10px; margin-bottom: 2rem;">
                <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 1rem;">
                    <div>
                        <p style="color: #888; margin-bottom: 0.3rem;">음식 코드</p>
                        <p style="font-weight: 600; font-size: 1.1rem;">${foodDto.code}</p>
                    </div>
                    <div>
                        <p style="color: #888; margin-bottom: 0.3rem;">카테고리</p>
                        <p style="font-weight: 600; font-size: 1.1rem;">${foodDto.category}</p>
                    </div>
                    <div>
                        <p style="color: #888; margin-bottom: 0.3rem;">기준량</p>
                        <p style="font-weight: 600; font-size: 1.1rem;">${foodDto.standard}</p>
                    </div>
                    <div>
                        <p style="color: #888; margin-bottom: 0.3rem;">칼로리</p>
                        <p style="font-weight: 600; font-size: 1.1rem; color: #667eea;">${foodDto.kcal} kcal</p>
                    </div>
                </div>
            </div>
            
            <!-- 영양 정보 -->
            <h3 style="margin-bottom: 1.5rem; color: #333;">영양 정보</h3>
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 1.5rem;">
                <div style="text-align: center; padding: 1.5rem; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 10px; color: white;">
                    <p style="margin-bottom: 0.5rem; font-size: 0.9rem;">탄수화물</p>
                    <p style="font-size: 1.8rem; font-weight: bold;">${foodDto.carb}g</p>
                </div>
                
                <div style="text-align: center; padding: 1.5rem; background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); border-radius: 10px; color: white;">
                    <p style="margin-bottom: 0.5rem; font-size: 0.9rem;">단백질</p>
                    <p style="font-size: 1.8rem; font-weight: bold;">${foodDto.protein}g</p>
                </div>
                
                <div style="text-align: center; padding: 1.5rem; background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); border-radius: 10px; color: white;">
                    <p style="margin-bottom: 0.5rem; font-size: 0.9rem;">지방</p>
                    <p style="font-size: 1.8rem; font-weight: bold;">${foodDto.fat}g</p>
                </div>
                
                <div style="text-align: center; padding: 1.5rem; background: linear-gradient(135deg, #fa709a 0%, #fee140 100%); border-radius: 10px; color: white;">
                    <p style="margin-bottom: 0.5rem; font-size: 0.9rem;">당류</p>
                    <p style="font-size: 1.8rem; font-weight: bold;">${foodDto.sugar}g</p>
                </div>
                
                <div style="text-align: center; padding: 1.5rem; background: linear-gradient(135deg, #30cfd0 0%, #330867 100%); border-radius: 10px; color: white;">
                    <p style="margin-bottom: 0.5rem; font-size: 0.9rem;">나트륨</p>
                    <p style="font-size: 1.8rem; font-weight: bold;">${foodDto.natrium}g</p>
                </div>
            </div>
        </div>
    </div>
</main>

<%@ include file="/view/common/footer.jsp" %>