package dao;

import model.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import model.Student;

public class DAOItem extends DAOConnect {

    public int addItem(Item item) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[items] " +
                     "([student_id], [category_name], [itemName], [title], [description], " +
                     "[image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], " +
                     "[rent_price], [sale_price], [created_at]) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, item.getStudent_id());
            pre.setString(2, item.getCategory_name());
            pre.setString(3, item.getItemName());
            pre.setString(4, item.getTitle());
            pre.setString(5, item.getDescription());
            pre.setString(6, item.getImage_url());
            pre.setBoolean(7, item.getIs_for_giveaway() != null ? item.getIs_for_giveaway() : false);
            pre.setBoolean(8, item.getIs_for_rent() != null ? item.getIs_for_rent() : false);
            pre.setBoolean(9, item.getIs_for_sale() != null ? item.getIs_for_sale() : false);
            pre.setBigDecimal(10, item.getRent_price());
            pre.setBigDecimal(11, item.getSale_price());
            pre.setDate(12, item.getCreated_at());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateItem(Item item) {
        int n = 0;
        String sql = "UPDATE [dbo].[items] " +
                     "SET [student_id] = ?, [category_name] = ?, [itemName] = ?, [title] = ?, " +
                     "[description] = ?, [image_url] = ?, [is_for_giveaway] = ?, [is_for_rent] = ?, " +
                     "[is_for_sale] = ?, [rent_price] = ?, [sale_price] = ?, [created_at] = ? " +
                     "WHERE [item_id] = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, item.getStudent_id());
            pre.setString(2, item.getCategory_name());
            pre.setString(3, item.getItemName());
            pre.setString(4, item.getTitle());
            pre.setString(5, item.getDescription());
            pre.setString(6, item.getImage_url());
            pre.setBoolean(7, item.getIs_for_giveaway());
            pre.setBoolean(8, item.getIs_for_rent());
            pre.setBoolean(9, item.getIs_for_sale());
            pre.setBigDecimal(10, item.getRent_price());
            pre.setBigDecimal(11, item.getSale_price());
            pre.setDate(12, item.getCreated_at());
            pre.setInt(13, item.getItem_id());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int deleteItem(int item_id) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[items] WHERE [item_id] = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, item_id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Sửa phương thức getItems để lấy cả thông tin sinh viên
    public Vector<Item> getItems(String sql) {
        Vector<Item> vector = new Vector<>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Item item = new Item();
                item.setItem_id(rs.getInt("item_id"));
                item.setStudent_id(rs.getInt("student_id"));
                item.setCategory_name(rs.getString("category_name"));
                item.setItemName(rs.getString("itemName"));
                item.setTitle(rs.getString("title"));
                item.setDescription(rs.getString("description"));
                item.setImage_url(rs.getString("image_url"));
                item.setIs_for_giveaway(rs.getBoolean("is_for_giveaway"));
                item.setIs_for_rent(rs.getBoolean("is_for_rent"));
                item.setIs_for_sale(rs.getBoolean("is_for_sale"));
                item.setRent_price(rs.getBigDecimal("rent_price"));
                item.setSale_price(rs.getBigDecimal("sale_price"));
                item.setCreated_at(rs.getDate("created_at"));

                Student student = new Student();
                student.setStudent_id(rs.getInt("s_student_id")); // Alias từ JOIN
                student.setFull_name(rs.getString("full_name"));   // Alias từ JOIN
                item.setStudents(student); // Set đối tượng Student vào Item

                vector.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    // Sửa phương thức getItemById để lấy cả thông tin sinh viên
    public Item getItemById(int item_id) {
        String sql = "SELECT i.*, s.student_id AS s_student_id, s.full_name " +
                     "FROM items i " +
                     "JOIN students s ON i.student_id = s.student_id " +
                     "WHERE i.item_id = " + item_id;
        Vector<Item> vector = getItems(sql);
        return vector.isEmpty() ? null : vector.get(0);
    }
    
    // Phương thức mới để lấy tất cả items với thông tin sinh viên
    public Vector<Item> getAllItemsWithStudent() {
        String sql = "SELECT i.*, s.student_id AS s_student_id, s.full_name " +
                     "FROM items i " +
                     "JOIN students s ON i.student_id = s.student_id";
        return getItems(sql);
    }
    
    public Vector<String> getAllCategories() {
        Vector<String> categories = new Vector<>();
        String sql = "SELECT DISTINCT category_name FROM items ORDER BY category_name";
        try {
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                if (categoryName != null && !categoryName.isEmpty()) {
                    categories.add(categoryName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categories;
    }
}
