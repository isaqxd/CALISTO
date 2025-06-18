package CALISTO.controller.Simples;

import CALISTO.model.dao.*;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.TipoConta;
import CALISTO.model.service.ContaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/encerrarContas")
public class EncerrarContasController extends HttpServlet {
    private final ClienteDao clienteDao = new ClienteDao();
    private final ContaService contaService = new ContaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cpf = request.getParameter("cpf");
            Cliente cliente = clienteDao.innerToRequestSession(cpf);
            request.setAttribute("cliente", cliente);
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao buscar cliente: " + e.getMessage());
        }
        request.getRequestDispatcher("novaVida/encerrarConta.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String mensagem = null;
        Cliente cliente = null;

        try {
            // Recupera dados do formulário
            String cpf = request.getParameter("cpf");
            int idConta = Integer.parseInt(request.getParameter("idConta"));
            String novoStatus = request.getParameter("status");
            String acaoAuditoria = request.getParameter("acaoAuditoria");
            String detalhesAuditoria = request.getParameter("detalhesAuditoria");

            // Validações básicas
            if (acaoAuditoria == null || acaoAuditoria.isEmpty() ||
                detalhesAuditoria == null || detalhesAuditoria.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos de auditoria são obrigatórios");
            }

            // Busca cliente e verifica conta
            cliente = clienteDao.innerToRequestSession(cpf);

            // Verifica se pode encerrar a conta
            if (!contaService.podeEncerrarConta(cliente, idConta)) {
                mensagem = "Esta conta não pode ser encerrada pois possui saldo negativo.";
                request.setAttribute("cliente", cliente);
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("novaVida/encerrarConta.jsp").forward(request, response);
                return;
            }

            // Obtém funcionário logado
            Funcionario funcionarioLogado = (Funcionario) session.getAttribute("funcionarioLogado");
            if (funcionarioLogado == null) {
                throw new IllegalStateException("Apenas funcionários podem encerrar contas");
            }

            // Encontra a conta específica no cliente
            Conta conta = cliente.getContas().stream()
                    .filter(c -> c.getIdConta() == idConta)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada para este cliente"));

            // Obtém DAO específico pelo tipo da conta
            ContaDao contaDao = getDaoPorTipo(conta.getTipoConta());

            // Encerra conta com auditoria
            boolean sucesso = contaService.encerrarContaComAuditoria(
                    contaDao, idConta, novoStatus,
                    funcionarioLogado, acaoAuditoria, detalhesAuditoria
            );

            mensagem = sucesso
                    ? "Status da conta atualizado com sucesso e auditoria registrada!"
                    : "Falha ao atualizar o status da conta.";

        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro ao processar o encerramento: " + e.getMessage();
        }

        request.setAttribute("cliente", cliente);
        request.setAttribute("mensagem", mensagem);
        request.getRequestDispatcher("novaVida/encerrarConta.jsp").forward(request, response);
    }

    private ContaDao getDaoPorTipo(TipoConta tipo) {
        switch (tipo) {
            case CORRENTE:
                return new ContaCorrenteDao();
            case INVESTIMENTO:
                return new ContaInvestimentoDao();
            case POUPANCA:
                return new ContaPoupancaDao();
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado: " + tipo);
        }
    }
}
