<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Register | ProjectFlow</title><link rel="stylesheet" href="css/style.css"></head>
<body><main class="auth-card"><div class="brand">Project<span>Flow</span></div><h1>Create account</h1><p class="muted">Start organizing your work.</p>
<% if(request.getAttribute("error")!=null){ %><div class="alert"><%=request.getAttribute("error")%></div><% } %>
<form method="post" action="register"><label>Full name</label><input name="name" maxlength="100" required><label>Email</label><input type="email" name="email" maxlength="150" required><label>Password (at least 8 characters)</label><input type="password" name="password" minlength="8" required><button class="btn" type="submit">Create account</button></form>
<p class="muted">Already registered? <a href="login.jsp">Sign in</a></p></main></body></html>
