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
                session.setAttribute("otpValidated", true);
                response.sendRedirect("test/sucesso.jsp");
            } else {
                // Se não for validar o OTP, redireciona para a página de login com erro
                response.sendRedirect("test/erro.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

