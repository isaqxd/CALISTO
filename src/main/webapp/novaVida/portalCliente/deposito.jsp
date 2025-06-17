<%@ page import="CALISTO.model.persistence.Usuario.Cliente" %>
<%@ page import="CALISTO.model.persistence.Conta.Conta" %>
<%@ page import="CALISTO.model.dao.ClienteDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  ClienteDao clienteDao = new ClienteDao();
  Cliente cliente = clienteDao.innerToRequestSession("07761207194");
  session.setAttribute("cliente", cliente);
  cliente = (Cliente) session.getAttribute("cliente");
//  Cliente cliente = (Cliente) session.getAttribute("cliente");
//
//  if (cliente == null) {
//    response.sendRedirect("../../novaVida/login.jsp");
//    return;
//  }
%>
<!DOCTYPE html>
<html>
<head><title>Depósito</title></head>
<body>
<h2>Depósito</h2>
<form method="post" action="${pageContext.request.contextPath}/deposito">
  <label>Conta:</label>
  <select name="numeroConta">
    <% for (Conta conta : cliente.getContas()) { %>
    <option value="<%= conta.getIdConta() %>"><%= conta.getTipoConta() %> - <%= conta.getNumeroConta() %></option>

    <% } %>
  </select><br>
  <label>Valor:</label>
  <input type="text" name="valor" step="0.01" required><br>
  <button type="submit">Depositar</button>
</form>
</body>
</html>
