<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layout/header.jsp" %>

<main class="form-container">
    <section class="form-section">
        <h2>Đăng Ký</h2>
        <c:if test="${not empty requestScope.errorMessage}">
            <div class="error-message">${requestScope.errorMessage}</div>
        </c:if>
        <%-- Updated form action to use the /signup URL pattern --%>
        <form action="${pageContext.request.contextPath}/signup" method="post">
            <div class="form-group">
                <label for="fullName">Họ và tên:</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <button type="submit" class="button primary">Đăng Ký</button>
        </form>
        <div class="form-footer">
            <%-- Updated login link to use the /login URL pattern --%>
            <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
        </div>
    </section>
</main>

<%@ include file="/layout/footer.jsp" %>