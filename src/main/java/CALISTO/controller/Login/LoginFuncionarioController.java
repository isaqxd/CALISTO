package CALISTO.controller.Login;

import CALISTO.model.service.Login.LoginFuncionarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet("/loginFuncionario")
public class LoginFuncionarioController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginFuncionarioService service = new LoginFuncionarioService();

        try {
            if (service.validateCpfSenhaForFuncionario(request, response)) {
                // define pagina de login como otp_true=true
                HttpSession session = request.getSession();
                session.setAttribute("usuarioAutenticado", true);
                session.setAttribute("tipo_usuario", "FUNCIONARIO");
                response.sendRedirect("novaVida/login.jsp?otp_true=true");
            } else {
                // Se não vor validar o OTP, redireciona para a página de login com erro
                response.sendRedirect("login.jsp?error=login_invalido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
