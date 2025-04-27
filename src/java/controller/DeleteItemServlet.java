package controller;

import dao.DAOItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Student;

/**
 * Servlet implementation class DeleteItemServlet
 */
@WebServlet(name = "DeleteItemServlet", urlPatterns = {"/DeleteItemServlet"})
public class DeleteItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteItemServlet.class.getName());

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy item_id từ request
        String itemIdStr = request.getParameter("item_id");
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Không tìm thấy ID sản phẩm để xóa.");
            request.getRequestDispatcher("/ItemURL?service=listItem").forward(request, response);
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
            request.getRequestDispatcher("/ItemURL?service=listItem").forward(request, response);
            return;
        }

        // Lấy thông tin người dùng đang đăng nhập từ session
        HttpSession session = request.getSession();
        Student loggedInUser = (Student) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để xóa sản phẩm.");
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Hoặc trang đăng nhập của bạn
            return;
        }

        int studentId = loggedInUser.getStudent_id();

        // Gọi DAO để xóa sản phẩm
        DAOItem daoItem = new DAOItem();
        int result = daoItem.deleteItem(itemId, studentId);

        if (result > 0) {
            session.setAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
        } else {
            session.setAttribute("errorMessage", "Không thể xóa sản phẩm. Có thể bạn không phải là người đăng sản phẩm này hoặc sản phẩm không tồn tại.");
        }

        // Chuyển hướng về trang danh sách sản phẩm
        response.sendRedirect(request.getContextPath() + "/ItemURL?service=listItem");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Thông thường, xóa sẽ được thực hiện bằng phương thức GET để dễ dàng xử lý và chuyển hướng.
        // Bạn có thể thêm logic xử lý POST nếu cần thiết (ví dụ: xác nhận xóa qua form).
        response.sendRedirect(request.getContextPath() + "/ItemURL?service=listItem");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for deleting an item.";
    }
}