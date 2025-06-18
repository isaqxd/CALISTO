package CALISTO.controller.Login;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.dao.FuncionarioDao;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.TipoUsuario;
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
        FuncionarioDao funcionarioDao = new FuncionarioDao();

        try {
            if (service.validateOtp(response, request)) {
                HttpSession session = request.getSession();
                String cpf = (String) session.getAttribute("cpfLogin");

                // Busca o cliente COM SUAS CONTAS
                Cliente cliente = clienteDao.innerToRequestSession(cpf);
                Funcionario funcionario = funcionarioDao.findByCpf(cpf);

                String tipoUsuario = (String) session.getAttribute("tipo_usuario");
                if (tipoUsuario != null && tipoUsuario.equals(TipoUsuario.CLIENTE.toString())) {
                    session.setAttribute("cliente", cliente);
                    response.sendRedirect("novaVida/portalCliente/portalCliente.jsp");
                } else if (tipoUsuario != null && tipoUsuario.equals(TipoUsuario.FUNCIONARIO.toString())) {
                    session.setAttribute("funcionario", funcionario);
                    response.sendRedirect("novaVida/portalFuncionario/portalfuncionario.jsp");
                }
            } else {
                // OTP is false, send to error message directly
                response.sendRedirect("novaVida/login.jsp?otp_true=true&error=otp_invalido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}