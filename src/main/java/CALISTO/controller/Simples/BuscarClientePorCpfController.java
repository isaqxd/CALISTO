package CALISTO.controller.Simples;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/BuscarClientePorCpf")
public class BuscarClientePorCpfController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");

        Cliente cliente = null;
        if (cpf != null && !cpf.trim().isEmpty()) {
            ClienteDao dao = new ClienteDao();
            cliente = dao.findByCpf(cpf);
        }

        request.setAttribute("clienteEncontrado", cliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }
}
