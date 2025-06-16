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

@WebServlet("/loginOtp")
public class LoginOtp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginClienteService service = new LoginClienteService();

        try {
            if (service.validateOtp(response, request)) {
                HttpSession session = request.getSession();
                String tipoUsuario = (String) session.getAttribute("tipo_usuario");
                session.setAttribute("otpValidated", true);
                if ("CLIENTE".equalsIgnoreCase(tipoUsuario)) {
                    response.sendRedirect("portalCliente.jsp");
                } else if ("FUNCIONARIO".equalsIgnoreCase(tipoUsuario)) {
                    response.sendRedirect("portalFuncionario.jsp");
                } else {
                    response.sendRedirect("test/index.jsp");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}