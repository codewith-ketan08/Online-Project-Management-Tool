package com.projectmanagement.service;

import com.projectmanagement.dao.ProjectDAO;
import com.projectmanagement.dao.TaskDAO;
import com.projectmanagement.model.Project;
import com.projectmanagement.model.Task;
import java.sql.SQLException;
import java.util.List;

public class ProjectService {
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final TaskDAO taskDAO = new TaskDAO();

    public boolean createProject(Project project) throws SQLException {
        if (project.getName()==null || project.getName().trim().length()<3)
            throw new IllegalArgumentException("Project name must contain at least 3 characters.");
        project.setName(project.getName().trim());
        if (project.getStatus()==null) project.setStatus("PLANNED");
        return projectDAO.create(project);
    }
    public List<Project> projectsFor(int ownerId) throws SQLException {
        return projectDAO.findByOwner(ownerId);
    }
    public List<Task> tasksFor(int projectId, int ownerId) throws SQLException {
        return taskDAO.findByProjectOwner(projectId,ownerId);
    }
}
