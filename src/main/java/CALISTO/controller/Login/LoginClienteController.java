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
                HttpSession session = request.getSession();
                session.setAttribute("usuarioAutenticado", true);
                session.setAttribute("tipo_usuario", "CLIENTE");
                // Redireciona para OTP se necessário (mantenha sua lógica atual)
                response.sendRedirect("novaVida/login.jsp?otp_true=true");
            } else {
                response.sendRedirect("novaVida/login.jsp?error=login_invalido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
