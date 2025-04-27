<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.Student"%>
<%
    ArrayList<Student> list = (ArrayList<Student>) request.getAttribute("students");
%>

<h2>Danh sách sinh viên</h2>

<% if (list != null && !list.isEmpty()) { %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Password</th>
        <th>Full Name</th>
    </tr>
    <% for (Student s : list) {%>
    <tr>
        <td><%= s.getStudent_id()%></td>
        <td><%= s.getEmail()%></td>
        <td><%= s.getPassword()%></td>
        <td><%= s.getFull_name()%></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>Không có sinh viên nào để hiển thị.</p>
<% }%>