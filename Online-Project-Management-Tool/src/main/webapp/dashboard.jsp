<%@ page contentType="text/html;charset=UTF-8" %><%@ page import="java.util.*,com.projectmanagement.model.Project,com.projectmanagement.model.Task" %>
<%
if(session.getAttribute("userId")==null){response.sendRedirect(request.getContextPath()+"/login.jsp");return;}
List<Project> projects=(List<Project>)request.getAttribute("projects");
List<Task> tasks=(List<Task>)request.getAttribute("tasks");
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Dashboard | ProjectFlow</title><link rel="stylesheet" href="css/style.css"></head><body>
<header class="topbar"><div class="brand">Project<span>Flow</span></div><div>Hello, <b><%=session.getAttribute("userName")%></b> &nbsp; <a href="logout">Logout</a></div></header>
<main class="container"><section class="hero"><div><p class="eyebrow">WORKSPACE OVERVIEW</p><h1>Your projects, in one place.</h1><p class="muted">Plan work, track tasks, and keep your team moving.</p></div><a class="btn" href="project-form.jsp">+ New project</a></section>
<section class="stats"><div class="stat"><span>Projects</span><strong><%=request.getAttribute("totalProjects")%></strong></div><div class="stat"><span>Total tasks</span><strong><%=request.getAttribute("totalTasks")%></strong></div><div class="stat"><span>Completed</span><strong><%=request.getAttribute("completedTasks")%></strong></div><div class="stat"><span>Open tasks</span><strong><%=request.getAttribute("pendingTasks")%></strong></div></section>
<section class="section-head"><h2>Projects</h2></section>
<div class="project-grid">
<% if(projects==null||projects.isEmpty()){ %><div class="empty">No projects yet. Create your first project to get started.</div><% } else { for(Project p:projects){ %>
<article class="project-card"><div class="card-top"><span class="badge"><%=p.getStatus().replace('_',' ')%></span><span class="muted">#<%=p.getId()%></span></div><h3><%=p.getName()%></h3><p><%=p.getDescription()==null?"No description added.":p.getDescription()%></p><div class="meta">Deadline: <%=p.getDeadline()==null?"Not set":p.getDeadline()%></div>
<form method="post" action="tasks" class="inline-form"><input type="hidden" name="action" value="create"><input type="hidden" name="projectId" value="<%=p.getId()%>"><input name="title" placeholder="New task title" required><select name="priority"><option>LOW</option><option selected>MEDIUM</option><option>HIGH</option></select><input type="date" name="dueDate" aria-label="Due date"><button class="small-btn">Add task</button></form>
<form method="post" action="projects" class="inline-form"><input type="hidden" name="action" value="status"><input type="hidden" name="id" value="<%=p.getId()%>"><select name="status"><option value="PLANNED" <%=p.getStatus().equals("PLANNED")?"selected":""%>>Planned</option><option value="IN_PROGRESS" <%=p.getStatus().equals("IN_PROGRESS")?"selected":""%>>In progress</option><option value="COMPLETED" <%=p.getStatus().equals("COMPLETED")?"selected":""%>>Completed</option></select><button class="small-btn secondary">Update project</button></form>
<form method="post" action="projects" onsubmit="return confirm('Delete this project and its tasks?')" class="delete-form"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%=p.getId()%>"><button class="text-danger">Delete project</button></form>
<h4>Tasks</h4><div class="task-list">
<% boolean any=false; for(Task t:tasks){if(t.getProjectId()==p.getId()){any=true;%>
<div class="task-row"><div><b><%=t.getTitle()%></b><small><%=t.getPriority()%> priority · Due <%=t.getDueDate()==null?"not set":t.getDueDate()%></small></div><form method="post" action="tasks" class="task-actions"><input type="hidden" name="action" value="status"><input type="hidden" name="id" value="<%=t.getId()%>"><select name="status"><option value="PENDING" <%=t.getStatus().equals("PENDING")?"selected":""%>>Pending</option><option value="IN_PROGRESS" <%=t.getStatus().equals("IN_PROGRESS")?"selected":""%>>In progress</option><option value="COMPLETED" <%=t.getStatus().equals("COMPLETED")?"selected":""%>>Completed</option></select><button class="small-btn">Save</button></form></div>
<% }} if(!any){%><p class="muted">No tasks in this project yet.</p><% } %></div></article>
<% }} %>
</div><footer>ProjectFlow · Java, Servlets, JDBC & MySQL</footer></main></body></html>
