<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Login | ProjectFlow</title><link rel="stylesheet" href="css/style.css"></head>
<body><main class="auth-card"><div class="brand">Project<span>Flow</span></div><h1>Welcome back</h1><p class="muted">Sign in to manage your projects and tasks.</p>
<% if(request.getAttribute("error")!=null){ %><div class="alert"><%=request.getAttribute("error")%></div><% } %>
<% if("1".equals(request.getParameter("registered"))){ %><div class="success">Account created. Please sign in.</div><% } %>
<form method="post" action="login"><label>Email</label><input type="email" name="email" required><label>Password</label><input type="password" name="password" required><button class="btn" type="submit">Sign in</button></form>
<p class="muted">New here? <a href="register.jsp">Create an account</a></p></main></body></html>
