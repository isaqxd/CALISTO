<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Saque</title>
</head>
<body>
<h2>Seus saldos:</h2>
<label>Conta:</label>
<form method="post" action="${pageContext.request.contextPath}/saque">
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

        <label for="valorSaque">Valor do saque:</label>
        <input type="text" step="0.01" name="valor" required><br>

        <button type="submit">Sacar</button>
    </div>
</form>

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
