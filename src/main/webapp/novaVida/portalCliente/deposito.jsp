<%-- Diretivas da página: Define o tipo de conteúdo e importa as classes Java necessárias. --%>
<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Bloco de código Java (Scriptlet) que é executado no servidor.
    // Ele assume que um objeto 'cliente' foi colocado como um atributo na requisição.
    Cliente cliente = (Cliente) request.getAttribute("cliente");
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
                        <%-- Loop para criar uma <option> para cada conta que o cliente possui. --%>
                        <% for (Conta conta : cliente.getContas()) { %>
                            <option value="<%= conta.getIdConta() %>">
                                <%= conta.getTipoConta() %> - <%= conta.getNumeroConta() %> (Saldo: R$ <%= String.format("%.2f", conta.getSaldo()) %>)
                            </option>
                        <% } %>
                    </select>
                </div>
                
                <%-- Agrupamento para o campo de valor do depósito. --%>
                <div class="form-group">
                    <label for="valorInput">Valor do Depósito:</label>
                    <input type="text" name="valor" id="valorInput" step="0.01" required placeholder="Ex: 100.00">
                </div>

                <button type="submit">Confirmar Depósito</button>
                
            </form>

        <% } else { %>
            
            <%-- Mensagem de feedback exibida se a condição acima falhar. --%>
            <p style="text-align:center; color: #8C480D; font-weight: 500;">
                Para realizar um depósito, primeiro busque por um cliente que possua contas ativas na página anterior.
            </p>
            
        <% } %>
        
    </div>

</body>
</html>