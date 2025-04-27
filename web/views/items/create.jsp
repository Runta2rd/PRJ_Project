<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layout/header.jsp" %>

<style>
    .create-item-container {
        max-width: 800px;
        margin: 20px auto;
        padding: 20px;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    }

    .create-item-container h2 {
        color: #333;
        margin-bottom: 20px;
        text-align: center;
    }

    .form-group {
        margin-bottom: 16px;
    }

    .form-group label {
        display: block;
        font-weight: bold;
        margin-bottom: 8px;
        color: #333;
    }

    .form-group input[type="text"],
    .form-group input[type="number"],
    .form-group input[type="file"],
    .form-group textarea,
    .form-group select {
        width: calc(100% - 12px);
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        font-size: 16px;
    }

    .form-group input[type="checkbox"] {
        margin-right: 8px;
    }

    .form-group textarea {
        resize: vertical;
        min-height: 100px;
    }

    .form-actions {
        margin-top: 20px;
        text-align: center;
    }

    .form-actions button[type="submit"],
    .form-actions a {
        padding: 10px 20px;
        font-size: 16px;
        border-radius: 4px;
        text-decoration: none;
        transition: background-color 0.3s ease;
    }

    .form-actions button[type="submit"] {
        background-color: #007bff;
        color: white;
        border: none;
        cursor: pointer;
    }

    .form-actions button[type="submit"]:hover {
        background-color: #0056b3;
    }

    .form-actions a {
        background-color: #eee;
        color: #333;
        border: 1px solid #ddd;
    }

    .form-actions a:hover {
        background-color: #ddd;
    }

    .error-message {
        color: red;
        margin-top: 8px;
    }
</style>

<main class="create-item-page">
    <div class="create-item-container">
        <h2>Đăng Bán Sản Phẩm Mới</h2>
        <form action="${pageContext.request.contextPath}/ItemURL?service=insertItem"" method="post">
            <c:if test="${not empty requestScope.errorMessage}">
                <p class="error-message">${requestScope.errorMessage}</p>
            </c:if>

            <div class="form-group">
                <label for="category_name">Danh mục:</label>
                <select name="category_name" class="category-select">
                    <option value="">-- Tất cả danh mục --</option>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="itemName">Tên sản phẩm:</label>
                <input type="text" id="itemName" name="itemName" >
            </div>

            <div class="form-group">
                <label for="title">Tiêu đề:</label>
                <input type="text" id="title" name="title">
            </div>

            <div class="form-group">
                <label for="description">Mô tả:</label>
                <textarea id="description" name="description" rows="5"></textarea>
            </div>

            <div class="form-group">
                <label for="image_url">Hình ảnh (URL):</label>
                <input type="text" id="image_url" name="image_url">
            </div>

            <div class="form-group">
                <input type="checkbox" id="is_for_giveaway" name="is_for_giveaway" value="true">
                <label for="is_for_giveaway">Cho tặng miễn phí</label>
            </div>

            <div class="form-group">
                <input type="checkbox" id="is_for_rent" name="is_for_rent" value="true">
                <label for="is_for_rent">Cho thuê</label>
                <div id="rent_price_section" style="display: none;">
                    <label for="rent_price">Giá thuê (VNĐ/ngày):</label>
                    <input type="number" id="rent_price" name="rent_price" step="0.01" min="0">
                </div>
            </div>

            <div class="form-group">
                <input type="checkbox" id="is_for_sale" name="is_for_sale" value="true">
                <label for="is_for_sale">Bán</label>
                <div id="sale_price_section" style="display: none;">
                    <label for="sale_price">Giá bán (VNĐ):</label>
                    <input type="number" id="sale_price" name="sale_price" step="0.01" min="0">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" name="submit">Đăng Bán</button>
                <a href="${pageContext.request.contextPath}/ItemURL?service=listItem">Hủy</a>
            </div>
        </form>
    </div>

    <script>
        const rentCheckbox = document.getElementById('is_for_rent');
        const saleCheckbox = document.getElementById('is_for_sale');
        const rentPriceSection = document.getElementById('rent_price_section');
        const salePriceSection = document.getElementById('sale_price_section');

        rentCheckbox.addEventListener('change', function () {
            rentPriceSection.style.display = this.checked ? 'block' : 'none';
            if (this.checked) {
                saleCheckbox.checked = false;
                salePriceSection.style.display = 'none';
            }
        });

        saleCheckbox.addEventListener('change', function () {
            salePriceSection.style.display = this.checked ? 'block' : 'none';
            if (this.checked) {
                rentCheckbox.checked = false;
                rentPriceSection.style.display = 'none';
            }
        });
    </script>
</main>

<%@ include file="/layout/footer.jsp" %>