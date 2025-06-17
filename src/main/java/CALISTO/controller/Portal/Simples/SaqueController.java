package CALISTO.controller.Portal.Simples;

import CALISTO.model.dao.ContaCorrenteDao;
import CALISTO.model.dao.ContaDao;
import CALISTO.model.dao.ContaInvestimentoDao;
import CALISTO.model.dao.ContaPoupancaDao;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.util.TipoConta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/saque")
public class SaqueController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            // Pega o ID da conta do formulário
            int idConta = Integer.parseInt(request.getParameter("idConta"));

            // Converte valor do formulário para BigDecimal
            String saqueStr = request.getParameter("valor").replace(",", ".");
            BigDecimal valorSaque = new BigDecimal(saqueStr);

            // Busca a conta usando DAO
            ContaDao contaDaoAbstrato = new ContaPoupancaDao();
            Conta conta = contaDaoAbstrato.buscarPorId(idConta);

            if (conta == null) {
                request.setAttribute("mensagem", "Conta não encontrada.");
                request.getRequestDispatcher("/novaVida/portalCliente/saque.jsp").forward(request, response);
                return;
            }

            // Define o DAO correto de acordo com o tipo
            ContaDao contaDao = getDaoPorTipo(conta.getTipoConta());

            boolean sucesso = contaDao.sacar(idConta, valorSaque);

            String mensagem = sucesso
                    ? "Saque realizado com sucesso."
                    : "Não foi possível realizar o saque.";

            request.setAttribute("mensagem", mensagem);
            request.getRequestDispatcher("/novaVida/portalCliente/saque.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao processar saque: " + e.getMessage());
            request.getRequestDispatcher("/novaVida/portalCliente/saque.jsp").forward(request, response);
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