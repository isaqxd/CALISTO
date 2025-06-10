package CALISTO.controller.Login;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/loginCliente")
public class LoginClienteController extends HttpServlet {
    protected static final String SALT = "X@mpl3S@lt2025!";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String tipoUsuario = request.getParameter("tipo_usuario");

        String senhaHash = gerarHashMD5(senha);

        ClienteDao dao = new ClienteDao();
        Cliente cliente = dao.findByCpf(cpf);

        if (cliente != null && cliente.getSenhaHash().equals(senhaHash) && tipoUsuario.equals(cliente.getTipoUsuario().toString())) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", cliente);
            response.sendRedirect("test/sucesso.jsp");
        } else {
            request.setAttribute("erro", "CPF ou senha inválidos.");
            request.getRequestDispatcher("test/login.jsp").forward(request, response);
        }
    }

    private String gerarHashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String senhaComSalt = input + SALT;

            byte[] hash = md.digest(senhaComSalt.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash MD5: " + e.getMessage(), e);
        }
    }
}
