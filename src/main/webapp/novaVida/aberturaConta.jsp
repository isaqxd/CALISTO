<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="CALISTO.model.persistence.Agencia.Agencia" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}aberturaconta.css">
    <title>Abertura de Conta</title>
</head>
<body>
<h1>Abertura de Conta</h1>
<form action="${pageContext.request.contextPath}/abrirConta" method="post">
    <label>Saldo: <input name="saldo" type="text" required></label><br>
    <label>Numero da conta: <input name="numeroConta" type="text" required></label><br>
    <label>Tipo da conta:</label>
    <!-- VERIFICAR TIPO DE CONTA -->
    <select name="tipoConta" id="tipoConta" onchange="verificarTipoConta()">
        <option value="">-- Selecione --</option>
        <option value="CORRENTE">Corrente</option>
        <option value="POUPANCA">Poupança</option>
        <option value="INVESTIMENTO">Investimento</option>
    </select>

    <!-- Campo extra que aparece só se for INVESTIMENTO -->
    <div id="perfilRiscoDiv" style="display: none;">
        <label>Perfil de Risco:</label>
        <select name="perfilRisco">
            <option value="BAIXO">Baixo</option>
            <option value="MEDIO">Medio</option>
            <option value="ALTO">Alto</option>
        </select>
    </div>

    <!-- PUXAR CLIENTE -->
    <label>Cliente <select name="idCliente">
        <option value="">Selecione o Cliente</option>
        <%
            List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
            if (clientes != null) {
                for (Cliente cliente : clientes) {
        %>
        <option value="<%= cliente.getIdCliente() %>">
            <%= cliente.getNome()%> | <%= cliente.getCpf() %> |
        </option>
        <%
                }
            }
        %>
    </select></label><br>
    <!-- PUXAR FUNCIONARIO -->
    <label>Agencia <select name="idAgencia">
        <option value="">Selecione a Agencia:</option>
        <%
            List<Agencia> agencias = (List<Agencia>) request.getAttribute("agencias");
            if (agencias != null) {
                for (Agencia agencia : agencias) {
        %>
        <option value="<%= agencia.getIdAgencia() %>">
            <%= agencia.getNome()%> | <%= agencia.getCodigoAgencia() %> |
        </option>
        <%
                }
            }
        %>
    </select></label>
    <button type="submit">Cadastrar</button>

    <script>
        function verificarTipoConta() {
            var tipo = document.getElementById("tipoConta").value;
            var divPerfil = document.getElementById("perfilRiscoDiv");

            if (tipo === "INVESTIMENTO") {
                divPerfil.style.display = "block";
            } else {
                divPerfil.style.display = "none";
            }
        }
    </script>
</form>
</body>
</html>
