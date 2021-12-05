package com.example.entranceact;

public class ProjectInfo {
    public String projectName, projectKey, projectCreator;

    public ProjectInfo() {
    }

    public ProjectInfo(String projectName, String projectKey, String projectCreator) {
        this.projectName = projectName;
        this.projectKey = projectKey;
        this.projectCreator = projectCreator;
    }

    public String getProjectName() {return projectName; }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectKey() {
        return projectKey;
    }
    public void setProjectKey(String projectKey) { this.projectKey = projectKey; }

    public String projectCreator() { return projectCreator; }
    public void projectCreator(String projectCreator) { this.projectCreator = projectKey;}
}
