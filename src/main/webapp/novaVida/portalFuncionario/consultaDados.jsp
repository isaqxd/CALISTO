<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Dados</title>
    <link rel="stylesheet" href="css/portal-cliente.css">
    <meta charset="UTF-8">
</head>
<body>
<div class="portal-container">
    <h2>Consultar Dados</h2>
    <p>Selecione o tipo de informação que deseja consultar:</p>

    <ul class="portal-menu">
        <li><a href="${pageContext.request.contextPath}/consultaCliente">🔎 Consultar Cliente</a></li>
        <li><a href="${pageContext.request.contextPath}/consultaConta">🏦 Consultar Conta</a></li>
        <li><a href="${pageContext.request.contextPath}/consultaFuncionario">👨‍💼 Consultar Funcionário</a></li>
    </ul>
</div>
</body>
</html>