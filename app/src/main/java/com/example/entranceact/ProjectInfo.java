package com.example.entranceact;

public class ProjectInfo {
    public String projectName, projectKey;

    public ProjectInfo() {
    }

    public ProjectInfo(String projectName, String projectKey) {
        this.projectName = projectName;
        this.projectKey = projectKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }
}
