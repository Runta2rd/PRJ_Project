package dao;

import model.Message;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOMessage extends DAOConnect {

    public int addMessage(Message message) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[messages] " +
                     "([sender_id], [receiver_id], [content], [created_at]) " +
                     "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, message.getSenderId());
            pre.setInt(2, message.getReceiverId());
            pre.setString(3, message.getContent());
            pre.setDate(4, message.getCreatedAt());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Vector<Message> getMessages(String sql) {
        Vector<Message> vector = new Vector<>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Message message = new Message();
                message.setMessageId(rs.getInt("message_id"));
                message.setSenderId(rs.getInt("sender_id"));
                message.setReceiverId(rs.getInt("receiver_id"));
                message.setContent(rs.getString("content"));
                message.setCreatedAt(rs.getDate("created_at"));
                vector.add(message);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int deleteMessage(int messageId) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[messages] WHERE [message_id] = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, messageId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateMessage(Message message) {
        int n = 0;
        String sql = "UPDATE [dbo].[messages] " +
                     "SET [sender_id] = ?, [receiver_id] = ?, [content] = ?, [created_at] = ? " +
                     "WHERE [message_id] = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, message.getSenderId());
            pre.setInt(2, message.getReceiverId());
            pre.setString(3, message.getContent());
            pre.setDate(4, message.getCreatedAt());
            pre.setInt(5, message.getMessageId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Message getMessageById(int messageId) {
        String sql = "SELECT * FROM [dbo].[messages] WHERE [message_id] = " + messageId;
        Vector<Message> vector = getMessages(sql);
        return vector.isEmpty() ? null : vector.get(0);
    }
}