<%@ page import="CALISTO.model.dto.RelatorioContaDto" %>
<%@ page import="java.util.List" %>
<%@ page import="CALISTO.model.persistence.Usuario.Funcionario" %>
<%
    Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
    if (funcionario == null) {
        response.sendRedirect(request.getContextPath() + "/novaVida/login.jsp");
        return;
    }
%>
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
    List<RelatorioContaDto> resultados = (List<RelatorioContaDto>) request.getAttribute("resultados");
    String cpf = request.getParameter("cpf");

    if (resultados != null && !resultados.isEmpty()) {
%>
<table>
    <tr>
        <th>Nome</th><th>CPF</th><th>Nascimento</th><th>Telefone</th><th>Endereço</th><th>Score</th>
        <th>Conta</th><th>Tipo</th><th>Status</th>
    </tr>
    <% for (RelatorioContaDto r : resultados) { %>
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
