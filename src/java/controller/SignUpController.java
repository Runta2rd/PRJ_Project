package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Student;
import dao.DAOStudent;

@WebServlet(name = "SignupController", urlPatterns = {"/signup"})
public class SignUpController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String full_name = request.getParameter("full_name");
        String facebook_url = request.getParameter("facebook_url");

        DAOStudent studentDAO = new DAOStudent();

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu không khớp!");
            request.getRequestDispatcher("/students/register.jsp").forward(request, response);
            return;
        }

        if (full_name == null || full_name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập họ và tên.");
            request.getRequestDispatcher("/students/register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.length() < 6) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("/students/register.jsp").forward(request, response);
            return;
        }

        Student existingStudent = studentDAO.getStudentByEmail(email);
        if (existingStudent != null) {
            request.setAttribute("errorMessage", "Email này đã được đăng ký!");
            request.getRequestDispatcher("/students/register.jsp").forward(request, response);
            return;
        }

        Student newStudent = new Student(email, password, full_name, facebook_url);
        int result = studentDAO.addStudent(newStudent);

        if (result > 0) {
            // Registration successful, redirect to /students/login.jsp with a success message
            response.sendRedirect("students/login.jsp?registerSuccess=true");
        } else {
            // Registration failed (e.g., database error)
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình đăng ký. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/students/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the signup form
        request.getRequestDispatcher("/students/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "SignupController Servlet";
    }
}
