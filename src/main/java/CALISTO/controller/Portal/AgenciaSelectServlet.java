package CALISTO.controller.Portal;

import CALISTO.model.dao.AgenciaDao;
import CALISTO.model.persistence.Agencia.Agencia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/selecionar-agencia")
public class AgenciaSelectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AgenciaDao dao = new AgenciaDao();

        List<Agencia> agencias = dao.findAll();

        request.setAttribute("agencias", agencias);
        request.getRequestDispatcher("test/portalfuncionario.jsp").forward(request, response);
    }
}
