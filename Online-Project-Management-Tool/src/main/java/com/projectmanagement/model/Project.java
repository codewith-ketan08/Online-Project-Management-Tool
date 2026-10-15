package com.projectmanagement.model;

import java.sql.Date;

public class Project {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date deadline;
    private String status;
    private int ownerId;

    public Project() {}
    public Project(int id, String name, String description, Date startDate, Date deadline, String status, int ownerId) {
        this.id=id; this.name=name; this.description=description; this.startDate=startDate;
        this.deadline=deadline; this.status=status; this.ownerId=ownerId;
    }
    public int getId(){return id;} public void setId(int v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Date getStartDate(){return startDate;} public void setStartDate(Date v){startDate=v;}
    public Date getDeadline(){return deadline;} public void setDeadline(Date v){deadline=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public int getOwnerId(){return ownerId;} public void setOwnerId(int v){ownerId=v;}
}
