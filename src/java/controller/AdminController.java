package controller;

import dao.DAOStudent;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Student;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminURL"})
public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String service = request.getParameter("service");
        if (service == null) {
            service = "listStudents"; // Mặc định hiển thị danh sách sinh viên
        }

        DAOStudent studentDAO = new DAOStudent();

        try {
            switch (service) {
                case "listStudents":
                    ArrayList<Student> students = DAOStudent.getAllStudentsForAdmin();
                    request.setAttribute("students", students);
                    request.getRequestDispatcher("/views/admin/AdminEdit.jsp").forward(request, response);
                    break;

                case "deleteStudent":
                    String studentIdStr = request.getParameter("student_id");
                    if (studentIdStr != null && !studentIdStr.isEmpty()) {
                        try {
                            int studentId = Integer.parseInt(studentIdStr);
                            if (studentId == 1) {
                                request.setAttribute("error", "Không thể xóa Admin.");
                            } else {
                                int rowsAffected = studentDAO.deleteStudent(studentId);
                                if (rowsAffected > 0) {
                                    request.setAttribute("message", "Xóa sinh viên thành công.");
                                } else {
                                    request.setAttribute("error", "Xóa thất bại. Có thể do sinh viên còn dữ liệu liên quan.");
                                }
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID sinh viên không hợp lệ.");
                        }
                    } else {
                        request.setAttribute("error", "Thiếu ID sinh viên để xóa.");
                    }
                    ArrayList<Student> updatedStudents = DAOStudent.getAllStudentsForAdmin();
                    request.setAttribute("students", updatedStudents);
                    request.getRequestDispatcher("/views/admin/AdminEdit.jsp").forward(request, response);
                    break;

                default:
                    ArrayList<Student> defaultStudents = DAOStudent.getAllStudentsForAdmin();
                    request.setAttribute("students", defaultStudents);
                    request.getRequestDispatcher("/views/admin/AdminEdit.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/AdminEdit.jsp").forward(request, response);
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
        return "AdminController Servlet";
    }
}
