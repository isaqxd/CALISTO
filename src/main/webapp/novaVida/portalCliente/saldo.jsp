<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("novaVida/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Saldo da Conta</title>
    <style>
        .conta-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
        }

        .saldo {
            font-size: 1.2em;
            font-weight: bold;
            color: #2a6496;
        }
    </style>
</head>
<body>
<h1>Olá, <%= cliente.getNome() %>!</h1>
<h2>Seus saldos:</h2>

<% if (cliente.getContas() != null && !cliente.getContas().isEmpty()) { %>
<% for (Conta conta : cliente.getContas()) { %>
<div class="conta-card">
    <h3>Conta <%= conta.getTipoConta().toString().toLowerCase() %>
    </h3>
    <p>Número: <%= conta.getNumeroConta() %>
    </p>
    <p>Status: <%= conta.getStatus() %>
    </p>
    <p class="saldo">Saldo: R$ <%= String.format("%,.2f", conta.getSaldo()) %>
    </p>
</div>
<% } %>
<% } else { %>
<p>Nenhuma conta encontrada para este cliente.</p>
<% } %>
</body>
</html>