//package CALISTO.controller.Simples;
//
//public class ConsultaFuncionarioController {
//
//}
package CALISTO.controller.Simples;


import CALISTO.model.dao.ConsultaFuncionarioDao;
import CALISTO.model.dto.FuncionarioContasDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/consultaFuncionario")
public class ConsultaFuncionarioController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");

        List<FuncionarioContasDto> resultados = null;
        if (cpf != null && !cpf.trim().isEmpty()) {
            ConsultaFuncionarioDao dao = new ConsultaFuncionarioDao();
            resultados = dao.listarFuncionariosComContas(cpf);
        }

        request.setAttribute("resultados", resultados);
        request.getRequestDispatcher("novaVida/portalFuncionario/consultaFuncionario.jsp").forward(request, response);
    }
}