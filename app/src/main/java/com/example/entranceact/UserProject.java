package com.example.entranceact;

public class UserProject {
    public String projectKey, projectName;

    public UserProject() {
    }

    public UserProject(String projectKey, String projectName) {
        this.projectKey = projectKey;
        this.projectName = projectName;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
