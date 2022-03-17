package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

public class Conversation {
    private String assistant;
    private String user;

    @Override
    public String toString() {
        return "Conversation{" +
                "app='" + assistant + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public Conversation(String assistant, String user) {
        this.assistant = assistant;
        this.user = user;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
