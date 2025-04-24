package controller;

import model.Message;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;
import dao.DAOMessage;
import java.sql.Date;

@WebServlet(name = "MessageController", urlPatterns = {"/MessageURL"})
public class MessageController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOMessage dao = new DAOMessage();
        String service = request.getParameter("service");
        if (service == null) {
            service = "listMessage";
        }

        try {
            if (service.equals("deleteMessage")) {
                int messageId = Integer.parseInt(request.getParameter("messageId"));
                dao.deleteMessage(messageId);
                response.sendRedirect("MessageURL?service=listMessage");
            } else if (service.equals("updateMessage")) {
                String submit = request.getParameter("submit");
                if (submit == null) { // Show update form
                    int messageId = Integer.parseInt(request.getParameter("messageId"));
                    Message message = dao.getMessageById(messageId);
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("/jsp/updateMessage.jsp").forward(request, response);
                } else { // Process update submission
                    int messageId = Integer.parseInt(request.getParameter("messageId"));
                    int senderId = Integer.parseInt(request.getParameter("senderId"));
                    int receiverId = Integer.parseInt(request.getParameter("receiverId"));
                    String content = request.getParameter("content");
                    Date createdAt = parseDate(request.getParameter("createdAt"));

                    Message updatedMessage = new Message(senderId, receiverId, content, createdAt);
                    updatedMessage.setMessageId(messageId);
                    dao.updateMessage(updatedMessage);
                    response.sendRedirect("MessageURL?service=listMessage");
                }
            } else if (service.equals("insertMessage")) {
                String submit = request.getParameter("submit");
                if (submit == null) { // Show insert form
                    request.getRequestDispatcher("/jsp/insertMessage.jsp").forward(request, response);
                } else { // Process insert submission
                    int senderId = Integer.parseInt(request.getParameter("senderId"));
                    int receiverId = Integer.parseInt(request.getParameter("receiverId"));
                    String content = request.getParameter("content");
                    Date createdAt = parseDate(request.getParameter("createdAt"));

                    Message newMessage = new Message(senderId, receiverId, content, createdAt);
                    dao.addMessage(newMessage);
                    response.sendRedirect("MessageURL?service=listMessage");
                }
            } else if (service.equals("listMessage")) {
                String sql = "SELECT * FROM messages";
                Vector<Message> messages = dao.getMessages(sql);
                request.setAttribute("messages", messages);
                request.getRequestDispatcher("/jsp/messageList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
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
        return "MessageController Servlet";
    }
}