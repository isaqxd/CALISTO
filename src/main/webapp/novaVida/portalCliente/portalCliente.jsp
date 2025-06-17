<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%
    // 1. A ÚNICA LÓGICA JAVA: Verificar se o usuário está na sessão.
    // O objeto "cliente" foi colocado aqui pelo Servlet de Login.
    Cliente cliente = (Cliente) session.getAttribute("cliente");

    // 2. Medida de segurança: se não houver cliente, volta para o login.
    if (cliente == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Portal do Cliente - <%= cliente.getNome() %></title>
    <%-- 3. Link para o arquivo CSS que fará a "mágica" visual --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cliente.css">
</head>
<body>

<%-- 4. ESTRUTURA HTML ORGANIZADA: Um container principal para o conteúdo --%>
<div class="portal-container">
    
    <div class="portal-header">
        <h1>Bem-vindo, <%= cliente.getNome() %></h1>
        <p>CPF: <%= cliente.getCpf() %></p>
    </div>

    <%-- Menu de opções com uma classe para estilização --%>
    <ul class="portal-menu">
        <li><a href="saldo.jsp">Consultar Saldo</a></li>
        <li><a href="deposito.jsp">Depósito</a></li>
        <li><a href="saque.jsp">Saque</a></li>
        <li><a href="transferencia.jsp">Transferência</a></li>
        <li><a href="extrato.jsp">Extrato</a></li>
        <li><a href="limite.jsp">Consultar Limite</a></li>
        <%-- 5. Link de logout corrigido e com uma classe para estilo diferente --%>
        <li class="logout-link"><a href="${pageContext.request.contextPath}/logout">Encerrar Sessão</a></li>
    </ul>

</div>

</body>
</html>