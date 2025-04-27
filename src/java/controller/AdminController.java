package controller;

import dao.DAOStudent;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import model.Student;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})//chatgpt
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Student currentUser = (Student) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("edit".equals(action) && currentUser != null 
                && "Admin".equals(currentUser.getFull_name())) {
            ArrayList<Student> list =  DAOStudent.getAllStudentsForAdmin();  
            request.setAttribute("list", list);
            request.getRequestDispatcher("/views/admin/AdminEdit.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý các hành động POST từ trang admin
    }
}