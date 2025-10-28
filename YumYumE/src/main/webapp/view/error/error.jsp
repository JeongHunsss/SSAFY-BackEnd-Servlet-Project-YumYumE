<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YumYume - 오류</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .error-container {
            background: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            max-width: 600px;
            width: 100%;
            text-align: center;
        }
        .error-icon {
            font-size: 5em;
            margin-bottom: 20px;
        }
        h1 {
            color: #dc3545;
            margin-bottom: 20px;
            font-size: 2em;
        }
        .error-message {
            color: #666;
            font-size: 1.1em;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        .error-details {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: left;
        }
        .error-details h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 1.1em;
        }
        .error-details p {
            color: #666;
            font-size: 0.95em;
            line-height: 1.5;
            word-break: break-word;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 1em;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
            margin: 5px;
        }
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        <h1>오류가 발생했습니다</h1>
        
        <% 
            String alertMsg = (String) request.getAttribute("alertMsg");
            if (alertMsg != null) {
        %>
            <div class="error-message">
                <%= alertMsg %>
            </div>
        <% } else { %>
            <div class="error-message">
                요청을 처리하는 중 오류가 발생했습니다.<br>
                잠시 후 다시 시도해주세요.
            </div>
        <% } %>
        
        <% 
            Exception ex = (Exception) request.getAttribute("exception");
            if (ex != null && ex.getMessage() != null) {
        %>
            <div class="error-details">
                <h3>오류 상세 정보:</h3>
                <p><%= ex.getMessage() %></p>
            </div>
        <% } %>
        
        <div>
            <a href="javascript:history.back()" class="btn btn-secondary">이전 페이지</a>
            <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-primary">메인으로</a>
        </div>
    </div>
</body>
</html>