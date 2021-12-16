package com.example.entranceact;

public class ProjectUsers {
    public String login, name, userColor;

    public ProjectUsers(String login, String name, String userColor

    )
                        {
        this.login = login;
        this.name = name;
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


    public String getUserColor() {
        return userColor;
    }
    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }
}
