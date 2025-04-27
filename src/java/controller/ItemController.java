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
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Student;

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

        HttpSession session = request.getSession();
        Student loggedInUser = (Student) session.getAttribute("loggedInUser");
        Integer student_id = null;

        if (loggedInUser != null) {
            student_id = loggedInUser.getStudent_id();
        }

        try {
            switch (service) {
                case "deleteItem":
                    if (loggedInUser != null) {
                        int item_id = Integer.parseInt(request.getParameter("item_id"));
                        dao.deleteItem(item_id, student_id);
                        response.sendRedirect("ItemURL?service=listItem");
                    } else {
                        request.setAttribute("errorMessage", "Bạn cần đăng nhập để thực hiện hành động này.");
                        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
                    }
                    break;

                case "updateItem":
                    // ... (giữ nguyên logic updateItem, có thể thêm validation tương tự nếu cần)
                    if (request.getParameter("submit") == null) {
                        int updateId = Integer.parseInt(request.getParameter("item_id"));
                        Item item = dao.getItemById(updateId);
                        Vector<String> categories = dao.getAllCategories();
                        request.setAttribute("item", item);
                        request.setAttribute("categories", categories);
                        request.getRequestDispatcher("/views/items/edit.jsp").forward(request, response);
                    } else {
                        try {
                            int updateId = Integer.parseInt(request.getParameter("item_id"));
                            Item updatedItem = extractItemFromUpdateRequest(request);
                            int rowsAffected = dao.updateItem(updatedItem);

                            if (rowsAffected > 0) {
                                System.out.println("Cập nhật sản phẩm thành công với ID: " + updateId);
                                response.sendRedirect("ItemURL?service=listItem&message=update_success");
                            } else {
                                System.out.println("Cập nhật sản phẩm thất bại với ID: " + updateId);
                                request.setAttribute("errorMessage", "Cập nhật sản phẩm thất bại. Vui lòng kiểm tra lại thông tin.");
                                Vector<String> categories = dao.getAllCategories();
                                request.setAttribute("categories", categories);
                                request.setAttribute("item", updatedItem); // Giữ lại thông tin đã nhập
                                request.getRequestDispatcher("/views/items/edit.jsp").forward(request, response);
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("errorMessage", "Lỗi định dạng số. Vui lòng kiểm tra lại giá.");
                            request.getRequestDispatcher("/views/items/edit.jsp").forward(request, response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
                            request.getRequestDispatcher("/views/items/edit.jsp").forward(request, response);
                        }
                    }
                    break;

                case "viewItem":
                    int itemId = Integer.parseInt(request.getParameter("item_id"));
                    Item item = dao.getItemById(itemId);
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("/views/items/view.jsp").forward(request, response);
                    break;

                case "insertItem":
                    if (request.getParameter("submit") == null) {
                        Vector<String> categories = dao.getAllCategories();
                        request.setAttribute("categories", categories);
                        request.getRequestDispatcher("/views/items/create.jsp").forward(request, response);
                    } else {
                        if (loggedInUser == null) {
                            response.sendRedirect("students/login.jsp");
                            return;
                        }
                        Item newItem = extractItemFromRequest(request, student_id);
                        List<String> errors = validateItemData(newItem);

                        if (!errors.isEmpty()) {
                            Vector<String> categories = dao.getAllCategories();
                            request.setAttribute("categories", categories);
                            request.setAttribute("errorMessage", String.join("<br>", errors));
                            request.setAttribute("item", newItem); // Giữ lại thông tin đã nhập
                            request.getRequestDispatcher("/views/items/create.jsp").forward(request, response);
                            return;
                        }

                        int n = dao.addItem(newItem);
                        if (n > 0) {
                            response.sendRedirect("ItemURL?service=listItem");
                        } else {
                            Vector<String> categories = dao.getAllCategories();
                            request.setAttribute("categories", categories);
                            request.setAttribute("errorMessage", "Thêm sản phẩm thất bại. Vui lòng kiểm tra lại thông tin.");
                            request.setAttribute("item", newItem); // Giữ lại thông tin đã nhập
                            request.getRequestDispatcher("/views/items/create.jsp").forward(request, response);
                        }
                    }
                    break;

                case "listItem":
                default:
                    String sql = "SELECT i.*, s.student_id AS s_student_id, s.full_name, s.facebook_url "
                            + "FROM items i "
                            + "JOIN students s ON i.student_id = s.student_id";
                    String searchName = request.getParameter("searchName");
                    String searchCategory = request.getParameter("searchCategory");
                    StringBuilder whereClause = new StringBuilder();
                    if (searchName != null && !searchName.isEmpty()) {
                        whereClause.append(" WHERE i.itemName LIKE N'%" + searchName + "%'");
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
                    Vector<String> categories = dao.getAllCategories();
                    request.setAttribute("items", items);
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

    private Item extractItemFromRequest(HttpServletRequest request, int studentId) {
        String category_name = request.getParameter("category_name");
        String itemName = request.getParameter("itemName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image_url = request.getParameter("image_url");
        Boolean is_for_giveaway = request.getParameter("is_for_giveaway") != null;
        Boolean is_for_rent = request.getParameter("is_for_rent") != null;
        Boolean is_for_sale = request.getParameter("is_for_sale") != null;
        BigDecimal rent_price = parseBigDecimal(request.getParameter("rent_price"));
        BigDecimal sale_price = parseBigDecimal(request.getParameter("sale_price"));
        Date created_at = Date.valueOf(LocalDate.now()); // Set created_at tại server

        return new Item(studentId, category_name, itemName, title, description, image_url,
                is_for_giveaway, is_for_rent, is_for_sale, rent_price, sale_price, created_at);
    }

    private Item extractItemFromUpdateRequest(HttpServletRequest request) {
        int item_id = Integer.parseInt(request.getParameter("item_id"));
        String category_name = request.getParameter("categoryName"); // Đảm bảo khớp với name trong select edit form
        String itemName = request.getParameter("itemName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image_url = request.getParameter("image_url");
        Boolean is_for_giveaway = request.getParameter("is_for_giveaway") != null;
        Boolean is_for_rent = request.getParameter("is_for_rent") != null;
        Boolean is_for_sale = request.getParameter("is_for_sale") != null;
        BigDecimal rent_price = parseBigDecimal(request.getParameter("rent_price"));
        BigDecimal sale_price = parseBigDecimal(request.getParameter("sale_price"));

        Item updatedItem = new Item();
        updatedItem.setItem_id(item_id);
        updatedItem.setCategory_name(category_name);
        updatedItem.setItemName(itemName);
        updatedItem.setTitle(title);
        updatedItem.setDescription(description);
        updatedItem.setImage_url(image_url);
        updatedItem.setIs_for_giveaway(is_for_giveaway);
        updatedItem.setIs_for_rent(is_for_rent);
        updatedItem.setIs_for_sale(is_for_sale);
        updatedItem.setRent_price(rent_price);
        updatedItem.setSale_price(sale_price);
        return updatedItem;
    }

    private List<String> validateItemData(Item item) {
        List<String> errors = new ArrayList<>();

        if (item.getStudent_id() == null || item.getStudent_id() == 0) {
            errors.add("Lỗi: Không xác định được người đăng.");
        }
        if (item.getCategory_name() == null || item.getCategory_name().trim().isEmpty()) {
            errors.add("Vui lòng chọn danh mục sản phẩm.");
        }
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            errors.add("Vui lòng nhập tên sản phẩm.");
        }
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            errors.add("Vui lòng nhập tiêu đề sản phẩm.");
        }
        if (item.getImage_url() == null || item.getImage_url().trim().isEmpty()) {
            errors.add("Vui lòng nhập URL hình ảnh.");
        }

        if (item.getIs_for_rent() != null && item.getIs_for_rent() && item.getRent_price() == null) {
            errors.add("Vui lòng nhập giá thuê.");
        }
        if (item.getIs_for_sale() != null && item.getIs_for_sale() && item.getSale_price() == null) {
            errors.add("Vui lòng nhập giá bán.");
        }

        return errors;
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

    private String handleNull(String value, String defaultValue) {
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
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