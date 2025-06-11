<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<%
    String msg = request.getParameter("msg");
    if ("erro".equals(msg)) {
%>
<p style="color:red;">CPF ou senha incorretos!</p>
<%
    }
%>

<h1>Login</h1>
<form action="${pageContext.request.contextPath}/loginCliente" method="post">

    <input type="hidden" name="tipo_usuario" value="CLIENTE">
    <label>CPF:</label><br>
    <input type="text" name="cpf" required><br><br>

    <label>Senha:</label><br>
    <input type="password" name="senha" required><br><br>

    <button type="submit">Entrar</button>
</form>

</body>
</html>