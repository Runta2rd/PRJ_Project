package dao;

import model.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class DAOStudent extends DAOConnect {

    public int addStudent(Student student) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[students] " +
                     "([email], [password], [full_name], [facebook_url]) " +
                     "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, student.getEmail());
            pre.setString(2, student.getPassword());
            pre.setString(3, student.getFull_name());
            pre.setString(4, student.getFacebook_url());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Vector<Student> getStudents(String sql) {
        Vector<Student> vector = new Vector<>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Student student = new Student();
                student.setStudent_id(rs.getInt("student_id"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                student.setFull_name(rs.getString("full_name"));
                student.setFacebook_url("facebook_url");
                vector.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int deleteStudent(int student_id) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[students] WHERE [student_id] = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, student_id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateStudent(Student student) {
        int n = 0;
        String sql = "UPDATE [dbo].[students] " +
                     "SET [email] = ?, [password] = ?, [full_name] = ?, [facebook_url] = ? " +
                     "WHERE [student_id] = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, student.getEmail());
            pre.setString(2, student.getPassword());
            pre.setString(3, student.getFull_name());
            pre.setString(4, student.getFacebook_url());
            pre.setInt(5, student.getStudent_id());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Student getStudentById(int student_id) {
        String sql = "SELECT * FROM [dbo].[students] WHERE [student_id] = " + student_id;
        Vector<Student> vector = getStudents(sql);
        return vector.isEmpty() ? null : vector.get(0);
    }

    public Student getStudentByEmail(String email) {
        String sql = "SELECT * FROM [dbo].[students] WHERE [email] = '" + email + "'";
        Vector<Student> vector = getStudents(sql);
        return vector.isEmpty() ? null : vector.get(0);
    }
    
    public static ArrayList<Student> getAllStudentsForAdmin() {
    DAOConnect db = DAOConnect.getInstance();
    ArrayList<Student> list = new ArrayList<>();

    try {
        String sql = "SELECT * FROM students WHERE student_id > 1";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Student student = new Student();
            student.setStudent_id(rs.getInt("student_id"));
            student.setEmail(rs.getString("email"));
            student.setPassword(rs.getString("password"));
            student.setFull_name(rs.getString("full_name"));
            student.setFacebook_url("facebook_url");
            list.add(student);
        }

        // Đảm bảo đóng các tài nguyên
        if (rs != null) rs.close();
        if (ps != null) ps.close();

    } catch (SQLException e) {
        e.printStackTrace();
        // Có thể log lỗi ở đây
    }

    return list; // Luôn trả về một ArrayList (có thể rỗng)
}
    
}
