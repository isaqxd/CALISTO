package CALISTO.controller.Portal;

import CALISTO.model.dao.*;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.Conta.Corrente;
import CALISTO.model.persistence.Conta.Investimento;
import CALISTO.model.persistence.Conta.Poupanca;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.PerfilRisco;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/funcionarios")
public class FuncionariosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String acao = request.getParameter("acao");

        FuncionarioDao funcionarioDao = new FuncionarioDao();
        AgenciaDao agenciaDao = new AgenciaDao();
        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> clientes = clienteDao.findAll();
        List<Funcionario> funcionarios = funcionarioDao.findAll();
        List<Agencia> agencias = agenciaDao.findAll();

        request.setAttribute("clientes", clientes);
        request.setAttribute("funcionarios", funcionarios);
        request.setAttribute("agencias", agencias);

        if ("buscarCliente".equals(acao)) {
            String cpf = request.getParameter("cpf");
            Cliente cliente = clienteDao.findByCpf(cpf);
            if (cliente != null) {
                request.setAttribute("clienteEncontrado", cliente);
            } else {
                request.setAttribute("erroCliente", "Cliente não encontrado.");
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("test/portalfuncionario.jsp");
        dispatcher.forward(request, response);
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String tipoConta = request.getParameter("tipoConta");
//
//        try {
//            Conta conta = montarContaBase(request, tipoConta); // método auxiliar abaixo
//
//            // Salvar a conta no banco usando o DAO correspondente
//            int idContaGerada = salvarContaBase(conta); // método auxiliar abaixo
//            conta.setIdConta(idContaGerada);
//
//            response.sendRedirect("funcionarios?mensagem=contaCriada");
//
//        } catch (Exception e) {
//            e.printStackTrace(); // só pra debug
//            request.setAttribute("erro", "Erro ao criar conta: " + e.getMessage());
//            request.getRequestDispatcher("test/portalfuncionario.jsp").forward(request, response);
//        }
//    }

    private Conta montarContaBase(HttpServletRequest request, String tipoConta) {
        String numeroConta = request.getParameter("numeroConta");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idAgencia = Integer.parseInt(request.getParameter("idAgencia"));
        BigDecimal saldo = new BigDecimal(request.getParameter("saldo"));

        Conta conta;

        switch (tipoConta) {
            case "CORRENTE":
                Corrente corrente = new Corrente();
                corrente.setLimite(new BigDecimal(request.getParameter("limite")));
                corrente.setDataVencimento(LocalDate.parse(request.getParameter("dataVencimento")));
                corrente.setTaxaManutencao(new BigDecimal(request.getParameter("taxaManutencao")));
                conta = corrente;
                break;

            case "POUPANCA":
                Poupanca poupanca = new Poupanca();
                poupanca.setTaxaRendimento(new BigDecimal(request.getParameter("taxaRendimento")));
                poupanca.setUltimoRendimento(LocalDateTime.now());
                conta = poupanca;
                break;

            case "INVESTIMENTO":
                Investimento investimento = new Investimento();
                investimento.setPerfilRisco(PerfilRisco.valueOf(request.getParameter("perfilRisco")));
                investimento.setValorMinimo(new BigDecimal(request.getParameter("valorMinimo")));
                investimento.setTaxaRendimento(new BigDecimal(request.getParameter("taxaRendimentoBase")));
                conta = investimento;
                break;

            default:
                throw new IllegalArgumentException("Tipo de conta inválido.");
        }

        conta.setNumeroConta(numeroConta);
        conta.setCliente(new Cliente(idCliente));
        conta.setAgencia(new Agencia(idAgencia));
        conta.setSaldo(saldo);
        conta.setTipoConta(TipoConta.valueOf(tipoConta));
        conta.setDataAbertura(LocalDateTime.now());
        conta.setStatus(Status.ATIVA);

        return conta;
    }

//    private int salvarContaBase(Conta conta) throws SQLException {
//        if (conta instanceof Corrente) {
//            return new ContaCorrenteDao().salvar(conta);
//        } else if (conta instanceof Poupanca) {
//            return new ContaPoupancaDao().salvar(conta);
//        } else if (conta instanceof Investimento) {
//            return new ContaInvestimentoDao().salvar(conta);
//        } else {
//            throw new IllegalArgumentException("Tipo de conta não suportado.");
//        }
//    }
}
