package CALISTO.controller.Simples;

import CALISTO.model.dao.ClienteContaDao;
import CALISTO.model.dto.RelatorioContaDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/consultaCliente")
public class ConsultaClienteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cpf = request.getParameter("cpf");

        ClienteContaDao dao = new ClienteContaDao();
        List<RelatorioContaDto> resultados = dao.buscarPorCpf(cpf);

        request.setAttribute("resultados", resultados);
        request.getRequestDispatcher("/novaVida/portalFuncionario/consultaCliente.jsp").forward(request, response);
    }
}