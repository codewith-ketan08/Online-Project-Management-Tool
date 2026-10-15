package com.projectmanagement.servlet;

import com.projectmanagement.dao.UserDAO;
import com.projectmanagement.model.User;
import com.projectmanagement.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("email"), password=req.getParameter("password");
        try {
            User user=(email==null)?null:userDAO.findByEmail(email.trim().toLowerCase());
            if(user!=null && password!=null && PasswordUtil.verify(password,user.getPasswordHash())){
                HttpSession session=req.getSession(true);
                session.setAttribute("userId",user.getId());
                session.setAttribute("userName",user.getName());
                session.setAttribute("userRole",user.getRole());
                session.setMaxInactiveInterval(30*60);
                resp.sendRedirect(req.getContextPath()+"/dashboard"); return;
            }
            req.setAttribute("error","Invalid email or password.");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        } catch(SQLException e) {
            throw new ServletException("Database error while logging in. Check database setup.",e);
        }
    }
}
