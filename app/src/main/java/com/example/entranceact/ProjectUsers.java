package com.example.entranceact;

public class ProjectUsers {
    public String login, name, password, email, projectKey, userColor;

    public ProjectUsers() {
    }

    public ProjectUsers(String login, String name, String password, String email, String projectKey, String userColor) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.email = email;
        this.projectKey = projectKey;
        this.userColor = userColor;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }
}
