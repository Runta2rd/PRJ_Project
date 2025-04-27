<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${pageTitle}" default="Chia Sẻ Đồ"></c:out></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/index.jsp">Chia Sẻ Đồ</a>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index.jsp">Trang Chủ</a></li>
                    <c:if test="${sessionScope.loggedInUser == null}">
                        <li><a href="${pageContext.request.contextPath}/login">Đăng Nhập</a></li>
                        <li><a href="${pageContext.request.contextPath}/signup">Đăng Ký</a></li>
                    </c:if>
                    <c:if test="${sessionScope.loggedInUser != null}">
                        <li><a href="${pageContext.request.contextPath}/ItemURL?service=insertItem">Đăng Tin</a></li>
                        <li><a href="${pageContext.request.contextPath}/views/items/create.jsp">Đăng tin (dự bị)</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a></li>
                        <li>Chào, <c:out value="${sessionScope.loggedInUser.full_name}"></c:out></li>
                        <%-- Assuming 'Admin' is a specific full name for admin users --%>
                        <c:if test="${sessionScope.loggedInUser.full_name == 'Admin'}">
                            <li><a href="${pageContext.request.contextPath}/AdminURL">Admin</a></li>
                        </c:if>
                    </c:if>
                </ul>
            </nav>
        </div>
    </header>
    <div class="container content">