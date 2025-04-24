package model;

import java.sql.Date;

public class Message {
    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Date createdAt;

    // Default constructor
    public Message() {
    }

    // Constructor with all fields (except messageId which is auto-increment)
    public Message(Integer senderId, Integer receiverId, String content, Date createdAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters
    public Integer getMessageId() {
        return messageId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}