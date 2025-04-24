<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layout/header.jsp" %>

<main class="form-container">
    <section class="form-section">
        <h2>Đăng Nhập</h2>
        <c:if test="${not empty requestScope.errorMessage}">
            <div class="error-message">${requestScope.errorMessage}</div>
        </c:if>
        <c:if test="${param.registerSuccess}">
            <div class="success-message">Đăng ký thành công! Vui lòng đăng nhập.</div>
        </c:if>
        <%-- Updated form action to use the /login URL pattern --%>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="button primary">Đăng Nhập</button>
        </form>
        <div class="form-footer">
            <%-- Updated register link to use the /signup URL pattern --%>
            <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/signup">Đăng ký ngay</a></p>
        </div>
    </section>
</main>

<%@ include file="/layout/footer.jsp" %>