package controller;

import dao.DAOItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.Student;

@WebServlet(name = "InsertItemServlet", urlPatterns = {"/InsertItemServlet"})
public class InsertItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(InsertItemServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 1. Lấy dữ liệu từ request
            Item newItem = getItemFromRequest(request);

            // 2. Kiểm tra dữ liệu
            List<String> errors = validateItemData(newItem);
            if (!errors.isEmpty()) {
                request.setAttribute("errorMessage", String.join("<br>", errors));
                // Chuyển hướng trở lại trang form để hiển thị lỗi
                request.getRequestDispatcher("/views/items/create.jsp").forward(request, response);
                return;
            }

            // 3. Thêm sản phẩm vào database
            DAOItem daoItem = new DAOItem();
            int result = daoItem.addItem(newItem);

            // 4. Xử lý kết quả
            if (result > 0) {
                // Nếu thêm thành công, chuyển hướng đến trang danh sách sản phẩm
                response.sendRedirect(request.getContextPath() + "/ItemURL?service=listItem"); // hoặc trang thành công
            } else {
                // Nếu thêm thất bại, hiển thị thông báo lỗi
                String errorMessage = "Thêm sản phẩm thất bại. Dữ liệu sản phẩm: <br>";
                errorMessage += "Student ID: " + newItem.getStudent_id() + "<br>";
                errorMessage += "Category Name: " + newItem.getCategory_name() + "<br>";
                errorMessage += "Item Name: " + newItem.getItemName() + "<br>";
                errorMessage += "Title: " + newItem.getTitle() + "<br>";
                errorMessage += "Description: " + newItem.getDescription() + "<br>";
                errorMessage += "Image URL: " + newItem.getImage_url() + "<br>";
                errorMessage += "Is For Giveaway: " + newItem.getIs_for_giveaway() + "<br>";
                errorMessage += "Is For Rent: " + newItem.getIs_for_rent() + "<br>";
                errorMessage += "Rent Price: " + newItem.getRent_price() + "<br>";
                errorMessage += "Is For Sale: " + newItem.getIs_for_sale() + "<br>";
                errorMessage += "Sale Price: " + newItem.getSale_price() + "<br>";
                errorMessage += "Created At: " + newItem.getCreated_at();

                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/views/items/create.jsp").forward(request, response); // hoặc trang form
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error inserting item", ex);
            request.setAttribute("errorMessage", "An error occurred: " + ex.getMessage());
            request.getRequestDispatcher("/views/items/create.jsp").forward(request, response); // giữ nguyên trang
        }
    }

    private Item getItemFromRequest(HttpServletRequest request) throws IOException, ServletException {
        // Lấy student_id từ session
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("loggedInUser"); // Đảm bảo 'loggedInUser' đã được set
        int studentId = (student != null) ? student.getStudent_id() : 0; // Handle null student

        // Lấy các tham số khác từ request
        String categoryName = request.getParameter("category_name");
        String itemName = request.getParameter("itemName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("image_url"); // Lấy URL trực tiếp từ request

        // Xử lý checkbox
        String isForGiveawayParam = request.getParameter("is_for_giveaway");
        boolean isForGiveaway = (isForGiveawayParam != null && isForGiveawayParam.equals("true"));

        String isForRentParam = request.getParameter("is_for_rent");
        boolean isForRent = (isForRentParam != null && isForRentParam.equals("true"));
        BigDecimal rentPrice = null;
        if (isForRent) {
            String rentPriceStr = request.getParameter("rent_price");
            if (rentPriceStr != null && !rentPriceStr.isEmpty()) {
                try {
                    rentPrice = new BigDecimal(rentPriceStr);
                } catch (NumberFormatException e) {
                    rentPrice = null; // Set null nếu không phải số hợp lệ
                }
            }
        }

        String isForSaleParam = request.getParameter("is_for_sale");
        boolean isForSale = (isForSaleParam != null && isForSaleParam.equals("true"));
        BigDecimal salePrice = null;
        if (isForSale) {
            String salePriceStr = request.getParameter("sale_price");
            if (salePriceStr != null && !salePriceStr.isEmpty()) {
                try {
                    salePrice = new BigDecimal(salePriceStr);
                } catch (NumberFormatException e) {
                    salePrice = null; // Set null nếu không phải số hợp lệ
                }
            }
        }

        // Lấy ngày hiện tại
        LocalDate now = LocalDate.now();
        Date createedAt = Date.valueOf(now);

        // Tạo đối tượng Item
        Item item = new Item();
        item.setStudent_id(studentId);
        item.setCategory_name(categoryName);
        item.setItemName(itemName);
        item.setTitle(title);
        item.setDescription(description);
        item.setImage_url(imageUrl);  // Sử dụng trực tiếp imageUrl
        item.setIs_for_giveaway(isForGiveaway);
        item.setIs_for_rent(isForRent);
        item.setRent_price(rentPrice);
        item.setIs_for_sale(isForSale);
        item.setSale_price(salePrice);
        item.setCreated_at(createedAt);

        return item;
    }

    private List<String> validateItemData(Item item) {
        List<String> errors = new ArrayList<>();

        if (item.getStudent_id() == 0) {
            errors.add("Vui lòng đăng nhập để đăng bán sản phẩm.");
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

        if (item.getIs_for_rent() && item.getRent_price() == null) {
            errors.add("Vui lòng nhập giá thuê.");
        }
        if (item.getIs_for_sale() && item.getSale_price() == null) {
            errors.add("Vui lòng nhập giá bán.");
        }

        return errors;
    }
}
