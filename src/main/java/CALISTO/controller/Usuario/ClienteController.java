package CALISTO.controller.Usuario;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.service.UsuarioService.ClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registrarCliente")
public class ClienteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Usando a classe utilitária para criar o endereço e preencher dados comuns
            Endereco endereco = UsuarioControllerUtil.makeEndereco(request);
            CALISTO.model.persistence.Usuario.Cliente cliente = new CALISTO.model.persistence.Usuario.Cliente();
            UsuarioControllerUtil.fillDataUsuario(cliente, request, endereco);

            // Configurando propriedades específicas do cliente
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);

            // Salvando no banco
            ClienteDao dao = new ClienteDao();
            ClienteService clienteService = new ClienteService(dao);
            CALISTO.model.persistence.Usuario.Cliente clienteSalvo = clienteService.verifyUsuario(cliente);

            if (clienteSalvo != null) {
                response.sendRedirect("test/sucesso.jsp");
            } else {
                response.sendRedirect("test/erro.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("test/erro.jsp");
        }
    }
}
