<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Cadastro</title>
</head>
<body>
<h2>Cadastro de Usuário</h2>

<c:if test="${not empty param.erro}">
  <p style="color:red">${param.erro}</p>
</c:if>

<form action="${pageContext.request.contextPath}/cadastro" method="post">
  Nome: <input type="text" name="nome" required><br><br>
  Email: <input type="email" name="email" required><br><br>
  Senha: <input type="password" name="senha" required minlength="4"><br><br>
  <button type="submit">Cadastrar</button>
</form>

<p>Já tem conta? <a href="${pageContext.request.contextPath}/views/login.jsp">Faça login</a></p>
</body>
</html>