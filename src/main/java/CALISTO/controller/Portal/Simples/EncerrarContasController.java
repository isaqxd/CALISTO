package CALISTO.controller.Portal.Simples;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.dao.ContaDao;
import CALISTO.model.dao.ContaPoupancaDao;
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

        String mensagem = null;

        try {
            // Recupera dados do formulário
            int idConta = Integer.parseInt(request.getParameter("idConta"));
            String novoStatus = request.getParameter("status");

            // Usa DAO para alterar o status
            ContaDao absDao = new ContaPoupancaDao();
            boolean sucesso = absDao.alterarStatus(idConta, novoStatus);

            mensagem = sucesso
                    ? "Status da conta atualizado com sucesso!"
                    : "Falha ao atualizar o status da conta.";

        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro ao atualizar conta: " + e.getMessage();
        }

        // Define a mensagem como atributo
        request.setAttribute("mensagem", mensagem);

        // Recarrega a tela de encerramento com dados atualizados
        // Precisamos do CPF ou cliente novamente para voltar à tela corretamente
        String cpf = request.getParameter("cpf");
        if (cpf != null && !cpf.isEmpty()) {
            ClienteDao dao = new ClienteDao();
            Cliente cliente = dao.innerToRequestSession(cpf);
            request.setAttribute("cliente", cliente);
        }

        request.getRequestDispatcher("novaVida/encerrarConta.jsp").forward(request, response);
    }
}
