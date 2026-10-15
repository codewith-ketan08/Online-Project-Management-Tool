package com.projectmanagement.servlet;

import com.projectmanagement.dao.UserDAO;
import com.projectmanagement.model.User;
import com.projectmanagement.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("name"), email=req.getParameter("email"), password=req.getParameter("password");
        if(name==null || email==null || password==null || name.isBlank() || email.isBlank() || password.length()<8){
            req.setAttribute("error","Enter a name, email, and password of at least 8 characters.");
            req.getRequestDispatcher("/register.jsp").forward(req,resp); return;
        }
        try {
            User user=new User(); user.setName(name.trim()); user.setEmail(email.trim().toLowerCase());
            user.setPasswordHash(PasswordUtil.hash(password)); user.setRole("MEMBER");
            userDAO.create(user);
            resp.sendRedirect(req.getContextPath()+"/login.jsp?registered=1");
        } catch (SQLException e) {
            req.setAttribute("error","Could not create account. The email may already be registered.");
            req.getRequestDispatcher("/register.jsp").forward(req,resp);
        }
    }
}
