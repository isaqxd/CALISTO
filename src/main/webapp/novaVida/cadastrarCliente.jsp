<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastrarcliente.css">
    <title>Cadastrar Cliente</title>
</head>
<body>
<!-- Botão de voltar -->
<a href="${pageContext.request.contextPath}/novaVida/portalfuncionario.jsp" class="btn-voltar">← Voltar</a>

<h1>Cadastro de Cliente</h1>
<form action="${pageContext.request.contextPath}/registrarCliente" method="post">
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" required>

    <label for="cpf">CPF:</label>
    <input type="text" id="cpf" name="cpf" required>

    <label for="data_nascimento">Data de Nascimento:</label>
    <input type="date" id="data_nascimento" name="data_nascimento" required>

    <label for="senha">Senha:</label>
    <div class="input-container">
        <input type="password" id="senha-cliente" name="senha" required>
        <button type="button" class="toggle-password" onclick="togglePassword('senha-cliente')">
            <img id="icon-senha-cliente" src="../img/iconeyeopen.png" alt="Mostrar senha" width="20">
        </button>
    </div>

    <label for="telefone">Telefone:</label>
    <input type="text" id="telefone" name="telefone" required>

    <h3>Endereço</h3>

    <label for="cep">CEP:</label>
    <input type="text" id="cep" name="cep" required>

    <label for="local">Rua:</label>
    <input type="text" id="local" name="local" required>

    <label for="numeroCasa">Número:</label>
    <input type="text" id="numeroCasa" name="numeroCasa" required>

    <label for="bairro">Bairro:</label>
    <input type="text" id="bairro" name="bairro" required>

    <label for="cidade">Cidade:</label>
    <input type="text" id="cidade" name="cidade" required>

    <label for="estado">Estado:</label>
    <input type="text" id="estado" name="estado" required>

    <label for="complemento">Complemento:</label>
    <input type="text" id="complemento" name="complemento">

    <button type="submit">Cadastrar</button>
</form>

<script src="${pageContext.request.contextPath}/js/cadastrarcliente.js"></script>
</body>
</html>