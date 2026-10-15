package com.projectmanagement.model;

import java.sql.Date;

public class Task {
    private int id, projectId;
    private String title, description, priority, status;
    private Integer assignedTo;
    private Date dueDate;

    public Task() {}
    public int getId(){return id;} public void setId(int v){id=v;}
    public int getProjectId(){return projectId;} public void setProjectId(int v){projectId=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getPriority(){return priority;} public void setPriority(String v){priority=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Integer getAssignedTo(){return assignedTo;} public void setAssignedTo(Integer v){assignedTo=v;}
    public Date getDueDate(){return dueDate;} public void setDueDate(Date v){dueDate=v;}
}
