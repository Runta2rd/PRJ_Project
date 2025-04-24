package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Item {
    
    private Integer item_id;
    private Integer student_id;
    private Student students; // Thêm thuộc tính Student
    private String category_name;
    private String itemName;
    private String title;
    private String description;
    private String image_url;
    private Boolean is_for_giveaway;
    private Boolean is_for_rent;
    private Boolean is_for_sale;
    private BigDecimal rent_price;
    private BigDecimal sale_price;
    private Date created_at;

    // Default constructor
    public Item() {
    }

    // Constructor with all fields (except item_id which is auto-increment)
    public Item(Integer student_id, String category_name, String itemName, String title, String description, String image_url, Boolean is_for_giveaway, Boolean is_for_rent, Boolean is_for_sale, BigDecimal rent_price, BigDecimal sale_price, Date created_at) {
        this.student_id = student_id;
        this.category_name = category_name;
        this.itemName = itemName;
        this.title = title;
        this.description = description;
        this.image_url = image_url;
        this.is_for_giveaway = is_for_giveaway;
        this.is_for_rent = is_for_rent;
        this.is_for_sale = is_for_sale;
        this.rent_price = rent_price;
        this.sale_price = sale_price;
        this.created_at = created_at;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getItemName() {
        return itemName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public Boolean getIs_for_giveaway() {
        return is_for_giveaway;
    }

    public Boolean getIs_for_rent() {
        return is_for_rent;
    }

    public Boolean getIs_for_sale() {
        return is_for_sale;
    }

    public BigDecimal getRent_price() {
        return rent_price;
    }

    public BigDecimal getSale_price() {
        return sale_price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setIs_for_giveaway(Boolean is_for_giveaway) {
        this.is_for_giveaway = is_for_giveaway;
    }

    public void setIs_for_rent(Boolean is_for_rent) {
        this.is_for_rent = is_for_rent;
    }

    public void setIs_for_sale(Boolean is_for_sale) {
        this.is_for_sale = is_for_sale;
    }

    public void setRent_price(BigDecimal rent_price) {
        this.rent_price = rent_price;
    }

    public void setSale_price(BigDecimal sale_price) {
        this.sale_price = sale_price;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    // Getter for students
    public Student getStudents() {
        return students;
    }

    // Setter for students
    public void setStudents(Student students) {
        this.students = students;
    }
}