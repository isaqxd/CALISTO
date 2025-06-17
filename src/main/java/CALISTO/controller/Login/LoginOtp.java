package CALISTO.controller.Login;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.service.Login.LoginClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginOtp")
public class LoginOtp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginClienteService service = new LoginClienteService();
        ClienteDao clienteDao = new ClienteDao();

        try {
            if (service.validateOtp(response, request)) {
                HttpSession session = request.getSession();
                String cpf = (String) session.getAttribute("cpfLogin");

                // Busca o cliente COM SUAS CONTAS
                Cliente cliente = clienteDao.innerToRequestSession(cpf);

                if (cliente != null) {
                    session.setAttribute("cliente", cliente);
                    response.sendRedirect("novaVida/portalCliente/portalCliente.jsp");
                } else {
                    response.sendRedirect("login.jsp?error=Cliente não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}