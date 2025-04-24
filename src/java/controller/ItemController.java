package controller;

import model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;
import dao.DAOItem;
import java.sql.Date;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "ItemController", urlPatterns = {"/ItemURL"})
public class ItemController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOItem dao = new DAOItem();
        String service = request.getParameter("service");
        if (service == null) {
            service = "listItem";
        }

        try {
            switch (service) {
                case "deleteItem":
                    int item_id = Integer.parseInt(request.getParameter("item_id"));
                    dao.deleteItem(item_id);
                    response.sendRedirect("ItemURL?service=listItem");
                    break;

                case "updateItem":
                    if (request.getParameter("submit") == null) {
                        int updateId = Integer.parseInt(request.getParameter("item_id"));
                        Item item = dao.getItemById(updateId); // Sử dụng phương thức đã sửa
                        request.setAttribute("item", item);
                        request.getRequestDispatcher("/jsp/updateItem.jsp").forward(request, response);
                    } else {
                        int updateId = Integer.parseInt(request.getParameter("item_id"));
                        Item updatedItem = extractItemFromRequest(request);
                        updatedItem.setItem_id(updateId);
                        dao.updateItem(updatedItem);
                        response.sendRedirect("ItemURL?service=listItem");
                    }
                    break;

                case "viewItem": // Đổi tên service thành viewItem cho rõ ràng hơn
                    int itemId = Integer.parseInt(request.getParameter("item_id"));
                    Item item = dao.getItemById(itemId); // Sử dụng phương thức đã sửa để lấy cả thông tin Student
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("/views/items/view.jsp").forward(request, response);
                    break;

                case "insertItem":
                    if (request.getParameter("submit") == null) {
                        request.getRequestDispatcher("/views/items/create.jsp").forward(request, response);
                    } else {
                        Item newItem = extractItemFromRequest(request);
                        dao.addItem(newItem);
                        response.sendRedirect("ItemURL?service=listItem");
                    }
                    break;

                case "listItem":
                default:
                    String sql = "SELECT i.*, s.student_id AS s_student_id, s.full_name " +
                                 "FROM items i " +
                                 "JOIN students s ON i.student_id = s.student_id";
                    String searchName = request.getParameter("searchName");
                    String searchCategory = request.getParameter("searchCategory");

                    StringBuilder whereClause = new StringBuilder();
                    if (searchName != null && !searchName.isEmpty()) {
                        whereClause.append(" WHERE i.itemName LIKE '%" + searchName + "%'");
                    }

                    if (searchCategory != null && !searchCategory.isEmpty()) {
                        if (whereClause.length() > 0) {
                            whereClause.append(" AND i.category_name = N'" + searchCategory + "'");
                        } else {
                            whereClause.append(" WHERE i.category_name = N'" + searchCategory + "'");
                        }
                    }

                    sql += whereClause.toString();
                    Vector<Item> items = dao.getItems(sql);
                    request.setAttribute("items", items);

                    Vector<String> categories = dao.getAllCategories();
                    request.setAttribute("categories", categories);

                    request.getRequestDispatcher("/views/items/list.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }

    private Item extractItemFromRequest(HttpServletRequest request) {
        int student_id = Integer.parseInt(request.getParameter("student_id"));
        String category_name = request.getParameter("category_name");
        String itemName = request.getParameter("itemName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image_url = request.getParameter("image_url");
        boolean is_for_giveaway = request.getParameter("is_for_giveaway") != null;
        boolean is_for_rent = request.getParameter("is_for_rent") != null;
        boolean is_for_sale = request.getParameter("is_for_sale") != null;
        BigDecimal rent_price = parseBigDecimal(request.getParameter("rent_price"));
        BigDecimal sale_price = parseBigDecimal(request.getParameter("sale_price"));
        Date created_at = parseDate(request.getParameter("created_at"));

        return new Item(student_id, category_name, itemName, title, description, image_url,
                is_for_giveaway, is_for_rent, is_for_sale, rent_price, sale_price, created_at);
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value != null && !value.isEmpty()) ? new BigDecimal(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Date parseDate(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Date.valueOf(value) : null;
        } catch (IllegalArgumentException e) {
            return null;
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
        return "ItemController Servlet";
    }
}
