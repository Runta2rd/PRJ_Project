package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession session = request.getSession(false); // Get existing session, don't create a new one

            if (session != null) {
                // Sửa ở đây: Thay "user" bằng "loggedInUser"
                session.removeAttribute("loggedInUser"); // Remove the loggedInUser attribute from the session
                session.invalidate(); // Invalidate the entire session
                response.sendRedirect("login?logoutSuccess=true"); // Redirect to login page with a success message
            } else {
                response.sendRedirect("index.jsp"); // If no session, redirect to home page (already logged out)
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?logoutFailed=true"); // Redirect to home page with a failure message
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "LogoutController Servlet";
    }
}