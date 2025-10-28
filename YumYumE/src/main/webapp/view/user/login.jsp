<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/view/common/header.jsp" %>

<main>
    <div class="container">
        <div class="card" style="max-width: 500px; margin: 3rem auto;">
            <h2 class="card-title" style="text-align: center;">로그인</h2>
            
            <form action="${root}/user" method="post">
                <input type="hidden" name="action" value="login">
                
                <div class="form-group">
                    <label for="id">아이디</label>
                    <input type="text" id="id" name="id" class="form-control" 
                           value="${cookie.rememberme.value}" required 
                           placeholder="아이디를 입력하세요">
                </div>
                
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" 
                           class="form-control" required placeholder="비밀번호를 입력하세요">
                </div>
                
                <div class="form-group">
                    <label style="display: flex; align-items: center; cursor: pointer;">
                        <input type="checkbox" name="rememberme" value="rememberme" 
                               ${not empty cookie.rememberme ? 'checked' : ''} 
                               style="margin-right: 0.5rem;">
                        아이디 기억하기
                    </label>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%; padding: 1rem; font-size: 1.1rem;">
                    로그인
                </button>
            </form>
            
            <div style="text-align: center; margin-top: 1.5rem; color: #888;">
                계정이 없으신가요? 
                <a href="${root}/user?action=regist" 
                   style="color: #667eea; text-decoration: none; font-weight: 600;">
                    회원가입
                </a>
            </div>
        </div>
    </div>
    <c:if test="${not empty alertMsg}">
		<script>
			alert("${alertMsg}");
		</script>
	</c:if>
</main>

<%@ include file="/view/common/footer.jsp" %>