<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="CALISTO.model.dto.FuncionarioContasDto" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consulta de Funcionario</title>
</head>
<body>

<h1>Consulta de Funcionario</h1>

<form method="get" action="${pageContext.request.contextPath}/consultaFuncionario">
    <label>Digite o CPF do cliente:</label>
    <input type="text" name="cpf" required>
    <button type="submit">Consultar</button>
</form>

<%
    List<FuncionarioContasDto> resultados = (List<FuncionarioContasDto>) request.getAttribute("resultados");
    String cpf = request.getParameter("cpf");

    if (resultados != null && !resultados.isEmpty()) {
%>
<h2>Resultado</h2>
<table>
    <tr>
        <th>Código</th>
        <th>Nome</th>
        <th>CPF</th>
        <th>Data Nascimento</th>
        <th>Telefone</th>
        <th>Cargo</th>
        <th>Endereço</th>
        <th>Contas Abertas</th>
    </tr>
    <%
        for (FuncionarioContasDto r : resultados) {
    %>
    <tr>
        <td><%= r.getCodigoFuncionario() %></td>
        <td><%= r.getNome() %></td>
        <td><%= r.getCpf() %></td>
        <td><%= r.getDataNascimento() %></td>
        <td><%= r.getTelefone() %></td>
        <td><%= r.getCargo() %></td>
        <td><%= r.getEnderecoCompleto() %></td>
        <td><%= r.getContasAbertas() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
} else if (cpf != null) {
%>
<p>Nenhum resultado encontrado para o CPF informado.</p>
<%
    }
%>
</body>
</html>
