package CALISTO.controller.Usuario;

import CALISTO.model.dao.FuncionarioDao;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Cargo;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.service.FuncionarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registrarFuncionario")
public class FuncionarioController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Usando a classe utilitária para criar o endereço e preencher dados comuns
            Endereco endereco = UsuarioControllerUtil.criarEndereco(request);
            Funcionario funcionario = new Funcionario();
            UsuarioControllerUtil.preencherDadosUsuario(funcionario, request, endereco);

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
            String codigo = request.getParameter("codigo_funcionario");

            // Validação do id_supervisor
            String idSupervisorStr = request.getParameter("id_supervisor");
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
            funcionario.setCodigoFuncionario(codigo);
            funcionario.setSupervisor(idSupervisor);
            funcionario.setTipoUsuario(TipoUsuario.FUNCIONARIO);


            // Salvando no banco
            FuncionarioDao dao = new FuncionarioDao();
            FuncionarioService funcionarioService = new FuncionarioService(dao);
            Funcionario funcionarioSalvo = funcionarioService.verificarFuncionario(funcionario);

            if (funcionarioSalvo != null) {
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
