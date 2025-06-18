package CALISTO.controller.Simples;

import CALISTO.controller.Usuario.UsuarioControllerUtil;
import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.service.UsuarioService.ClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/cadastrarCliente")
public class CadastrarClienteController extends HttpServlet {
    private static final LocalDateTime AGORA = LocalDateTime.now();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Usando a classe utilitária para criar o endereço e preencher dados comuns
            Endereco endereco = UsuarioControllerUtil.requestEndereco(request);
            CALISTO.model.persistence.Usuario.Cliente cliente = new CALISTO.model.persistence.Usuario.Cliente();
            UsuarioControllerUtil.fillDataUsuario(cliente, request, endereco);

            // Configurando propriedades específicas do cliente
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);

            // Salvando no banco
            ClienteDao dao = new ClienteDao();
            ClienteService clienteService = new ClienteService(dao);
            Cliente clienteSalvo = clienteService.verifyUsuario(cliente);

            if (clienteSalvo != null) {
                auditoriaCadastro(request);
                response.sendRedirect("/portalfuncionario.jsp");
            } else {
                response.sendRedirect("test/erro.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("test/erro.jsp");
        }
    }

    public void auditoriaCadastro(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
        Auditoria a = new Auditoria();
        AuditoriaDao auditoriaDao = new AuditoriaDao();

        a.setUsuario(funcionario);
        a.setAcao("CADASTRAR_CONTA");
        a.setDataHora(AGORA);
        a.setDetalhes("CADASTRO DA CONTA DO CLIENTE");

        auditoriaDao.save(a);
    }
}
