<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/encerrar.css">
    <title>Encerrar Contas do Cliente</title>
</head>
<body>
<h2>Buscar Cliente por CPF</h2>
<form method="get" action="encerrarContas">
    <label>CPF:</label>
    <input type="text" name="cpf" required>
    <button type="submit">Buscar Contas</button>
</form>

<h4>Contas associadas:</h4>
<div>
    <form action="encerrarContas" method="post">
        <% if (cliente != null) { %>
        <% cliente.getIdCliente(); %>
        <label>Nome: <input readonly value="<%= cliente.getNome()%>"></label><br>
        <label>CPF: <input readonly value="<%= cliente.getCpf() %>"></label><br>
        <% for (Conta conta : cliente.getContas()) { %>
        <label>Tipo:<input name="tipo_conta" readonly value=" <%= conta.getTipoConta() %>"></label>
        <label>Número: <input readonly value="<%= conta.getNumeroConta() %>"></label>
        <label>Saldo: R$ <input readonly value="<%= conta.getSaldo() %>"></label>
        <label>Status:
            <select>
                <option name="status" value="<%= conta.getStatus() %>"><%= conta.getStatus()%>
                </option>
                <option value="ENCERRADA">Encerrada</option>
            </select>
        </label>
        <button type="submit" name="conta">Alterar Status Conta</button>
        <br>
    </form>

    <% } %>

    <% } else if (request.getParameter("cpf") != null) { %>
    <p><strong>Nenhum cliente encontrado com esse CPF.</strong></p>
    <% } %>
</div>
</body>
</html>
