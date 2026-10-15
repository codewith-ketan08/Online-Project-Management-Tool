package com.projectmanagement.dao;

import com.projectmanagement.model.User;
import com.projectmanagement.util.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean create(User user) throws SQLException {
        String sql = "INSERT INTO users(name,email,password_hash,role) VALUES(?,?,?,?)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,user.getName()); ps.setString(2,user.getEmail());
            ps.setString(3,user.getPasswordHash()); ps.setString(4,user.getRole());
            return ps.executeUpdate()==1;
        }
    }
    public User findByEmail(String email) throws SQLException {
        String sql="SELECT id,name,email,password_hash,role FROM users WHERE email=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,email);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()) return new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),
                    rs.getString("password_hash"),rs.getString("role"));
            }
        }
        return null;
    }
}
