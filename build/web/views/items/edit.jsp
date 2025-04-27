<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/layout/header.jsp" %>

<style>
    .edit-item-form {
        max-width: 600px;
        margin: auto;
        padding: 20px;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }
    .edit-item-form h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
        display: block;
        font-size: 14px;
        color: #555;
        margin-bottom: 5px;
    }
    .form-control {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 16px;
    }
    .form-check-label {
        margin-left: 8px;
    }
    .submit-btn {
        padding: 10px 20px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }
    .submit-btn:hover {
        background-color: #0056b3;
    }
    .cancel-btn {
        padding: 10px 20px;
        background-color: #e0e0e0;
        color: #333;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
        margin-left: 10px;
        text-decoration: none;
    }
    .cancel-btn:hover {
        background-color: #ccc;
    }

    .form-check-inline {
        display: inline-flex;
        align-items: center;
        margin-right: 15px;
    }

    .error-message {
        color: red;
        font-size: 14px;
        margin-top: 5px;
    }
</style>

<main class="edit-item-page">
    <form action="${pageContext.request.contextPath}/UpdateItemServlet" method="post" class="edit-item-form">
        <h2>Chỉnh sửa sản phẩm</h2>

        <input type="hidden" name="item_id" value="${requestScope.item.item_id}"> <%-- Truyền item_id để biết sản phẩm nào cần chỉnh sửa --%>

        <div class="form-group">
            <label for="itemName">Tên sản phẩm <span class="text-danger">*</span></label>
            <input type="text" name="itemName" id="itemName" class="form-control" value="${requestScope.item.itemName}" required>
            <c:if test="${not empty requestScope.errors['itemName']}">
                <p class="error-message">${requestScope.errors['itemName']}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="categoryName">Danh mục <span class="text-danger">*</span></label>
            <select name="categoryName" id="categoryName" class="form-control" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="category" items="${requestScope.categories}">
                    <option value="${category}" ${category == requestScope.item.category_name ? 'selected' : ''}>${category}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty requestScope.errors['categoryName']}">
                <p class="error-message">${requestScope.errors['categoryName']}</p>
            </c:if>
        </div>

        <div class="form-group">
            <label for="title">Tiêu đề</label>
            <input type="text" name="title" id="title" class="form-control" value="${requestScope.item.title}">
             <c:if test="${not empty requestScope.errors['title']}">
                            <p class="error-message">${requestScope.errors['title']}</p>
             </c:if>
        </div>

        <div class="form-group">
            <label for="description">Mô tả</label>
            <textarea name="description" id="description" class="form-control" rows="4">${requestScope.item.description}</textarea>
             <c:if test="${not empty requestScope.errors['description']}">
                            <p class="error-message">${requestScope.errors['description']}</p>
             </c:if>
        </div>

        <div class="form-group">
            <label for="image_url">URL hình ảnh</label>
            <input type="text" name="image_url" id="image_url" class="form-control" value="${requestScope.item.image_url}">
             <c:if test="${not empty requestScope.errors['image_url']}">
                            <p class="error-message">${requestScope.errors['image_url']}</p>
             </c:if>
        </div>

        <div class="form-group">
            <div class="form-check-inline">
                <input type="checkbox" name="is_for_giveaway" id="is_for_giveaway" class="form-check-input" ${requestScope.item.is_for_giveaway ? 'checked' : ''}>
                <label class="form-check-label" for="is_for_giveaway">Cho tặng</label>
            </div>
            <div class="form-check-inline">
                <input type="checkbox" name="is_for_rent" id="is_for_rent" class="form-check-input" ${requestScope.item.is_for_rent ? 'checked' : ''}>
                <label class="form-check-label" for="is_for_rent">Cho thuê</label>
            </div>
            <div class="form-check-inline">
                <input type="checkbox" name="is_for_sale" id="is_for_sale" class="form-check-input" ${requestScope.item.is_for_sale ? 'checked' : ''}>
                <label class="form-check-label" for="is_for_sale">Bán</label>
            </div>
        </div>

        <div class="form-group">
            <label for="rent_price">Giá cho thuê (VND/ngày)</label>
            <input type="number" name="rent_price" id="rent_price" class="form-control" value="${requestScope.item.rent_price}" min="0">
             <c:if test="${not empty requestScope.errors['rent_price']}">
                            <p class="error-message">${requestScope.errors['rent_price']}</p>
             </c:if>
        </div>

        <div class="form-group">
            <label for="sale_price">Giá bán (VND)</label>
            <input type="number" name="sale_price" id="sale_price" class="form-control" value="${requestScope.item.sale_price}" min="0">
             <c:if test="${not empty requestScope.errors['sale_price']}">
                            <p class="error-message">${requestScope.errors['sale_price']}</p>
             </c:if>
        </div>


        <button type="submit" class="submit-btn">Lưu thay đổi</button>
        <a href="${pageContext.request.contextPath}/ItemURL?service=listItem" class="cancel-btn">Hủy</a>
    </form>
</main>

<%@ include file="/layout/footer.jsp" %>
