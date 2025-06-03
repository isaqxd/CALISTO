
package CALISTO.controller;

import CALISTO.model.Usuario;
import CALISTO.service.UsuarioService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cadastro")
public class CadastroController extends HttpServlet {
    private UsuarioService service = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario(nome, email, senha);

        try {
            service.insert(usuario);
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?sucesso=cadastro");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/views/cadastro.jsp?erro=" + e.getMessage());
        }
    }
}