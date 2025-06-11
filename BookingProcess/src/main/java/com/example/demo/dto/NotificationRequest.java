package com.example.demo.dto;
public class NotificationRequest {
    private String recipient;
    private String subject;
    private String message;

    // ✅ Add getters, setters, no-args constructor
    public NotificationRequest() {}

    public NotificationRequest(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
