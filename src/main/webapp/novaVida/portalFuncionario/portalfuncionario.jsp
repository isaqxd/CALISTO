<%@ page import="CALISTO.model.persistence.Usuario.Funcionario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Portal do Funcionário</title>

    <%-- Aponta para o arquivo CSS que agora contém todos os estilos --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/funcionarionv.css">

    <%-- Importa a biblioteca de ícones do Google --%>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<div style="text-align: center; padding: 20px 0;">
    <img src="${pageContext.request.contextPath}/img/image.svg" style="height: 60px;">
</div>


<h1>Portal do Funcionário</h1>

<table>
    <tr>
        <td style="vertical-align: top;">
            <h3>Menu</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/cadastrarCliente.jsp"><i class="material-icons">person_add</i>Cadastrar Cliente</a></li>
                <li><a href="${pageContext.request.contextPath}/abrirConta"><i class="material-icons">account_balance_wallet</i>Abertura de Conta</a></li>
                <li><a href="${pageContext.request.contextPath}/encerrarContas"><i class="material-icons">lock</i>Encerramento de Conta</a></li>
                <li><a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/consultaDados.jsp"><i class="material-icons">search</i>Consulta de Dados</a></li>
                <li><a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/alteracaoDados.jsp"><i class="material-icons">edit</i>Alteração de Dados</a></li>
                <li><a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/cadastrarFuncionario.jsp"><i class="material-icons">badge</i>Cadastro de Funcionário</a></li>
                <li><a href="${pageContext.request.contextPath}/novaVida/portalFuncionario/gerarRelatorios.jsp"><i class="material-icons">assessment</i>Geração de Relatórios</a></li>
            </ul>
        </td>
        <td style="padding-left: 30px;">
            <p>Bem-vindo ao portal! Escolha uma opção no menu.</p>
        </td>
    </tr>
</table>
</body>
</html>