package CALISTO.controller.Login;

import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.service.Login.LoginClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/loginCliente")
public class LoginClienteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cliente = new Cliente();
        LoginClienteService service = new LoginClienteService();

        if (service.validateLoginCliente(request, response)) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", cliente);
            response.sendRedirect("test/sucesso.jsp");
        } else {
            request.setAttribute("erro", "CPF ou senha inválidos.");
            request.getRequestDispatcher("test/login.jsp").forward(request, response);
        }
    }
}
