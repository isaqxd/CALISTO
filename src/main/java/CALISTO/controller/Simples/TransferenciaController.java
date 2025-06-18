package CALISTO.controller.Simples;

import CALISTO.model.dao.*;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.Transacao.Transacao;
import CALISTO.model.persistence.util.TipoConta;
import CALISTO.model.persistence.util.TipoDeTransacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/transferencia")
public class TransferenciaController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            // Pega o ID da conta do formulário
            int idConta = Integer.parseInt(request.getParameter("idConta"));
            // Pega o ID da conta de destino
            int numeroContaDestino = Integer.parseInt(request.getParameter("numeroContaDestino"));
            // Converte valor do formulário para BigDecimal
            String transferenciaStr = request.getParameter("valor").replace(",", ".");
            BigDecimal valorSaque = new BigDecimal(transferenciaStr);

            // Busca a conta usando DAO
            ContaDao contaDaoAbstrato = new ContaPoupancaDao();
            Conta conta = contaDaoAbstrato.buscarPorId(idConta);

            if (conta == null) {
                request.setAttribute("mensagem", "Conta não encontrada.");
                request.getRequestDispatcher("/novaVida/portalCliente/transferencia.jsp").forward(request, response);
                return;
            }

            // Define o DAO correto de acordo com o tipo
            ContaDao contaDao = getDaoPorTipo(conta.getTipoConta());

            boolean sucesso = contaDao.transferir(idConta, numeroContaDestino, valorSaque);

            if (sucesso) {
                //CRIA UM REGISTRO NA TABELA TRANSACAO
                Transacao transacao = new Transacao(idConta, numeroContaDestino, TipoDeTransacao.TRANSFERENCIA, valorSaque, "Transferencia realizada com sucesso.");
                TransacaoDao transacaoDao = new TransacaoDao();
                transacaoDao.registrar(transacao);
            }

            String mensagem = sucesso
                    ? "Transferencia realizada com sucesso."
                    : "Não foi possível realizar o saque.";

            request.setAttribute("mensagem", mensagem);
            request.getRequestDispatcher("/novaVida/portalCliente/transferencia.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao processar transferencia: " + e.getMessage());
            request.getRequestDispatcher("/novaVida/portalCliente/transferencia.jsp").forward(request, response);
        }
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