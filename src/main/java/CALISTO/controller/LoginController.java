package CALISTO.controller;

import CALISTO.service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UsuarioService service = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            boolean isAutenticado = service.autenticar(email, senha);

            if (isAutenticado) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", email);

                response.sendRedirect("views/login.jsp");
            } else {
                response.sendRedirect("views/login.jsp?erro=Login ou senha inválidos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("views/login.jsp?erro=" + e.getMessage());
        }
    }
}
