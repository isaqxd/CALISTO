<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="CALISTO.model.dto.RelatorioTransacaoDto" %>
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
    <title>Consulta de Conta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/consulta.css">
</head>
<body>
<!-- Botão de voltar -->
<a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/consultaDados.jsp" class="btn-voltar">← Voltar</a>

<h1>Consulta de Conta</h1>

<form method="get" action="${pageContext.request.contextPath}/consultaConta">
    <label>Digite o CPF do cliente:</label>
    <input type="text" name="cpf" required>
    <button type="submit">Consultar</button>
</form>

<%
    List<RelatorioTransacaoDto> resultados = (List<RelatorioTransacaoDto>) request.getAttribute("resultados");
    String cpf = request.getParameter("cpf");

    if (resultados != null && !resultados.isEmpty()) {
%>
<h2>Resultado</h2>
<div class="table-container">
    <table>
        <tr>
            <th>Conta</th>
            <th>Tipo</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Saldo</th>
            <th>Limite</th>
            <th>Vencimento</th>
            <th>Tipo Transação</th>
            <th>Valor</th>
            <th>Data</th>
            <th>Descrição</th>
        </tr>
        <%
            for (RelatorioTransacaoDto r : resultados) {
        %>
        <tr>
            <td><%= r.getNumeroConta() %></td>
            <td><%= r.getTipoConta() %></td>
            <td><%= r.getNome() %></td>
            <td><%= r.getCpf() %></td>
            <td><%= r.getSaldo() %></td>
            <td><%= r.getLimite() %></td>
            <td><%= r.getDataVencimento() %></td>
            <td><%= r.getTipoTransacao() %></td>
            <td><%= r.getValorTransacao() %></td>
            <td><%= r.getDataTransacao() %></td>
            <td><%= r.getDescricao() %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%
} else if (cpf != null) {
%>
<p>Nenhum resultado encontrado para o CPF informado.</p>
<%
    }
%>

</body>
</html>