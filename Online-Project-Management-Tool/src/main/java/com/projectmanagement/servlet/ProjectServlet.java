package com.projectmanagement.servlet;

import com.projectmanagement.dao.ProjectDAO;
import com.projectmanagement.model.Project;
import com.projectmanagement.service.ProjectService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private final ProjectService service=new ProjectService();
    private final ProjectDAO dao=new ProjectDAO();

    private Integer authenticatedUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s=req.getSession(false);
        if(s==null || s.getAttribute("userId")==null){resp.sendRedirect(req.getContextPath()+"/login.jsp");return null;}
        return (Integer)s.getAttribute("userId");
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        Integer uid=authenticatedUser(req,resp); if(uid==null)return;
        String action=req.getParameter("action");
        try{
            if("create".equals(action)){
                Project p=new Project(); p.setName(req.getParameter("name")); p.setDescription(req.getParameter("description"));
                p.setStartDate(parseDate(req.getParameter("startDate"))); p.setDeadline(parseDate(req.getParameter("deadline")));
                p.setStatus("PLANNED"); p.setOwnerId(uid);
                service.createProject(p);
            }else if("status".equals(action)){
                String status=req.getParameter("status");
                if(!java.util.Set.of("PLANNED","IN_PROGRESS","COMPLETED").contains(status)) throw new IllegalArgumentException("Invalid project status");
                dao.updateStatus(Integer.parseInt(req.getParameter("id")),uid,status);
            }else if("delete".equals(action)){
                dao.delete(Integer.parseInt(req.getParameter("id")),uid);
            }
            resp.sendRedirect(req.getContextPath()+"/dashboard");
        }catch(SQLException|IllegalArgumentException e){
            req.setAttribute("error","Project action failed: "+e.getMessage());
            req.getRequestDispatcher("/project-form.jsp").forward(req,resp);
        }
    }
    private Date parseDate(String value){return value==null||value.isBlank()?null:Date.valueOf(value);}
}
