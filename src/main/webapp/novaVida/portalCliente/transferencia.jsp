<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/transferencia.css">
    <title>Transferência</title></head>
<body>
<h2>Transferência entre contas</h2>

<form method="post" action="${pageContext.request.contextPath}/transferencia">
    <label>Conta de origem:</label>
    <select name="idConta" id="selectConta" onchange="atualizaInformacoesConta(this)">
        <option selected disabled>Selecione uma conta</option>
        <% for (Conta conta : cliente.getContas()) { %>
        <option value="<%= conta.getIdConta() %>"
                data-tipoconta="<%= conta.getTipoConta() %>"
                data-saldo="<%= conta.getSaldo() %>">
            <%= conta.getTipoConta() %> - <%= conta.getNumeroConta() %>
        </option>
        <% } %>
    </select><br>
    <div id="infoConta" style="display: none;">
        <p><strong>Saldo:</strong> R$ <span id="saldoConta">0.00</span></p>
    </div>

    <label>Número da conta de destino:</label>
    <input type="text" name="numeroContaDestino" required><br><br>

    <label>Valor a transferir (R$):</label>
    <input type="number" step="0.01" name="valor" required><br><br>

    <button type="submit">Transferir</button>
</form>

<% if (request.getAttribute("mensagem") != null) { %>
<p><strong><%= request.getAttribute("mensagem") %></strong></p>
<% } %>

<script>
    function atualizaInformacoesConta(selectElement) {
        const opcaoSelecionada = selectElement.options[selectElement.selectedIndex];
        const saldo = opcaoSelecionada.dataset.saldo;

        // Atualiza saldo e exibe a área de informações da conta
        document.getElementById("saldoConta").textContent = parseFloat(saldo).toFixed(2);
        document.getElementById("infoConta").style.display = "block";
    }
</script>
</body>
</html>
