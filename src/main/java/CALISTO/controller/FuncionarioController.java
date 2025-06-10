package CALISTO.controller;

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

            funcionario.setCargo(cargo);
            funcionario.setTipoUsuario(TipoUsuario.FUNCIONARIO);

            // Salvando no banco
            FuncionarioDao dao = new FuncionarioDao();
            FuncionarioService funcionarioService = new FuncionarioService(dao);
            Funcionario funcionarioSalvo = funcionarioService.verificarFuncionario(funcionario);

            if (funcionarioSalvo != null) {
                response.sendRedirect("sucesso.jsp");
            } else {
                response.sendRedirect("erro.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp");
        }
    }
}
