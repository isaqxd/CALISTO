<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Login</title>
  <script>
    window.onload = function () {
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has('erro')) {
        alert(urlParams.get('erro'));
      }
      if (urlParams.get('sucesso') === 'cadastro') {
        alert('Cadastro realizado com sucesso! Agora faça login.');
      }
    }
  </script>
</head>
<body>
<h2>Login</h2>

<form action="${pageContext.request.contextPath}/login" method="post">
  Email: <input type="email" name="email" required><br><br>
  Senha: <input type="password" name="senha" required minlength="4"><br><br>
  <button type="submit">Entrar</button>
</form>

<p>Não tem conta? <a href="${pageContext.request.contextPath}/views/cadastro.jsp">Cadastre-se</a></p>
</body>
</html>