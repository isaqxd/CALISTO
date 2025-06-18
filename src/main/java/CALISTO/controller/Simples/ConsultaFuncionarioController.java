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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/consultaFuncionario")
public class ConsultaFuncionarioController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
        String cpf = request.getParameter("cpf");


        ConsultaFuncionarioDao dao = new ConsultaFuncionarioDao();
        List<FuncionarioContasDto> funcionarios = dao.listarFuncionariosComContas(cpf);

        request.setAttribute("funcionarios", funcionarios);
        request.getRequestDispatcher("novaVida/consultaFuncionario.jsp").forward(request, response);
    }
}