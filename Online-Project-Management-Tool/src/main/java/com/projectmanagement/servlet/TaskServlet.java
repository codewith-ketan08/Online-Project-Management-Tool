package com.projectmanagement.servlet;

import com.projectmanagement.dao.TaskDAO;
import com.projectmanagement.model.Task;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private final TaskDAO dao=new TaskDAO();
    private Integer authenticatedUser(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        HttpSession s=req.getSession(false);
        if(s==null||s.getAttribute("userId")==null){resp.sendRedirect(req.getContextPath()+"/login.jsp");return null;}
        return (Integer)s.getAttribute("userId");
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        Integer uid=authenticatedUser(req,resp);if(uid==null)return;
        String action=req.getParameter("action");
        try{
            if("create".equals(action)){
                Task t=new Task(); t.setProjectId(Integer.parseInt(req.getParameter("projectId")));
                t.setTitle(req.getParameter("title")); t.setDescription(req.getParameter("description"));
                t.setPriority(req.getParameter("priority")); t.setStatus("PENDING");
                String due=req.getParameter("dueDate"); t.setDueDate(due==null||due.isBlank()?null:Date.valueOf(due));
                if(t.getTitle()==null||t.getTitle().isBlank()) throw new IllegalArgumentException("Task title is required.");
                dao.create(t);
            }else if("status".equals(action)){
                String status=req.getParameter("status");
                if(!java.util.Set.of("PENDING","IN_PROGRESS","COMPLETED").contains(status)) throw new IllegalArgumentException("Invalid task status");
                dao.updateStatus(Integer.parseInt(req.getParameter("id")),uid,status);
            }else if("delete".equals(action)){
                dao.delete(Integer.parseInt(req.getParameter("id")),uid);
            }
            resp.sendRedirect(req.getContextPath()+"/dashboard");
        }catch(SQLException|IllegalArgumentException e){
            throw new ServletException("Task action failed: "+e.getMessage(),e);
        }
    }
}
