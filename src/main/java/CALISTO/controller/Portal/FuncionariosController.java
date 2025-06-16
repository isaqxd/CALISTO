package CALISTO.controller.Portal;

import CALISTO.model.dao.AgenciaDao;
import CALISTO.model.dao.ClienteDao;
import CALISTO.model.dao.FuncionarioDao;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/funcionarios")
public class FuncionariosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FuncionarioDao funcionarioDao = new FuncionarioDao();
        AgenciaDao agenciaDao = new AgenciaDao();
        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> clientes = clienteDao.findAll();
        List<Funcionario> funcionarios = funcionarioDao.findAll();
        List<Agencia> agencias = agenciaDao.findAll();

        request.setAttribute("clientes", clientes);
        request.setAttribute("funcionarios", funcionarios);
        request.setAttribute("agencias", agencias);

        RequestDispatcher dispatcher = request.getRequestDispatcher("test/portalfuncionario.jsp");
        dispatcher.forward(request, response);
    }
}
