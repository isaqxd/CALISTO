package CALISTO.controller.Portal.Simples;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/encerrarContas")
public class EncerrarContasController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");

        ClienteDao dao = new ClienteDao();
        Cliente cliente = dao.innerToRequestSession(cpf);

        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("novaVida/encerrarConta.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        String tipoConta = request.getParameter("tipo_conta");

    }
}
