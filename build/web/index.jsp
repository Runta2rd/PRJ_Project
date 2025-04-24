<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layout/header.jsp" %>

<main>
    <section class="hero">
        <div class="container">
            <h1>Chào mừng đến với Chia Sẻ Đồ</h1>
            <p>Nơi bạn có thể chia sẻ, cho tặng, thuê hoặc mua những món đồ cần thiết.</p>
            <a href="${pageContext.request.contextPath}/ItemURL?action=listItem" class="button primary">Xem các món đồ</a>
        </div>
    </section>

    <section class="features">
        <div class="container">
            <h2>Các tính năng nổi bật</h2>
            <div class="feature-list">
                <div class="feature-item">
                    <h3>Chia sẻ dễ dàng</h3>
                    <p>Đăng tải và chia sẻ những món đồ bạn không còn sử dụng một cách nhanh chóng.</p>
                </div>
                <div class="feature-item">
                    <h3>Tìm kiếm thuận tiện</h3>
                    <p>Dễ dàng tìm thấy những món đồ bạn cần từ cộng đồng người dùng.</p>
                </div>
                <div class="feature-item">
                    <h3>Kết nối cộng đồng</h3>
                    <p>Giao lưu và kết nối với những người có cùng nhu cầu.</p>
                </div>
            </div>
        </div>
    </section>
</main>

<%@ include file="/layout/footer.jsp" %>