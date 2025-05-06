<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layout/header.jsp" %>
<style>
    .students-grid {
        display: flex;
        flex-direction: column;
        gap: 16px;
        max-width: 800px;
        margin: auto;
        padding: 20px;
    }
    .student-card {
        display: flex;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 12px;
        background: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        transition: box-shadow 0.3s;
        align-items: center; /* Căn chỉnh theo chiều dọc */
        justify-content: space-between; /* Đẩy thông tin và nút delete ra hai đầu */
    }
    .student-info {
        flex-grow: 1;
    }
    .student-name {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 4px;
        color: #333;
    }
    .student-email, .student-id {
        font-size: 14px;
        color: #666;
    }
    .delete-button {
        background-color: #f44336; /* Màu đỏ */
        color: white;
        padding: 8px 16px;
        text-decoration: none;
        border-radius: 4px;
        font-size: 14px;
        cursor: pointer;
    }
    .delete-button:hover {
        background-color: #d32f2f; /* Màu đỏ đậm hơn khi hover */
    }
    .no-students-message {
        text-align: center;
        padding: 20px;
        font-size: 16px;
        color: #777;
    }
</style>

<main class="admin-edit-page">
    <section class="students-section">
        <h2>Quản lý Sinh viên</h2>
        <div class="students-grid">
            <c:forEach var="student" items="${requestScope.students}">
                <div class="student-card">
                    <div class="student-info">
                        <div class="student-name">${student.full_name}</div>
                        <div class="student-email">Email: ${student.email}</div>
                        <div class="student-id">ID: ${student.student_id}</div>
                        <c:if test="${not empty student.facebook_url}">
                            <div class="student-facebook">Facebook: <a href="${student.facebook_url}" target="_blank">Link</a></div>
                        </c:if>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/AdminURL?service=deleteStudent&student_id=${student.student_id}"
                           class="delete-button"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên này?')">Xóa</a>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty requestScope.students}">
                <p class="no-students-message">Không có sinh viên nào trong hệ thống.</p>
            </c:if>
        </div>
    </section>
</main>

<%@ include file="/layout/footer.jsp" %>