package com.projectmanagement.dao;

import com.projectmanagement.model.Task;
import com.projectmanagement.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    public boolean create(Task t) throws SQLException {
        String sql="INSERT INTO tasks(project_id,title,description,assigned_to,priority,status,due_date) VALUES(?,?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,t.getProjectId()); ps.setString(2,t.getTitle()); ps.setString(3,t.getDescription());
            if(t.getAssignedTo()==null) ps.setNull(4,Types.INTEGER); else ps.setInt(4,t.getAssignedTo());
            ps.setString(5,t.getPriority()); ps.setString(6,t.getStatus()); ps.setDate(7,t.getDueDate());
            return ps.executeUpdate()==1;
        }
    }
    public List<Task> findByProjectOwner(int projectId, int ownerId) throws SQLException {
        List<Task> tasks=new ArrayList<>();
        String sql="SELECT t.* FROM tasks t JOIN projects p ON t.project_id=p.id WHERE t.project_id=? AND p.owner_id=? ORDER BY t.created_at DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,projectId); ps.setInt(2,ownerId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    Task t=new Task(); t.setId(rs.getInt("id")); t.setProjectId(rs.getInt("project_id"));
                    t.setTitle(rs.getString("title")); t.setDescription(rs.getString("description"));
                    int assigned=rs.getInt("assigned_to"); t.setAssignedTo(rs.wasNull()?null:assigned);
                    t.setPriority(rs.getString("priority")); t.setStatus(rs.getString("status"));
                    t.setDueDate(rs.getDate("due_date")); tasks.add(t);
                }
            }
        }
        return tasks;
    }
    public boolean updateStatus(int taskId, int ownerId, String status) throws SQLException {
        String sql="UPDATE tasks t JOIN projects p ON t.project_id=p.id SET t.status=? WHERE t.id=? AND p.owner_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,status); ps.setInt(2,taskId); ps.setInt(3,ownerId);
            return ps.executeUpdate()==1;
        }
    }
    public boolean delete(int taskId, int ownerId) throws SQLException {
        String sql="DELETE t FROM tasks t JOIN projects p ON t.project_id=p.id WHERE t.id=? AND p.owner_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,taskId); ps.setInt(2,ownerId);
            return ps.executeUpdate()==1;
        }
    }
}
