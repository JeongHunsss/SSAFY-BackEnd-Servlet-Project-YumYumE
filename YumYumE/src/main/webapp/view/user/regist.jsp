<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/view/common/header.jsp" %>

<main>
    <div class="container">
        <div class="card" style="max-width: 600px; margin: 2rem auto;">
            <h2 class="card-title" style="text-align: center;">회원가입</h2>
            
            <form action="${root}/user" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="regist">
                <input type="hidden" name="role" value="USER">
                
                <!-- 아이디 -->
                <div class="form-group">
                    <label for="id">아이디 *</label>
                    <div style="display: flex; gap: 0.5rem;">
                        <input type="text" id="id" name="id" class="form-control" 
                               value="${checkedId != null ? checkedId : param.id}" 
                               placeholder="아이디를 입력하세요 (4~20자)"
                               minlength="4" maxlength="20" required>
                        <button type="button" onclick="checkDuplicate()" class="btn btn-secondary" 
                                style="white-space: nowrap;">중복확인</button>
                    </div>
                    <c:if test="${idCheckResult.equals('duplicate')}">
                        <span style="color: red; font-size: 0.9rem;">이미 사용중인 아이디입니다.</span>
                    </c:if>
                    <c:if test="${idCheckResult.equals('ok')}">
                        <span style="color: green; font-size: 0.9rem;">사용 가능한 아이디입니다.</span>
                    </c:if>
                </div>
                
                <!-- 비밀번호 -->
                <div class="form-group">
                    <label for="password">비밀번호 *</label>
                    <input type="password" id="password" name="password" class="form-control" 
                           value="${param.password}" placeholder="비밀번호를 입력하세요 (8~20자)"
                           minlength="8" maxlength="20" required>
                </div>
                
                <div class="form-group">
                    <label for="password_confirm">비밀번호 확인 *</label>
                    <input type="password" id="password_confirm" name="password_confirm" class="form-control" 
                           value="${param.password_confirm}" placeholder="비밀번호를 다시 입력하세요" required>
                </div>
                
                <!-- 이름 -->
                <div class="form-group">
                    <label for="name">이름 *</label>
                    <input type="text" id="name" name="name" class="form-control"
                           value="${param.name}" placeholder="이름을 입력하세요" required>
                </div>
                
                <!-- 이메일 -->
                <div class="form-group">
                    <label for="email">이메일 *</label>
                    <div style="display: flex; gap: 0.5rem; align-items: center;">
                        <input type="text" id="email" name="email" class="form-control" 
                               value="${param.email}" placeholder="이메일" required>
                        <span>@</span>
                        <input type="text" id="email_domain" name="email_domain" class="form-control" 
                               value="${param.email_domain}" placeholder="도메인" required>
                    </div>
                </div>
                
                <!-- 나이 -->
                <div class="form-group">
                    <label for="age">나이 *</label>
                    <input type="number" id="age" name="age" class="form-control" 
                           value="${param.age}" placeholder="나이를 입력하세요" min="1" max="150" required>
                </div>
                
                <!-- 성별 -->
                <div class="form-group">
                    <label>성별 *</label>
                    <div style="display: flex; gap: 2rem;">
                        <label style="cursor: pointer;">
                            <input type="radio" name="gender" value="남" 
                                   ${param.gender == '남' ? 'checked' : ''} required>
                            남성
                        </label>
                        <label style="cursor: pointer;">
                            <input type="radio" name="gender" value="여" 
                                   ${param.gender == '여' ? 'checked' : ''} required>
                            여성
                        </label>
                    </div>
                </div>
                
                <!-- 키 -->
                <div class="form-group">
                    <label for="height">키 (cm) *</label>
                    <input type="number" id="height" name="height" class="form-control" 
                           value="${param.height}" placeholder="키를 입력하세요" 
                           min="80" max="250" step="0.1" required>
                </div>
                
                <!-- 몸무게 -->
                <div class="form-group">
                    <label for="weight">몸무게 (kg) *</label>
                    <input type="number" id="weight" name="weight" class="form-control" 
                           value="${param.weight}" placeholder="몸무게를 입력하세요" 
                           min="20" max="300" step="0.1" required>
                </div>
                
                <!-- 활동 수준 -->
                <div class="form-group">
                    <label for="activity_level">활동 수준 *</label>
                    <select id="activity_level" name="activity_level" class="form-control" required>
                        <option value="">활동 수준을 선택하세요</option>
                        <option value="SEDENTARY" ${param.activity_level == 'SEDENTARY' ? 'selected' : ''}>
                            거의 안함 (운동 안함)
                        </option>
                        <option value="LIGHT" ${param.activity_level == 'LIGHT' ? 'selected' : ''}>
                            가벼운 활동 (주 1~3회)
                        </option>
                        <option value="MODERATE" ${param.activity_level == 'MODERATE' ? 'selected' : ''}>
                            보통 활동 (주 3~5회)
                        </option>
                        <option value="ACTIVE" ${param.activity_level == 'ACTIVE' ? 'selected' : ''}>
                            활발한 활동 (주 6~7회)
                        </option>
                        <option value="VERY_ACTIVE" ${param.activity_level == 'VERY_ACTIVE' ? 'selected' : ''}>
                            매우 활발 (육체노동 or 선수급)
                        </option>
                    </select>
                </div>
                
                <button type="submit" class="btn btn-primary" 
                        style="width: 100%; padding: 1rem; font-size: 1.1rem; margin-top: 1rem;">
                    회원가입
                </button>
            </form>
            
            <div style="text-align: center; margin-top: 1.5rem; color: #888;">
                이미 계정이 있으신가요? 
                <a href="${root}/user?action=login" 
                   style="color: #667eea; text-decoration: none; font-weight: 600;">
                    로그인
                </a>
            </div>
        </div>
    </div>
</main>

<script>
let idChecked = ${idCheckResult == 'ok' ? 'true' : 'false'};
let checkedId = "${checkedId}";

function checkDuplicate() {
    const id = document.getElementById('id').value;
    if (!id) {
        alert('아이디를 입력해주세요.');
        return;
    }
    
    // 현재 입력된 모든 값을 유지하면서 중복 체크
    const form = document.createElement('form');
    form.method = 'get';
    form.action = '${root}/user';
    
    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'idduplicate';
    form.appendChild(actionInput);
    
    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'id';
    idInput.value = id;
    form.appendChild(idInput);
    
    // 다른 입력 필드들도 함께 전송
    ['name', 'password', 'password_confirm', 'email', 'email_domain', 'age', 'height', 'weight', 'activity_level'].forEach(name => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = document.getElementsByName(name)[0]?.value || '';
        form.appendChild(input);
    });
    
    const gender = document.querySelector('input[name="gender"]:checked');
    if (gender) {
        const genderInput = document.createElement('input');
        genderInput.type = 'hidden';
        genderInput.name = 'gender';
        genderInput.value = gender.value;
        form.appendChild(genderInput);
    }
    
    document.body.appendChild(form);
    form.submit();
}

function validateForm() {
    const id = document.getElementById('id').value;
    
    if (!idChecked || id !== checkedId) {
        alert('아이디 중복확인을 해주세요.');
        return false;
    }
    
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password_confirm').value;
    
    if (password !== passwordConfirm) {
        alert('비밀번호가 일치하지 않습니다.');
        return false;
    }
    
    return true;
}

// 아이디가 변경되면 중복 체크 상태 초기화
document.getElementById('id').addEventListener('input', function() {
    if (this.value !== checkedId) {
        idChecked = false;
    }
});
</script>

<%@ include file="/view/common/footer.jsp" %>