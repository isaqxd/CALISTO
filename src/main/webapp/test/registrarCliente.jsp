<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Cadastro de Cliente</title>
</head>
<body>
<h1>Cadastro de Cliente</h1>
<form action="registrarCliente" method="post">
  <label>Nome: <input type="text" name="nome" required></label><br>
  <label>CPF: <input type="text" name="cpf" required></label><br>
  <label>Data de Nascimento: <input name="data_nascimento" type="date" required></label><br>
  <label>Senha: <input type="password" name="senha" required></label><br>
  <label>Telefone: <input type="text" name="telefone" required></label><br>
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