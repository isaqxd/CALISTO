package CALISTO.controller.Simples;


import CALISTO.model.dao.ConsultaContaDao;
import CALISTO.model.dto.RelatorioTransacaoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/consultaConta")
public class ConsultaContaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");

        ConsultaContaDao dao = new ConsultaContaDao();
        List<RelatorioTransacaoDto> resultados = dao.buscarContaComTransacoes(cpf);

        request.setAttribute("resultados", resultados);
        request.getRequestDispatcher("novaVida/consultaConta.jsp").forward(request, response);
    }
}

