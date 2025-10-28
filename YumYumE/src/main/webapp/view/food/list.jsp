<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/view/common/header.jsp" %>

<link rel="stylesheet" href="${root}/static/css/food_modal.css">
<link rel="stylesheet" href="${root}/static/css/meal_selector.css">

<main>
    <div class="container" style="max-width: 1400px;">
        <div class="card">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <h2 class="card-title" style="margin-bottom: 0;">음식 검색 및 선택</h2>
            </div>
            
            <form action="${root}/food" method="get" style="margin-bottom: 2rem;">
                <input type="hidden" name="action" value="list">
                
                <div style="display: grid; grid-template-columns: 2fr 1fr auto; gap: 1rem; align-items: end;">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label for="name">음식 이름</label>
                        <input type="text" id="name" name="name" class="form-control" 
                               value="${param.name}" placeholder="음식 이름을 입력하세요">
                    </div>
                    
                    <div class="form-group" style="margin-bottom: 0;">
                        <label for="category">카테고리</label>
                        <select id="category" name="category" class="form-control">
                            <option value="">전체</option>
                            <c:forEach items="${categoryList}" var="category">
                                <option value="${category}" ${param.category == category ? 'selected' : ''}>${category}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary" style="padding: 0.8rem 2rem;">
                        검색
                    </button>
                </div>
            </form>
            
            <c:choose>
                <c:when test="${empty foodList}">
                    <div style="text-align: center; padding: 3rem; color: #888;">
                        <p style="font-size: 1.2rem;">검색 결과가 없습니다.</p>
                        <p>다른 검색어로 시도해보세요.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="overflow-x: auto;">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>음식명</th>
                                    <th>카테고리</th>
                                    <th>기준량</th>
                                    <th>칼로리</th>
                                    <th>탄수화물</th>
                                    <th>단백질</th>
                                    <th>지방</th>
                                    <th>당류</th>
                                    <th>나트륨</th>
                                    <c:if test="${not empty sessionScope.userInfo}">
                                        <th></th>
                                    </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="food" items="${foodList}">
                                    <tr>
                                        <td><strong>${food.name}</strong></td>
                                        <td>${food.category}</td>
                                        <td>${food.standard}</td>
                                        <td>${food.kcal} kcal</td>
                                        <td>${food.carb}g</td>
                                        <td>${food.protein}g</td>
                                        <td>${food.fat}g</td>
                                        <td>${food.sugar}g</td>
                                        <td>${food.natrium}g</td>
                                        <c:if test="${not empty sessionScope.userInfo}">
                                            <td>
                                                <button type="button" onclick="addFood('${food.code}', '${food.name}', '${food.category}', '${food.standard}', ${food.kcal}, ${food.carb}, ${food.protein}, ${food.fat}, ${food.sugar}, ${food.natrium})" 
                                                        class="btn btn-primary" style="padding: 0.4rem 1rem; font-size: 0.9rem;">
                                                    추가
                                                </button>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                    <div style="display: flex; justify-content: center; align-items: center; gap: 0.5rem; margin-top: 2rem;">
                        <c:set var="currentPage" value="${param.pageNum != null ? param.pageNum : 1}" />
                        <c:set var="totalPages" value="${totalPages}" />
                        
                        <c:if test="${currentPage > 1}">
                            <a href="?action=list&name=${param.name}&category=${param.category}&pageNum=${currentPage - 1}" 
                               class="btn btn-secondary" style="padding: 0.5rem 1rem;">이전</a>
                        </c:if>
                        
                        <c:forEach begin="${currentPage > 3 ? currentPage - 2 : 1}" 
                                   end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}" 
                                   var="i">
                            <a href="?action=list&name=${param.name}&category=${param.category}&pageNum=${i}" 
                               class="btn ${i == currentPage ? 'btn-primary' : 'btn-secondary'}" 
                               style="padding: 0.5rem 1rem;">
                                ${i}
                            </a>
                        </c:forEach>
                        
                        <c:if test="${currentPage < totalPages}">
                            <a href="?action=list&name=${param.name}&category=${param.category}&pageNum=${currentPage + 1}" 
                               class="btn btn-secondary" style="padding: 0.5rem 1rem;">다음</a>
                        </c:if>
                    </div>
                    
                    <div style="text-align: center; margin-top: 1rem; color: #888;">
                        총 ${totalSearchCount}개의 음식이 검색되었습니다.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>

<c:if test="${not empty sessionScope.userInfo}">
    <button id="floatingBtn" class="floating-btn" onclick="openModal()">
        🍽️ 나의 식단 <span class="badge" id="floatingCount">0</span>
    </button>
</c:if>

<div id="foodModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 style="margin: 0; color: #333;">🍽️ 나의 식단</h2>
            <span class="close" onclick="closeModal()">&times;</span>
        </div>
        <div class="modal-body">
            
            <div id="mealTypeSelector" class="meal-type-selector">
                <div class="meal-type-btn" data-value="BREAKFAST">아침</div>
                <div class="meal-type-btn active" data-value="LUNCH">점심</div>
                <div class="meal-type-btn" data-value="DINNER">저녁</div>
                <div class="meal-type-btn" data-value="SNACK">간식</div>
            </div>
            
            <div id="selectedFoodsList">
                <p style="text-align: center; color: #888; padding: 2rem;">식단에 담은 음식이 없습니다.</p>
            </div>
            
            <div id="nutritionSummary" style="display: none; background: #f5f5f5; padding: 1.5rem; border-radius: 10px; margin-top: 1.5rem;">
                <h4 style="margin-bottom: 1rem;">영양소 합계</h4>
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(120px, 1fr)); gap: 1rem;">
                    <div style="text-align: center;">
                        <p style="color: #888; font-size: 0.9rem;">칼로리</p>
                        <p style="font-size: 1.3rem; font-weight: bold; color: #667eea;">
                            <span id="totalKcal">0</span> kcal
                        </p>
                    </div>
                    <div style="text-align: center;">
                        <p style="color: #888; font-size: 0.9rem;">탄수화물</p>
                        <p style="font-size: 1.3rem; font-weight: bold; color: #667eea;">
                            <span id="totalCarb">0</span>g
                        </p>
                    </div>
                    <div style="text-align: center;">
                        <p style="color: #888; font-size: 0.9rem;">단백질</p>
                        <p style="font-size: 1.3rem; font-weight: bold; color: #667eea;">
                            <span id="totalProtein">0</span>g
                        </p>
                    </div>
                    <div style="text-align: center;">
                        <p style="color: #888; font-size: 0.9rem;">지방</p>
                        <p style="font-size: 1.3rem; font-weight: bold; color: #667eea;">
                            <span id="totalFat">0</span>g
                        </p>
                    </div>
                    <div style="text-align: center;">
                        <p style="color: #888; font-size: 0.9rem;">당류</p>
                        <p style="font-size: 1.3rem; font-weight: bold; color: #667eea;">
                            <span id="totalSugar">0</span>g
                        </p>
                    </div>
                    <div style="text-align: center;">
                        <p style="color: #888; font-size: 0.9rem;">나트륨</p>
                        <p style="font-size: 1.3rem; font-weight: bold; color: #667eea;">
                            <span id="totalNatrium">0</span>g
                        </p>
                    </div>
                </div>
            </div>
            
            <div style="display: flex; gap: 1rem; justify-content: center; margin-top: 1rem;">
                <button onclick="clearAll()" class="btn btn-secondary" style="padding: 1rem 2rem;">
                    전체 삭제
                </button>
                <button onclick="goToAnalysis()" class="btn btn-primary" style="padding: 1rem 2rem;">
                    영양 분석하기
                </button>
            </div>
        </div>
    </div>
</div>

<div id="inputModal" class="modal">
    <div class="modal-content" style="max-width: 500px;">
        <div class="modal-header">
            <h2 id="inputModalTitle" style="margin: 0; color: #333;">섭취량 입력</h2>
            <span class="close" onclick="closeInputModal()">&times;</span>
        </div>
        <div class="modal-body">
            <p id="inputModalSubtitle" style="color: #888; margin-bottom: 1rem;"></p>
            
            <div class="form-group">
                <label for="amountInput">섭취량 (g 또는 ml)</label>
                <input type="number" id="amountInput" class="form-control" placeholder="숫자를 입력하세요" min="0" step="0.1">
            </div>
            
            <div style="display: flex; gap: 1rem; justify-content: flex-end; margin-top: 2rem;">
                <button onclick="closeInputModal()" class="btn btn-secondary">취소</button>
                <button onclick="confirmAmount()" class="btn btn-primary">확인</button>
            </div>
        </div>
    </div>
</div>

<script>
let selectedFoods = [];
let tempFoodData = null;

window.addEventListener('DOMContentLoaded', function() {
    loadFromLocalStorage();
    
    const mealTypeButtons = document.querySelectorAll('.meal-type-btn');
    mealTypeButtons.forEach(button => {
        button.addEventListener('click', function() {
            mealTypeButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
        });
    });
});

function loadFromLocalStorage() {
    const saved = localStorage.getItem('selectedFoods');
    if (saved) {
        try {
            selectedFoods = JSON.parse(saved);
            updateDisplay();
        } catch (e) {
            console.error('식단 데이터 로드 실패:', e);
            selectedFoods = [];
        }
    }
}

function saveToLocalStorage() {
    localStorage.setItem('selectedFoods', JSON.stringify(selectedFoods));
}

function addFood(code, name, category, standard, kcal, carb, protein, fat, sugar, natrium) {
    if (selectedFoods.some(f => f.code === code)) {
        alert('이미 추가된 음식입니다.');
        return;
    }
    
    tempFoodData = {
        code, name, category, standard, kcal, carb, protein, fat, sugar, natrium
    };
    
    openInputModal(name, standard);
}

function openInputModal(name, standard) {
    document.getElementById('inputModalTitle').textContent = name;
    document.getElementById('inputModalSubtitle').textContent = '기준량: ' + standard;
    document.getElementById('amountInput').value = '100';
    document.getElementById('amountInput').focus();
    document.getElementById('inputModal').style.display = 'block';
    
    document.getElementById('amountInput').onkeypress = function(e) {
        if (e.key === 'Enter') {
            confirmAmount();
        }
    };
}

function closeInputModal() {
    document.getElementById('inputModal').style.display = 'none';
    tempFoodData = null;
}

function confirmAmount() {
    const inputAmount = parseFloat(document.getElementById('amountInput').value);
    
    if (isNaN(inputAmount) || inputAmount <= 0) {
        alert('올바른 숫자를 입력해주세요.');
        return;
    }
    
    const ratio = inputAmount / 100;
    
    selectedFoods.push({
        code: tempFoodData.code,
        name: tempFoodData.name,
        category: tempFoodData.category,
        standard: inputAmount,
        kcal: parseFloat((tempFoodData.kcal * ratio).toFixed(1)) || 0,
        carb: parseFloat((tempFoodData.carb * ratio).toFixed(1)) || 0,
        protein: parseFloat((tempFoodData.protein * ratio).toFixed(1)) || 0,
        fat: parseFloat((tempFoodData.fat * ratio).toFixed(1)) || 0,
        sugar: parseFloat((tempFoodData.sugar * ratio).toFixed(1)) || 0,
        natrium: parseFloat((tempFoodData.natrium * ratio).toFixed(1)) || 0
    });
    
    saveToLocalStorage();
    updateDisplay();
    closeInputModal();
    alert(tempFoodData.name + ' ' + inputAmount + 'g (또는 ml)이(가) 추가되었습니다.');
}

function removeFood(code) {
    selectedFoods = selectedFoods.filter(f => f.code !== code);
    saveToLocalStorage();
    updateDisplay();
}

function clearAll() {
    if (selectedFoods.length === 0) {
        alert('식단에 담은 음식이 없습니다.');
        return;
    }
    
    if (confirm('모든 음식을 삭제하시겠습니까?')) {
        selectedFoods = [];
        saveToLocalStorage();
        updateDisplay();
    }
}

function updateDisplay() {
    updateFloatingButton();
    updateModalContent();
    updateTotals();
}

function updateFloatingButton() {
    const btn = document.getElementById('floatingBtn');
    const count = document.getElementById('floatingCount');
    
    if (btn && count) {
        count.textContent = selectedFoods.length;
        btn.style.display = 'block'; 
    }
}

function updateModalContent() {
    const container = document.getElementById('selectedFoodsList');
    const summary = document.getElementById('nutritionSummary');
    const mealSelector = document.getElementById('mealTypeSelector');
    
    if (!container) return;
    
    if (selectedFoods.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #888; padding: 2rem;">식단에 담은 음식이 없습니다.</p>';
        if (summary) summary.style.display = 'none';
        if (mealSelector) mealSelector.style.display = 'none';
        return;
    }
    
    if (summary) summary.style.display = 'block';
    if (mealSelector) mealSelector.style.display = 'flex';
    
    let html = '<table class="table"><thead><tr>' +
        '<th>음식명</th><th>카테고리</th><th>기준량<br>(g 또는 ml)</th><th>칼로리</th><th>탄수화물</th><th>단백질</th><th>지방</th><th>당류</th><th>나트륨</th><th></th>' +
        '</tr></thead><tbody>';
    
    selectedFoods.forEach(food => {
        html += '<tr>' +
            '<td><strong>' + food.name + '</strong></td>' +
            '<td>' + food.category + '</td>' +
            '<td>' + food.standard + '</td>' +
            '<td>' + food.kcal.toFixed(1) + ' kcal</td>' +
            '<td>' + food.carb.toFixed(1) + 'g</td>' +
            '<td>' + food.protein.toFixed(1) + 'g</td>' +
            '<td>' + food.fat.toFixed(1) + 'g</td>' +
            '<td>' + food.sugar.toFixed(1) + 'g</td>' +
            '<td>' + food.natrium.toFixed(1) + 'g</td>' +
            '<td><button type="button" class="btn btn-secondary" style="padding: 0.4rem 1rem;" onclick="removeFood(\'' + food.code + '\')">삭제</button></td>' +
            '</tr>';
    });
    
    html += '</tbody></table>';
    container.innerHTML = html;
}

function updateTotals() {
    const totals = selectedFoods.reduce((acc, food) => ({
        kcal: acc.kcal + food.kcal,
        carb: acc.carb + food.carb,
        protein: acc.protein + food.protein,
        fat: acc.fat + food.fat,
        sugar: acc.sugar + food.sugar,
        natrium: acc.natrium + food.natrium
    }), { kcal: 0, carb: 0, protein: 0, fat: 0, sugar: 0, natrium: 0 });
    
    const elements = {
        'totalKcal': totals.kcal,
        'totalCarb': totals.carb,
        'totalProtein': totals.protein,
        'totalFat': totals.fat,
        'totalSugar': totals.sugar,
        'totalNatrium': totals.natrium
    };
    
    for (let id in elements) {
        const el = document.getElementById(id);
        if (el) {
            el.textContent = elements[id].toFixed(1);
        }
    }
}

function openModal() {
    document.getElementById('foodModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('foodModal').style.display = 'none';
}

function goToAnalysis() {
    if (selectedFoods.length === 0) {
        alert('음식을 선택해주세요.');
        return;
    }
    
    const totals = selectedFoods.reduce((acc, food) => ({
        kcal: acc.kcal + food.kcal,
        carb: acc.carb + food.carb,
        protein: acc.protein + food.protein,
        fat: acc.fat + food.fat,
        sugar: acc.sugar + food.sugar,
        natrium: acc.natrium + food.natrium
    }), { kcal: 0, carb: 0, protein: 0, fat: 0, sugar: 0, natrium: 0 });
    
    const activeMealBtn = document.querySelector('.meal-type-btn.active');
    const mealType = activeMealBtn ? activeMealBtn.dataset.value : 'LUNCH';
    
    const form = document.createElement('form');
    form.method = 'post';
    form.action = '${root}/report';
    
    form.innerHTML = 
        '<input type="hidden" name="action" value="create">' +
        '<input type="hidden" name="meal_type" value="' + mealType + '">' +
        '<input type="hidden" name="carb_sum" value="' + totals.carb.toFixed(1) + '">' +
        '<input type="hidden" name="protein_sum" value="' + totals.protein.toFixed(1) + '">' +
        '<input type="hidden" name="fat_sum" value="' + totals.fat.toFixed(1) + '">' +
        '<input type="hidden" name="kcal_sum" value="' + totals.kcal.toFixed(1) + '">' +
        '<input type="hidden" name="sugar_sum" value="' + totals.sugar.toFixed(1) + '">' +
        '<input type="hidden" name="natrium_sum" value="' + totals.natrium.toFixed(1) + '">';
    
    selectedFoods.forEach(food => {
        if (food && food.code && food.standard > 0) { // 유효한 데이터만 전송
            // 음식 코드 input
            const codeInput = document.createElement('input');
            codeInput.type = 'hidden';
            codeInput.name = 'food_code'; // 모든 코드에 동일한 name 부여 (배열로 받기 위함)
            codeInput.value = food.code;
            form.appendChild(codeInput);

            // 음식 무게(섭취량) input
            const weightInput = document.createElement('input');
            weightInput.type = 'hidden';
            weightInput.name = 'food_weight'; // 모든 무게에 동일한 name 부여 (배열로 받기 위함)
            weightInput.value = food.standard;
            form.appendChild(weightInput);
            
        	// 음식 이름 input
            const nameInput = document.createElement('input');
            nameInput.type = 'hidden';
            nameInput.name = 'food_name'; // 모든 이름에 동일한 name 부여
            nameInput.value = food.name;
            form.appendChild(nameInput);
        }
    });
        
    localStorage.removeItem('selectedFoods');
    
    document.body.appendChild(form);
    form.submit();
}

window.onclick = function(event) {
    const foodModal = document.getElementById('foodModal');
    const inputModal = document.getElementById('inputModal');
    
    if (event.target === foodModal) {
        closeModal();
    }
    if (event.target === inputModal) {
        closeInputModal();
    }
}
</script>

<%@ include file="/view/common/footer.jsp" %>