<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Cliente cliente = (Cliente) session.getAttribute("cliente");
  if (cliente == null) {
    response.sendRedirect("../../novaVida/login.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html>
<head><title>Depósito</title></head>
<body>
<h2>Depósito</h2>
<form method="post" action="../../deposito">
  <label>Conta:</label>
  <select name="numeroConta">
    <% for (Conta conta : cliente.getContas()) { %>
    <option value="<%= conta.getIdConta() %>"><%= conta.getTipoConta() %> - <%= conta.getNumeroConta() %></option>
    <% } %>
  </select><br>
  <label>Valor:</label>
  <input type="number" name="valor" step="0.01" required><br>
  <button type="submit">Depositar</button>
</form>
</body>
</html>
