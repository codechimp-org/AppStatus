package org.codechimp.appstatus.objects;

public class Status {
    private String message;
    private String priority;

    public Status() {
    }

    public Status(String message, String priority) {
        this.message = message;
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
