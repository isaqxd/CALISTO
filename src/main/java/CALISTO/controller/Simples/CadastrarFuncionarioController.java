package CALISTO.controller.Simples;

import CALISTO.controller.Usuario.UsuarioControllerUtil;
import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.FuncionarioDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Cargo;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.service.UsuarioService.FuncionarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/cadastrarFuncionario")
public class CadastrarFuncionarioController extends HttpServlet {
    private static final LocalDateTime AGORA = LocalDateTime.now();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Usando a classe utilitária para criar o endereço e preencher dados comuns
            Endereco endereco = UsuarioControllerUtil.requestEndereco(request);
            CALISTO.model.persistence.Usuario.Funcionario funcionario = new CALISTO.model.persistence.Usuario.Funcionario();
            UsuarioControllerUtil.fillDataUsuario(funcionario, request, endereco);

            // Configurando propriedades específicas do funcionário
            String cargoStr = request.getParameter("cargo");
            Cargo cargo = null;
            try {
                cargo = Cargo.valueOf(cargoStr.toUpperCase());
            } catch (IllegalArgumentException | NullPointerException e) {
                // Tratamento de erro para cargo inválido
                System.out.println("Cargo inválido: " + cargoStr);
                response.sendRedirect("erro.jsp");
                return;
            }

            // Validação do id_supervisor
            String idSupervisorStr = request.getParameter("id_supervisor");
            System.out.println("ID do supervisor: " + idSupervisorStr);
            int idSupervisor = 0; // valor padrão caso não haja supervisor
            if (idSupervisorStr != null && !idSupervisorStr.trim().isEmpty()) {
                try {
                    idSupervisor = Integer.parseInt(idSupervisorStr);
                } catch (NumberFormatException e) {
                    System.out.println("ID do supervisor inválido: " + idSupervisorStr);
                    response.sendRedirect("erro.jsp");
                    return;
                }
            }

            funcionario.setCargo(cargo);

            funcionario.setSupervisor(idSupervisor);
            funcionario.setTipoUsuario(TipoUsuario.FUNCIONARIO);

            System.out.println("Funcionario: " + funcionario.getNome() + " COM SUPERVISOR DE ID: " + funcionario.getSupervisor());

            // Salvando no banco
            FuncionarioDao dao = new FuncionarioDao();
            FuncionarioService funcionarioService = new FuncionarioService(dao);
            Funcionario funcionarioSalvo = funcionarioService.verificarFuncionario(funcionario);

            if (funcionarioSalvo != null) {
                auditoriaCadastro(request);
                response.sendRedirect("test/sucesso.jsp");
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
        a.setDetalhes("CADASTRO DA CONTA DO FUNCIONARIO");

        auditoriaDao.save(a);
    }
}
