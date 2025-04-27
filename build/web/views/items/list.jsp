<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/layout/header.jsp" %>

<style>
    .items-grid {
        display: flex;
        flex-direction: column;
        gap: 16px;
        max-width: 800px;
        margin: auto;
        padding: 20px;
    }
    .item-card {
        display: flex;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 12px;
        background: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        transition: box-shadow 0.3s;
    }
    .item-card:hover {
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }
    .item-image img {
        width: 150px;
        height: 150px;
        object-fit: cover;
        border-radius: 6px;
    }
    .item-content {
        margin-left: 16px;
        flex: 1;
    }
    .item-name {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 4px;
        color: #333;
    }
    .item-title, .item-description, .item-category {
        font-size: 14px;
        color: #666;
    }
    .item-price {
        font-size: 16px;
        color: #d70000;
        font-weight: bold;
        margin-top: 6px;
    }
    .item-location {
        font-size: 13px;
        color: #888;
    }
    .item-footer {
        margin-top: 8px;
        display: flex; /* Thêm display flex để căn chỉnh nút */
        justify-content: space-between; /* Đẩy nút xem chi tiết và edit ra hai đầu */
        align-items: center; /* Căn chỉnh các nút theo chiều dọc nếu cần */
    }
    .button.secondary {
        background-color: #eee;
        color: #333;
        padding: 6px 12px;
        text-decoration: none;
        border-radius: 4px;
    }
    .edit-button {
        background-color: #4CAF50; /* Màu xanh lá cây */
        color: white;
        padding: 6px 12px;
        text-decoration: none;
        border-radius: 4px;
        font-size: 14px;
    }
    .edit-button:hover {
        background-color: #45a049; /* Màu xanh lá cây đậm hơn khi hover */
    }

    .search-filter-bar {
        max-width: 800px;
        margin: 20px auto;
        padding: 10px;
        background: #f9f9f9;
        border: 1px solid #ddd;
        border-radius: 6px;
        display: flex;
        gap: 16px;
        align-items: center;
    }

    .search-input {
        flex-grow: 1;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 16px;
    }

    .category-select {
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 16px;
    }

    .filter-button {
        padding: 8px 16px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    .filter-button:hover {
        background-color: #0056b3;
    }
    .delete-button {
        background-color: #f44336; /* Màu đỏ */
        color: white;
        padding: 6px 12px;
        text-decoration: none;
        border-radius: 4px;
        font-size: 14px;
        margin-right: 8px; /* Thêm khoảng cách với nút Edit */
    }
    .delete-button:hover {
        background-color: #d32f2f; /* Màu đỏ đậm hơn khi hover */
    }
</style>

<main class="list-items-page">
    <section class="search-filter-section">
        <form action="${pageContext.request.contextPath}/ItemURL" method="get" class="search-filter-bar">
            <input type="hidden" name="service" value="listItem">
            <input type="text" name="searchName" class="search-input" placeholder="Tìm kiếm theo tên sản phẩm">
            <select name="searchCategory" class="category-select">
                <option value="">-- Tất cả danh mục --</option>
                <c:forEach var="category" items="${requestScope.categories}">
                    <option value="${category}">${category}</option>
                </c:forEach>
            </select>
            <button type="submit" class="filter-button">Lọc</button>
        </form>
    </section>

    <section class="items-section">
        <div class="items-grid">
            <c:forEach var="item" items="${requestScope.items}">
                <div class="item-card">
                    <div class="item-image">
                        <c:choose>
                            <c:when test="${not empty item.image_url}">
                                <img src="${item.image_url}" alt="${item.itemName}" />
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/images/default-image.jpg" alt="No Image" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="item-content">
                        <div class="item-name">${item.itemName}</div>
                        <div class="item-title">Người đăng: ${item.students.full_name}</div>
                        <div class="item-category">
                            <c:choose>
                                <c:when test="${not empty item.category_name}">
                                    ${item.category_name}
                                </c:when>
                                <c:otherwise>Không rõ danh mục</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="item-description">${item.description}</div>

                        <div class="item-price">
                            <c:choose>
                                <c:when test="${item.is_for_giveaway}">
                                    <span style="color: green; font-weight: bold;">✅ Cho tặng miễn phí</span>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${item.is_for_rent}">
                                        <div>✅ Cho thuê (tính theo ngày):
                                            <span style="color: #e53935; font-weight: bold;">
                                                <fmt:formatNumber value="${item.rent_price}" type="currency"/>
                                            </span>
                                        </div>
                                    </c:if>
                                    <c:if test="${item.is_for_sale}">
                                        <div>✅ Bán:
                                            <span style="color: #d32f2f; font-weight: bold;">
                                                <fmt:formatNumber value="${item.sale_price}" type="currency"/>
                                            </span>
                                        </div>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>


                        <div class="item-location">🌍 Hà Nội (demo)</div>

                        <div class="item-footer">
                            <a href="${pageContext.request.contextPath}/ItemURL?service=viewItem&item_id=${item.item_id}" class="button secondary">Xem chi tiết</a>
                            <%-- Kiểm tra nếu ID người đăng trùng với ID người dùng đang đăng nhập thì hiện nút Edit --%>
                            <c:if test="${sessionScope.loggedInUser.student_id == item.student_id}">
                                <a href="${pageContext.request.contextPath}/DeleteItemServlet?item_id=${item.item_id}" class="delete-button"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa</a>
                                <a href="${pageContext.request.contextPath}/ItemURL?service=updateItem&item_id=${item.item_id}" class="edit-button">Edit</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty requestScope.items}">
                <p class="no-items-message">Không có món đồ nào trong hệ thống.</p>
            </c:if>
        </div>
    </section>
</main>

<%@ include file="/layout/footer.jsp" %>
