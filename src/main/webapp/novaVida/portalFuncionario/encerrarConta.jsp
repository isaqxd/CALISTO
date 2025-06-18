<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page import="CALISTO.model.persistence.Usuario.Funcionario" %>
<%
    Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
    if (funcionario == null) {
        response.sendRedirect(request.getContextPath() + "/novaVida/login.jsp");
        return;
    }
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Encerrar Contas do Cliente</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/portal-cliente.css">
    <meta charset="UTF-8">
</head>
<body>
<!-- Botão de voltar -->
<a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/portalfuncionario.jsp" class="btn-voltar">← Voltar</a>
<div class="container">
    <h2>Buscar Cliente por CPF</h2>
    <form method="get" action="encerrarContas" class="form-busca">
        <label>CPF:</label>
        <input type="text" name="cpf" required>
        <button type="submit">Buscar Contas</button>
    </form>

    <h4>Contas associadas:</h4>
    <div class="contas-wrapper">
        <% if (cliente != null && cliente.getContas() != null) { %>
        <div class="cliente-info">
            <label>Nome: <input readonly value="<%= cliente.getNome() %>"></label>
            <label>CPF: <input readonly value="<%= cliente.getCpf() %>"></label>
        </div>

        <label>Tipo de Conta:</label>
        <select id="tipoContaSelect">
            <option value="">-- Selecione o tipo --</option>
            <% for (Conta conta : cliente.getContas()) { %>
            <option value="conta_<%= conta.getIdConta() %>">
                <%= conta.getTipoConta() %>
            </option>
            <% } %>
        </select>

        <% for (Conta conta : cliente.getContas()) { %>
        <div class="detalhesConta" id="conta_<%= conta.getIdConta() %>" style="display:none">
            <form action="encerrarContas" method="post" class="form-encerrar">
                <input type="hidden" name="idConta" value="<%= conta.getIdConta() %>">
                <label>Número da Conta: <input readonly value="<%= conta.getNumeroConta() %>"></label>
                <label>Saldo: R$ <input readonly value="<%= conta.getSaldo() %>"></label>

                <label>Status:
                    <select name="status">
                        <option value="<%= conta.getStatus() %>" selected><%= conta.getStatus() %></option>
                        <option value="ENCERRADA">Encerrar</option>
                    </select>
                </label>

                <label for="motivo">Motivo:</label>
                <input type="text" name="detalhes" placeholder="Informe o motivo do encerramento." required>

                <button type="submit">Finalizar Conta</button>
            </form>
        </div>
        <% } %>
        <% } else { %>
        <p>Nenhum cliente selecionado. Busque um CPF acima.</p>
        <% } %>
    </div>
</div>

<script>
    const tipoContaSelect = document.getElementById("tipoContaSelect");
    const divs = document.querySelectorAll(".detalhesConta");

    tipoContaSelect.addEventListener("change", function () {
        const selecionado = this.value;

        divs.forEach(div => {
            div.style.display = div.id === selecionado ? "block" : "none";
        });
    });
</script>
</body>
</html>