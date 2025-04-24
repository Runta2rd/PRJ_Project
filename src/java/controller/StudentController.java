package controller;

import model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;
import dao.DAOStudent;

@WebServlet(name = "StudentController", urlPatterns = {"/StudentURL"})
public class StudentController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOStudent dao = new DAOStudent();
        String service = request.getParameter("service");
        if (service == null) service = "listStudent";

        try {
            switch (service) {
                case "deleteStudent":
                    int student_id = Integer.parseInt(request.getParameter("student_id"));
                    dao.deleteStudent(student_id);
                    response.sendRedirect("StudentURL?service=listStudent");
                    break;

                case "updateStudent":
                    if (request.getParameter("submit") == null) {
                        int updateId = Integer.parseInt(request.getParameter("student_id"));
                        Student student = dao.getStudentById(updateId);
                        request.setAttribute("student", student);
                        request.getRequestDispatcher("/jsp/updateStudent.jsp").forward(request, response);
                    } else {
                        int updateId = Integer.parseInt(request.getParameter("student_id"));
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String full_name = request.getParameter("full_name");
                        Student updatedStudent = new Student(email, password, full_name);
                        updatedStudent.setStudent_id(updateId);
                        dao.updateStudent(updatedStudent);
                        response.sendRedirect("StudentURL?service=listStudent");
                    }
                    break;

                case "insertStudent":
                    if (request.getParameter("submit") == null) {
                        request.getRequestDispatcher("/jsp/insertStudent.jsp").forward(request, response);
                    } else {
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String full_name = request.getParameter("full_name");
                        Student newStudent = new Student(email, password, full_name);
                        dao.addStudent(newStudent);
                        response.sendRedirect("StudentURL?service=listStudent");
                    }
                    break;

                case "listStudent":
                default:
                    String sql = "SELECT * FROM students";
                    String search = request.getParameter("search");
                    if (search != null && !search.isEmpty()) {
                        sql += " WHERE email LIKE '%" + search + "%' OR full_name LIKE '%" + search + "%'";
                    }
                    Vector<Student> students = dao.getStudents(sql);
                    request.setAttribute("students", students);
                    request.getRequestDispatcher("/jsp/studentList.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
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
        return "StudentController Servlet";
    }
}
