package com.projectmanagement.dao;

import com.projectmanagement.model.Project;
import com.projectmanagement.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    public boolean create(Project p) throws SQLException {
        String sql="INSERT INTO projects(name,description,start_date,deadline,status,owner_id) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,p.getName()); ps.setString(2,p.getDescription());
            ps.setDate(3,p.getStartDate()); ps.setDate(4,p.getDeadline());
            ps.setString(5,p.getStatus()); ps.setInt(6,p.getOwnerId());
            return ps.executeUpdate()==1;
        }
    }
    public List<Project> findByOwner(int ownerId) throws SQLException {
        List<Project> projects=new ArrayList<>();
        String sql="SELECT * FROM projects WHERE owner_id=? ORDER BY created_at DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,ownerId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()) projects.add(new Project(rs.getInt("id"),rs.getString("name"),
                    rs.getString("description"),rs.getDate("start_date"),rs.getDate("deadline"),
                    rs.getString("status"),rs.getInt("owner_id")));
            }
        }
        return projects;
    }
    public boolean updateStatus(int projectId, int ownerId, String status) throws SQLException {
        String sql="UPDATE projects SET status=? WHERE id=? AND owner_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,status); ps.setInt(2,projectId); ps.setInt(3,ownerId);
            return ps.executeUpdate()==1;
        }
    }
    public boolean delete(int projectId, int ownerId) throws SQLException {
        String sql="DELETE FROM projects WHERE id=? AND owner_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,projectId); ps.setInt(2,ownerId);
            return ps.executeUpdate()==1;
        }
    }
}
