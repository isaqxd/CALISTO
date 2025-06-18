<%-- Diretivas da página: Define o tipo de conteúdo e importa as classes Java necessárias. --%>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        out.println("Cliente da sessão é nulo.");
        response.sendRedirect(request.getContextPath() + "/novaVida/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Depósito</title>

    <%-- Link para a folha de estilo externa que define a aparência da página. --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/deposito.css">
</head>
<body>

<%-- Container principal que envolve todo o conteúdo para aplicar um estilo de "cartão". --%>
<div class="form-container">

    <h2>Depósito em Conta</h2>

    <%--
        Verificação de segurança e usabilidade:
        O formulário só é exibido se um objeto 'cliente' válido foi encontrado
        e se esse cliente possui uma lista de contas não vazia.
    --%>
    <% if (cliente != null && cliente.getContas() != null && !cliente.getContas().isEmpty()) { %>

    <form method="post" action="${pageContext.request.contextPath}/deposito">

        <%-- Agrupamento para o campo de seleção de conta. --%>
            <div class="form-group">
                <label for="contaSelect">Selecione a Conta para Depósito:</label>
                <select name="numeroConta" id="contaSelect">
                    <option value="">-- Escolha uma conta --</option>
                    <% for (Conta conta : cliente.getContas()) { %>
                    <option
                            value="<%= conta.getIdConta() %>"
                            data-info='{
                    "tipo": "<%= conta.getTipoConta() %>",
                    "numero": "<%= conta.getNumeroConta() %>",
                    "saldo": <%= conta.getSaldo() %>
                }'>
                        <%= conta.getTipoConta() %> - <%= conta.getNumeroConta() %>
                    </option>
                    <% } %>
                </select>
            </div>

    <% } else { %>

    <%-- Mensagem de feedback exibida se a condição acima falhar. --%>
    <p style="text-align:center; color: #8C480D; font-weight: 500;">
        Para realizar um depósito, primeiro busque por um cliente que possua contas ativas na página anterior.
    </p>

    <% } %>

</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const select = document.getElementById("contaSelect");
        const detalhes = document.getElementById("detalhesConta");
        const tipoSpan = document.getElementById("tipoConta");
        const numeroSpan = document.getElementById("numeroConta");
        const saldoSpan = document.getElementById("saldoConta");

        const atualizarDetalhes = (dados) => {
            tipoSpan.textContent = dados.tipo;
            numeroSpan.textContent = dados.numero;
            saldoSpan.textContent = parseFloat(dados.saldo).toFixed(2);
            detalhes.style.display = "block";
        };

        const limparDetalhes = () => {
            tipoSpan.textContent = "";
            numeroSpan.textContent = "";
            saldoSpan.textContent = "";
            detalhes.style.display = "none";
        };

        select.addEventListener("change", () => {
            const selected = select.options[select.selectedIndex];
            const dados = selected.dataset.info;

            if (!dados) {
                limparDetalhes();
                return;
            }

            try {
                const info = JSON.parse(dados);
                atualizarDetalhes(info);
            } catch (e) {
                console.error("Erro ao interpretar dados da conta:", e);
                limparDetalhes();
            }
        });
    });
</script>

</body>
</html>