<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Funcionario</title>
</head>
<body>
<h1>Cadastro de Funcionario</h1>
<form action="${pageContext.request.contextPath}/registrarFuncionario" method="post">
    <label>Nome: <input type="text" name="nome" required></label><br>
    <label>CPF: <input type="text" name="cpf" required></label><br>
    <label>Data de Nascimento: <input name="data_nascimento" type="date" required></label><br>
    <label>Senha: <input type="password" name="senha" required></label><br>
    <label>Telefone: <input type="text" name="telefone" required></label><br>
    <label>Cargo: <select name="cargo" required>
        <option value="ESTAGIARIO">ESTAGIARIO</option>
        <option value="ATENDENTE">ATENDENTE</option>
        <option value="GERENTE">GERENTE</option>
    </select>
    </label><br>
    <label>Codigo: <input type="text" name="codigo_funcionario" required></label>
    <label>Supervisor: <input type="number" name="supervisor" required></label><br>
    <h3>Endereço</h3>
    <label>CEP: <input type="text" name="cep" required></label><br>
    <label>Rua: <input type="text" name="local" required></label><br>
    <label>Número: <input type="text" name="numeroCasa" required></label><br>
    <label>Bairro: <input type="text" name="bairro" required></label><br>
    <label>Cidade: <input type="text" name="cidade" required></label><br>
    <label>Estado: <input type="text" name="estado" required></label><br>
    <label>Complemento: <input type="text" name="complemento"></label><br><br>
    <button type="submit">Cadastrar</button>
</form>
</body>
</html>
