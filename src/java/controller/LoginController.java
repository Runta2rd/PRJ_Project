package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Student;
import dao.DAOStudent;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            DAOStudent studentDAO = new DAOStudent();
            Student student = studentDAO.getStudentByEmail(email);

            if (student != null && student.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                // Sử dụng "loggedInUser" thay vì "user" để thống nhất
                session.setAttribute("loggedInUser", student);
                response.sendRedirect("index.jsp"); // Redirect to the home page
            } else {
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("students/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during login.");
            request.getRequestDispatcher("students/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to the login page
        response.sendRedirect("students/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "LoginController Servlet";
    }
}