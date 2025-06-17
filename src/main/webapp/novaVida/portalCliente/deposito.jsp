<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Este código assume que o objeto 'cliente' foi passado para a página
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Depósito</title>
    <%-- Garante que o caminho para o CSS esteja correto --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/deposito.css">
</head>
<body>

<%-- 1. ADICIONADO: Um container principal para centralizar e aplicar o estilo de "cartão" --%>
<div class="form-container">
    <h2>Depósito em Conta</h2>

    <%-- Verificação para só mostrar o formulário se um cliente válido foi encontrado --%>
    <% if (cliente != null && cliente.getContas() != null && !cliente.getContas().isEmpty()) { %>
        <form method="post" action="${pageContext.request.contextPath}/deposito">
            
            <%-- 2. ORGANIZADO: Cada label/input está em uma "form-group" para espaçamento correto --%>
            <div class="form-group">
                <label for="contaSelect">Selecione a Conta para Depósito:</label>
                <select name="numeroConta" id="contaSelect">
                    <% for (Conta conta : cliente.getContas()) { %>
                        <option value="<%= conta.getIdConta() %>">
                            <%= conta.getTipoConta() %> - <%= conta.getNumeroConta() %> (Saldo: R$ <%= String.format("%.2f", conta.getSaldo()) %>)
                        </option>
                    <% } %>
                </select>
            </div>
            
            <div class="form-group">
                <label for="valorInput">Valor do Depósito:</label>
                <input type="text" name="valor" id="valorInput" step="0.01" required placeholder="Ex: 100.00">
            </div>

            <button type="submit">Confirmar Depósito</button>
        </form>
    <% } else { %>
        <%-- Mensagem exibida se não houver um cliente ou se o cliente não tiver contas --%>
        <p style="text-align:center; color: #8C480D; font-weight: 500;">
            Para realizar um depósito, primeiro busque por um cliente que possua contas ativas na página anterior.
        </p>
    <% } %>
</div>

</body>
</html>