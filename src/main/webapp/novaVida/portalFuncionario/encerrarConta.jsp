<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<!DOCTYPE html>
<html>
<head>
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
    <% if (cliente != null) { %>
    <label>Nome: <input readonly value="<%= cliente.getNome()%>"></label><br>
    <label>CPF: <input readonly value="<%= cliente.getCpf() %>"></label><br>

    <!-- Select com tipos de conta -->
    <label>Tipo de Conta:</label>
    <select id="tipoContaSelect">
        <option value="">-- Selecione o tipo --</option>
        <% for (Conta conta : cliente.getContas()) { %>
        <option name="tipo_conta" value="conta_<%= conta.getIdConta() %>">
            <%= conta.getTipoConta() %>
        </option>
        <% } %>
    </select>
    <br><br>

    <% for (Conta conta : cliente.getContas()) { %>
    <!-- Div com dados da conta, oculta por padrão -->
    <div class="detalhesConta" id="conta_<%= conta.getIdConta() %>"
         style="display:none; border:1px solid #ccc; padding:10px; margin:10px 0;">
        <form action="encerrarContas" method="post">
            <input type="hidden" name="idConta" value="<%= conta.getIdConta() %>">
            <label>Número da Conta: <input readonly value="<%= conta.getNumeroConta() %>"></label><br>
            <label>Saldo: R$ <input readonly value="<%= conta.getSaldo() %>"></label><br>
            <label>Status:
                <select name="status">
                    <option value="<%= conta.getStatus() %>" selected><%= conta.getStatus() %>
                    </option>
                    <option value="ENCERRADA">Encerrar</option>
                </select>
            </label><br>
            <label>Ação:
                <input readonly name="acao" value="ENCERRAR_CONTA">Encerrar Conta />
            </label><br>
            <label>Detalhes: <textarea name="detalhes"
                                       placeholder="Insira uma descrição do motivo de encerramento da conta.">
                            </textarea>
            </label><br><br>
            <button type="submit">Finalizar Conta</button>
        </form>
    </div>
    <% } %>
    <% } %>

    <script>
        const tipoContaSelect = document.getElementById("tipoContaSelect");
        const divs = document.querySelectorAll(".detalhesConta");

        tipoContaSelect.addEventListener("change", function () {
            const selecionado = this.value;

            divs.forEach(div => {
                if (div.id === selecionado) {
                    div.style.display = "block";
                } else {
                    div.style.display = "none";
                }
            });
        });
    </script>
</div>
</body>
</html>