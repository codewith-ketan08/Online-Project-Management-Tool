package com.projectmanagement.servlet;

import com.projectmanagement.model.Project;
import com.projectmanagement.model.Task;
import com.projectmanagement.service.ProjectService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final ProjectService service=new ProjectService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("userId")==null){resp.sendRedirect(req.getContextPath()+"/login.jsp");return;}
        int userId=(Integer)session.getAttribute("userId");
        try {
            List<Project> projects=service.projectsFor(userId);
            int totalTasks=0, completed=0;
            List<Task> allTasks=new ArrayList<>();
            for(Project p:projects){
                List<Task> tasks=service.tasksFor(p.getId(),userId);
                allTasks.addAll(tasks);
            }
            totalTasks=allTasks.size();
            for(Task t:allTasks) if("COMPLETED".equals(t.getStatus())) completed++;
            req.setAttribute("projects",projects); req.setAttribute("tasks",allTasks);
            req.setAttribute("totalProjects",projects.size()); req.setAttribute("totalTasks",totalTasks);
            req.setAttribute("completedTasks",completed); req.setAttribute("pendingTasks",totalTasks-completed);
            req.getRequestDispatcher("/dashboard.jsp").forward(req,resp);
        } catch(SQLException e){throw new ServletException("Could not load dashboard.",e);}
    }
}
