<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    String cpf = (String) session.getAttribute("cpfLogin");
    if (cliente == null) {
        response.sendRedirect("../login.jsp?cliente");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Portal do Cliente</title>
</head>
<body>
<h1>Bem-vindo, <%= cliente != null ? cliente.getNome() : "Cliente" %>
</h1>
<p>CPF: <%= cpf != null ? cpf : "Não identificado" %>
</p>



<!-- Seu menu de opções aqui -->
<ul>
    <li><a href="saldo.jsp">Consultar Saldo</a></li>
    <li><a href="deposito.jsp">- Depósito</a></li>
    <li><a href="saque.jsp">- Saque</a></li>
    <li><a href="transferencia.jsp">- Transferência</a></li>
    <li><a href="#">- Extrato</a></li>
    <li><a href="#">- Consultar Limite</a></li>
</ul>
</li>
<li><a href="#">2. Encerrar Programa</a></li>
</ul>
</body>
</html>