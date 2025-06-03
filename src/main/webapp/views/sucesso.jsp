<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<html>
<head>
  <title>Sucesso</title>
</head>
<body>
<%
  HttpSession session = request.getSession(false);
  if (session == null || session.getAttribute("usuario") == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>

<h2>Login efetuado com sucesso!</h2>
<p>Bem-vindo, <%= session.getAttribute("usuario") %>!</p>

<form action="logout" method="post">
  <button type="submit">Sair</button>
</form>
</body>
</html>
