<%@ page import="CALISTO.model.persistence.Usuario.Funcionario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
    if (funcionario == null) {
        response.sendRedirect(request.getContextPath() + "/novaVida/login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Consulta de Dados</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/portalfuncionario.css" />
</head>
<body>
<div class="background-layer bg-funcionario"></div>

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/img/image.svg" class="logo-image" alt="Logo"/>
    </div>
</header>

<div class="app-container">
    <nav class="sidebar">
        <div class="sidebar-header">
            <h3>Menu</h3>
        </div>
        <div class="sidebar-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/consultaConta"><span class="menu-icon">💳</span><span>Contas</span></a></li>
                <li><a href="${pageContext.request.contextPath}/consultaCliente"><span class="menu-icon">👤</span><span>Clientes</span></a></li>
                <li><a href="${pageContext.request.contextPath}/consultaFuncionario"><span class="menu-icon">🧑‍💼</span><span>Funcionários</span></a></li>
            </ul>
        </div>
    </nav>
</div>

<footer class="app-footer">
    &copy; 2025 - CALISTO
</footer>
</body>
</html>
