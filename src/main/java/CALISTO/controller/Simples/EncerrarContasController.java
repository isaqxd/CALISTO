package CALISTO.controller.Simples;

import CALISTO.model.dao.*;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/encerrarContas")
public class EncerrarContasController extends HttpServlet {
    private static final LocalDateTime AGORA = LocalDateTime.now();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");

        ClienteDao dao = new ClienteDao();
        Cliente cliente = dao.innerToRequestSession(cpf);

        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("novaVida/portalFuncionario/encerrarConta.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        FuncionarioDao dao = new FuncionarioDao();
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");

        String mensagem = null;

        try {
            // Recupera dados do formulário
            int idConta = Integer.parseInt(request.getParameter("idConta"));
            String novoStatus = request.getParameter("status");

            // Usa DAO para alterar o status
            ContaDao absDao = new ContaPoupancaDao();
            boolean sucesso = absDao.alterarStatus(idConta, novoStatus);
            Auditoria auditoria = new Auditoria();
            AuditoriaDao daoAuditoria = new AuditoriaDao();

            String acao = request.getParameter("acao");
            String detalhes = request.getParameter("detalhes");

            auditoria.setAcao(acao);
            auditoria.setDataHora(AGORA);
            auditoria.setDetalhes(detalhes);
            auditoria.setUsuario(funcionario);

            daoAuditoria.save(auditoria);

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
            ClienteDao daoCliente = new ClienteDao();
            Cliente cliente = daoCliente.innerToRequestSession(cpf);
            request.setAttribute("cliente", cliente);
        }

        request.getRequestDispatcher("novaVida/encerrarConta.jsp").forward(request, response);
    }
}