package controller;

import dao.DAOItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UpdateItemServlet", urlPatterns = {"/UpdateItemServlet"})
public class UpdateItemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Get parameters
        String itemIdStr = request.getParameter("item_id");
        String itemName = request.getParameter("itemName");
        String categoryName = request.getParameter("categoryName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("image_url");
        String rentPriceStr = request.getParameter("rent_price");
        String salePriceStr = request.getParameter("sale_price");

        boolean isForGiveaway = request.getParameter("is_for_giveaway") != null;
        boolean isForRent = request.getParameter("is_for_rent") != null;
        boolean isForSale = request.getParameter("is_for_sale") != null;

        Map<String, String> errors = new HashMap<>();

        // Validate data
        if (itemName == null || itemName.trim().isEmpty()) {
            errors.put("itemName", "Tên sản phẩm không được để trống.");
        }
        if (categoryName == null || categoryName.trim().isEmpty()) {
            errors.put("categoryName", "Danh mục không được để trống.");
        }

        BigDecimal rentPrice = BigDecimal.ZERO;
        BigDecimal salePrice = BigDecimal.ZERO;

        try {
            if (rentPriceStr != null && !rentPriceStr.trim().isEmpty()) {
                rentPrice = new BigDecimal(rentPriceStr);
                if (rentPrice.compareTo(BigDecimal.ZERO) < 0) {
                    errors.put("rent_price", "Giá cho thuê phải >= 0.");
                }
            }
        } catch (NumberFormatException e) {
            errors.put("rent_price", "Giá cho thuê không hợp lệ.");
        }

        try {
            if (salePriceStr != null && !salePriceStr.trim().isEmpty()) {
                salePrice = new BigDecimal(salePriceStr);
                if (salePrice.compareTo(BigDecimal.ZERO) < 0) {
                    errors.put("sale_price", "Giá bán phải >= 0.");
                }
            }
        } catch (NumberFormatException e) {
            errors.put("sale_price", "Giá bán không hợp lệ.");
        }

        int itemId = -1;
        try {
            itemId = Integer.parseInt(itemIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }

        DAOItem daoItem = new DAOItem();
        Item existingItem = daoItem.getItemById(itemId);

        if (existingItem == null) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }

        if (!errors.isEmpty()) {
            // Nếu có lỗi, chuyển lại form
            request.setAttribute("errors", errors);
            request.setAttribute("item", existingItem);
            request.setAttribute("categories", daoItem.getAllCategories());
            request.getRequestDispatcher("/views/items/edit.jsp").forward(request, response);
            return;
        }

        // Update item
        existingItem.setItemName(itemName);
        existingItem.setCategory_name(categoryName);
        existingItem.setTitle(title);
        existingItem.setDescription(description);
        existingItem.setImage_url(imageUrl);
        existingItem.setIs_for_giveaway(isForGiveaway);
        existingItem.setIs_for_rent(isForRent);
        existingItem.setIs_for_sale(isForSale);
        existingItem.setRent_price(rentPrice);
        existingItem.setSale_price(salePrice);

        int n = daoItem.updateItem(existingItem);

        if (n > 0) {
            // Update thành công
            response.sendRedirect(request.getContextPath() + "/ItemURL?service=listItem");
        } else {
            // Update thất bại
            request.setAttribute("errorMessage", "Cập nhật sản phẩm thất bại. Vui lòng thử lại!");
            request.setAttribute("item", existingItem);
            request.setAttribute("categories", daoItem.getAllCategories());
            request.getRequestDispatcher("/views/items/edit.jsp").forward(request, response);
        }
    }
}
