package CALISTO.controller.Login;

import CALISTO.model.service.Login.LoginClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginCliente")
public class LoginClienteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginClienteService service = new LoginClienteService();

        try {
            if (service.validateCpfSenhaForCliente(request, response)) {
                // define pagina de login como otp_true=true
                HttpSession session = request.getSession();
                session.setAttribute("usuarioAutenticado", true);
                response.sendRedirect("views/login.jsp?otp_true=true");
            } else {
                // Se não vor validar o OTP, redireciona para a página de login com erro
                String errorMessage = "CPF ou senha inválidos.";
                String encodedMessage = java.net.URLEncoder.encode(errorMessage, "UTF-8");
                response.sendRedirect("test/login.jsp?error=" + encodedMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}