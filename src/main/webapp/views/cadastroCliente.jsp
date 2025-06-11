<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cadastro de Cliente</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }
    .container {
      max-width: 600px;
      background: #fff;
      margin: 0 auto;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    }
    .container h1 {
      text-align: center;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    .form-group input {
      width: 100%;
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
    }
    button {
      display: block;
      width: 100%;
      padding: 10px;
      background-color: #007BFF;
      border: none;
      color: white;
      font-weight: bold;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s ease-in-out;
    }
    button:hover {
      background-color: #0056b3;
    }
    .mensagem {
      margin-top: 10px;
      padding: 10px;
      border-radius: 5px;
      text-align: center;
      font-weight: bold;
      display: none;
    }
    .sucesso {
      background: #d4edda;
      color: #155724;
    }
    .erro {
      background: #f8d7da;
      color: #721c24;
    }
  </style>
</head>
<body>

<div class="container">
  <h1>Cadastro de Cliente</h1>
  <form id="formCadastro">
    <div class="form-group">
      <label for="nome">Nome</label>
      <input type="text" id="nome" name="nome" required>
    </div>

    <div class="form-group">
      <label for="cpf">CPF</label>
      <input type="text" id="cpf" name="cpf" required>
    </div>

    <div class="form-group">
      <label for="senha">Senha</label>
      <input type="password" id="senha" name="senha" required>
    </div>

    <div class="form-group">
      <label for="telefone">Telefone</label>
      <input type="text" id="telefone" name="telefone" required>
    </div>

    <h3>Endereço</h3>

    <div class="form-group">
      <label for="cep">CEP</label>
      <input type="text" id="cep" name="cep" required>
    </div>

    <div class="form-group">
      <label for="local">Rua</label>
      <input type="text" id="local" name="local" required>
    </div>

    <div class="form-group">
      <label for="numeroCasa">Número</label>
      <input type="text" id="numeroCasa" name="numeroCasa" required>
    </div>

    <div class="form-group">
      <label for="bairro">Bairro</label>
      <input type="text" id="bairro" name="bairro" required>
    </div>

    <div class="form-group">
      <label for="cidade">Cidade</label>
      <input type="text" id="cidade" name="cidade" required>
    </div>

    <div class="form-group">
      <label for="estado">Estado</label>
      <input type="text" id="estado" name="estado" required>
    </div>

    <div class="form-group">
      <label for="complemento">Complemento</label>
      <input type="text" id="complemento" name="complemento">
    </div>

    <button type="submit">Cadastrar</button>
  </form>

  <div id="mensagem" class="mensagem"></div>
</div>

<script>
  const form = document.getElementById("formCadastro");
  const mensagem = document.getElementById("mensagem");

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(form);

    fetch("cliente", { method: "POST", body: formData })
            .then((response) => {
              if (response.ok) {
                mostrarMensagem("Cadastro realizado com sucesso!", true);
                form.reset();
              } else {
                mostrarMensagem("Erro ao cadastrar. Tente novamente.", false);
              }
            })
            .catch((error) => {
              mostrarMensagem("Erro de conexão com o servidor.", false);
            });
  });

  function mostrarMensagem(texto, sucesso) {
    mensagem.textContent = texto;
    mensagem.classList.remove("sucesso", "erro");
    mensagem.classList.add(sucesso ? "sucesso" : "erro");
    mensagem.style.display = "block";

    setTimeout(() => {
      mensagem.style.display = "none";
    }, 5000);
  }
</script>

</body>
</html>