package CALISTO.controller.Login;

import CALISTO.model.dao.LoginClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.service.Login.LoginClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/testLoginCliente")
public class testLoginClienteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LoginClienteService service = new LoginClienteService();

            if (service.validateLoginCliente(request, response)) {
                LoginClienteDao dao = new LoginClienteDao();
                Cliente cliente = dao.findByCpf(request.getParameter("cpf"));

                if (cliente != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogado", cliente);
                    response.sendRedirect("test/sucesso.jsp");
                    return;
                }
            }

            response.sendRedirect("test/login.jsp?msg=erro");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("test/login.jsp?msg=erro");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/test/login.jsp").forward(request, response);
    }
}