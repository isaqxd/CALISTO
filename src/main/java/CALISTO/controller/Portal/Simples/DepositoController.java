package CALISTO.controller.Portal.Simples;

import CALISTO.model.dao.ContaCorrenteDao;
import CALISTO.model.dao.ContaDao;
import CALISTO.model.dao.ContaInvestimentoDao;
import CALISTO.model.dao.ContaPoupancaDao;
import CALISTO.model.persistence.Conta.Conta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/deposito")
public class DepositoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idConta = Integer.parseInt(request.getParameter("numeroConta"));
            String depositoStr = request.getParameter("valor").replace(",", ".");
            BigDecimal valorDeposito = new BigDecimal(depositoStr);

            // Buscar a conta para identificar o tipo
            ContaDao contaDaoAbstrato = new ContaPoupancaDao(); // Pode ser qualquer um, pois o método está na superclasse
            Conta conta = contaDaoAbstrato.buscarPorId(idConta);

            if (conta == null) {
                request.setAttribute("mensagem", "Conta não encontrada.");
                request.getRequestDispatcher("/novaVida/portalCliente/deposito.jsp").forward(request, response);
                return;
            }

            // Delegar o depósito à DAO correta
            ContaDao contaDao;
            switch (conta.getTipoConta()) {
                case CORRENTE:
                    contaDao = new ContaCorrenteDao();
                    break;
                case INVESTIMENTO:
                    contaDao = new ContaInvestimentoDao();
                    break;
                case POUPANCA:
                    contaDao = new ContaPoupancaDao();
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de conta não suportado.");
            }

            boolean sucesso = contaDao.depositar(idConta, valorDeposito);

            if (sucesso) {
                request.setAttribute("mensagem", "Depósito realizado com sucesso.");
            } else {
                request.setAttribute("mensagem", "Não foi possível realizar o depósito.");
            }

            request.getRequestDispatcher("/novaVida/portalCliente/deposito.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao processar depósito: " + e.getMessage());
            request.getRequestDispatcher("/novaVida/portalCliente/deposito.jsp").forward(request, response);
        }
    }
}
