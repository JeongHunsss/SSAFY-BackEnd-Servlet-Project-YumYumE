<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YumYumE - 영양 관리 시스템</title>
    <link rel="stylesheet" href="${root}/static/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <header>
        <nav>
            <div class="logo" onclick="location.href='${root}/index.jsp'">
                <span style="-webkit-text-fill-color: initial;">🍽️</span> YumYumE
            </div>
            
            <ul class="nav-links">
                <li><a href="${root}/food?action=list">식단 분석</a></li>
                <c:if test="${not empty sessionScope.userInfo}">
                    <li><a href="${root}/report?action=list&userId=${sessionScope.userInfo.id}">내 리포트</a></li>
                </c:if>
            </ul>
            
            <div class="user-info">
                <c:choose>
                    <c:when test="${not empty sessionScope.userInfo}">
                        <a href="${root}/user?action=mypage&id=${sessionScope.userInfo.id}" class="btn btn-secondary">${sessionScope.userInfo.name}님</a>
                        <form action="${root}/user?action=logout" method="post" style="display: inline;">
                            <button type="submit" class="btn btn-primary">로그아웃</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <a href="${root}/user?action=login" class="btn btn-secondary">로그인</a>
                        <a href="${root}/user?action=regist" class="btn btn-primary">회원가입</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>
    
    <c:if test="${not empty sessionScope.alertMsg}">
        <div class="container" style="margin-top: 1rem;">
            <div class="alert alert-success">
                ${sessionScope.alertMsg}
            </div>
        </div>
        <c:remove var="alertMsg" scope="session"/>
    </c:if>
    
    <c:if test="${not empty requestScope.alertMsg}">
        <div class="container" style="margin-top: 1rem;">
            <div class="alert alert-error">
                ${requestScope.alertMsg}
            </div>
        </div>
    </c:if>