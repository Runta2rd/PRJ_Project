<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/layout/header.jsp" %>

<style>
    .item-detail-container {
        max-width: 800px;
        margin: 20px auto;
        padding: 20px;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    }

    .item-image {
        text-align: center;
        margin-bottom: 20px;
    }

    .item-image img {
        max-width: 100%;
        height: auto;
        border-radius: 6px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    }

    .item-info {
        margin-bottom: 16px;
    }

    .item-info label {
        font-weight: bold;
        display: block;
        margin-bottom: 4px;
        color: #333;
    }

    .item-info span {
        display: block;
        color: #666;
        margin-bottom: 8px;
    }

    .item-price {
        font-size: 18px;
        color: #d70000;
        font-weight: bold;
        margin-bottom: 16px;
    }

    .item-description-section {
        margin-bottom: 20px;
        border-top: 1px solid #eee;
        padding-top: 16px;
    }

    .item-description-section h3 {
        color: #333;
        margin-bottom: 8px;
    }

    .item-description-section p {
        color: #666;
        line-height: 1.6;
    }

    .back-link {
        display: inline-block;
        padding: 8px 16px;
        background-color: #eee;
        color: #333;
        text-decoration: none;
        border-radius: 4px;
        transition: background-color 0.3s ease;
    }

    .back-link:hover {
        background-color: #ddd;
    }
    .chat-button {
        display: inline-block;
        padding: 10px 20px;
        background-color: #007bff;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        margin-top: 16px;
        transition: background-color 0.3s ease;
        border: none;
        cursor: pointer;
        font-size: 16px;
    }

    .chat-button:hover {
        background-color: #0056b3;
    }
</style>

<main class="view-item-page">
    <div class="item-detail-container">
        <c:if test="${not empty requestScope.item}">
            <div class="item-image">
                <c:choose>
                    <c:when test="${not empty requestScope.item.image_url}">
                        <img src="${requestScope.item.image_url}" alt="${requestScope.item.itemName}" />
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/default-image-large.jpg" alt="No Image" style="max-height: 300px; object-fit: contain;">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="item-info">
                <label>Tên sản phẩm:</label>
                <span>${requestScope.item.itemName}</span>
            </div>

            <div class="item-info">
                <label>Tiêu đề:</label>
                <span>${requestScope.item.title}</span>
            </div>

            <div class="item-info">
                <label>Danh mục:</label>
                <span>${requestScope.item.category_name}</span>
            </div>

            <div class="item-info">
                <label>Người bán:</label>
                <span>${requestScope.item.students.full_name}</span>
                <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.student_id != requestScope.item.student_id}">
                    <a href="${requestScope.item.students.facebook_url}" class="chat-button" target="_blank" rel="noopener noreferrer">
                        Liên kết Facebook Người bán
                    </a>
                </c:if>
                <c:if test="${empty sessionScope.loggedInUser}">
                    <p style="color: orange;">Bạn cần đăng nhập để chat với người bán.</p>
                </c:if>
                <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.student_id == requestScope.item.student_id}">
                    <p style="color: gray;">Đây là sản phẩm của bạn.</p>
                </c:if>
            </div>

            <div class="item-price">
                <c:choose>
                    <c:when test="${requestScope.item.is_for_giveaway}">
                        <span style="color: green; font-weight: bold;">Cho tặng miễn phí</span>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${requestScope.item.is_for_rent}">
                            <div>Cho thuê (tính theo ngày):
                                <span style="color: #e53935; font-weight: bold;">
                                    <fmt:formatNumber value="${requestScope.item.rent_price}" type="currency"/>
                                </span>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.item.is_for_sale}">
                            <div>Giá bán:
                                <span style="color: #d32f2f; font-weight: bold;">
                                    <fmt:formatNumber value="${requestScope.item.sale_price}" type="currency"/>
                                </span>
                            </div>
                        </c:if>
                        <c:if test="${not requestScope.item.is_for_rent and not requestScope.item.is_for_sale}">
                            <span>Không có thông tin về giá</span>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="item-description-section">
                <h3>Mô tả sản phẩm</h3>
                <p>${requestScope.item.description}</p>
            </div>

            <a href="${pageContext.request.contextPath}/ItemURL?service=listItem" class="back-link">Quay lại danh sách</a>
        </c:if>
        <c:if test="${empty requestScope.item}">
            <p class="error-message">Không tìm thấy sản phẩm.</p>
            <a href="${pageContext.request.contextPath}/ItemURL?service=listItem" class="back-link">Quay lại danh sách</a>
        </c:if>
    </div>

    <script>
        function startChat(sellerId, itemName) {
            // Đây là nơi bạn sẽ xử lý logic để bắt đầu chat
            // Ví dụ: chuyển hướng đến trang chat với sellerId,
            // hoặc gọi một hàm JavaScript để mở cửa sổ chat.
            alert('Bắt đầu chat với người bán có ID: ' + sellerId + ' về sản phẩm: ' + itemName);
            // window.location.href = '/chat?sellerId=' + sellerId; // Ví dụ chuyển hướng
            // openChatWindow(sellerId, itemName); // Ví dụ gọi hàm JavaScript
        }
    </script>
</main>

<%@ include file="/layout/footer.jsp" %>