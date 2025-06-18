<%@ page import="CALISTO.model.dto.ClienteContaDTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consulta de Cliente</title>
</head>
<body>

<h1>Consulta de Cliente</h1>

<form method="get" action="${pageContext.request.contextPath}/consultaCliente">
    <label>Digite o CPF do cliente:</label>
    <input type="text" name="cpf" required>
    <button type="submit">Consultar</button>
</form>

<%
    List<ClienteContaDTO> resultados = (List<ClienteContaDTO>) request.getAttribute("resultados");
    String cpf = request.getParameter("cpf");

    if (resultados != null && !resultados.isEmpty()) {
%>
<table>
    <tr>
        <th>Nome</th><th>CPF</th><th>Nascimento</th><th>Telefone</th><th>Endereço</th><th>Score</th>
        <th>Conta</th><th>Tipo</th><th>Status</th>
    </tr>
    <% for (ClienteContaDTO r : resultados) { %>
    <tr>
        <td><%= r.getNome() %></td>
        <td><%= r.getCpf() %></td>
        <td><%= r.getDataNascimento() %></td>
        <td><%= r.getTelefone() %></td>
        <td><%= r.getEndereco() %></td>
        <td><%= r.getScoreCredito() %></td>
        <td><%= r.getNumeroConta() != null ? r.getNumeroConta() : "-" %></td>
        <td><%= r.getTipoConta() != null ? r.getTipoConta() : "-" %></td>
        <td><%= r.getStatus() != null ? r.getStatus() : "-" %></td>
    </tr>
    <% } %>
</table>
<%
} else {
%>
<p>Nenhum dado encontrado para este CPF.</p>
<%
    }
%>

</body>
</html>
