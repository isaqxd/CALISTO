<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Encerrar Contas do Cliente</title>
</head>
<body>
<h2>Buscar Cliente por CPF</h2>
<form method="get" action="encerrarContas">
    <label>CPF:</label>
    <input type="text" name="cpf" required>
    <button type="submit">Buscar Contas</button>
</form>

<hr>

<% if (cliente != null) { %>
<h3>Cliente encontrado:</h3>
<p><strong>Nome:</strong> <%= cliente.getNome() %>
</p>
<p><strong>CPF:</strong> <%= cliente.getCpf() %>
</p>

<h4>Contas associadas:</h4>
<ul>
    <% for (Conta conta : cliente.getContas()) { %>
    <li>
        Tipo: <%= conta.getTipoConta() %> |
        Número: <%= conta.getNumeroConta() %> |
        Saldo: R$ <%= conta.getSaldo() %> |
        Status: <%= conta.getStatus() %>
    </li>
    <% } %>
</ul>
<% } else if (request.getParameter("cpf") != null) { %>
<p><strong>Nenhum cliente encontrado com esse CPF.</strong></p>
<% } %>
</body>
</html>
