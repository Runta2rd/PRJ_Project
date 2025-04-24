package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    private StudentService studentService = new StudentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý các request đến /AdminController
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            // Trang dashboard admin mặc định
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
        } else if (action.equals("users")) {
            // Hiển thị danh sách người dùng
            request.setAttribute("users", studentService.getAllStudents()); // Cần thêm getAllStudents()
            request.getRequestDispatcher("/views/admin/users.jsp").forward(request, response);
        }
        // Thêm các xử lý khác dựa trên tham số 'action'
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý các hành động POST từ trang admin
    }
}