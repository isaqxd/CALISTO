package CALISTO.controller.Simples;

import CALISTO.model.dao.*;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Corrente;
import CALISTO.model.persistence.Conta.Investimento;
import CALISTO.model.persistence.Conta.Poupanca;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.PerfilRisco;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.service.ContaService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/abrirConta")
public class AbrirContaController extends HttpServlet {
    private static final LocalDateTime DATA_ABERTURA_CONTA_PADRAO = LocalDateTime.now();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Pegando os parâmetros do formulário
        String stringSaldo = request.getParameter("saldo");
        String tipoConta = request.getParameter("tipoConta");
        String idCliente = String.valueOf(Integer.parseInt(request.getParameter("idCliente")));
        String idAgencia = String.valueOf(Integer.parseInt(request.getParameter("idAgencia")));


        // 2. Convertendo o saldo para BigDecimal
        BigDecimal decimalSaldo = null;
        if (stringSaldo != null && !stringSaldo.isEmpty()) {
            try {
                decimalSaldo = new BigDecimal(stringSaldo.replace(",", "."));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("erro", "Saldo inválido!");
                request.getRequestDispatcher("/erro.jsp").forward(request, response);
                return;
            }
        }

        // 3. Buscando Cliente e Agencia no banco de dados
        Cliente cliente = new ClienteDao().findById(Integer.parseInt(idCliente));
        Agencia agencia = new AgenciaDao().findById(Integer.parseInt(idAgencia));

        // Verificações básicas
        if (cliente == null || agencia == null) {
            request.setAttribute("erro", "Cliente ou agência não encontrados.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            return;
        }


        // 4. Inserindo algoritmo de Luhn
        ContaService service = new ContaService();
        String numeroConta = service.gerarNumeroConta();

        // 5. Inserindo cliente cadastro na auditoria;

        HttpSession session = request.getSession();
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
        try {
            service.gerarAuditoriaConta(funcionario);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 6. Criando o objetos
        if ("CORRENTE".equalsIgnoreCase(tipoConta)) {
            Corrente corrente = new Corrente();
            corrente.setCliente(cliente);
            corrente.setAgencia(agencia);
            corrente.setSaldo(decimalSaldo);
            corrente.setNumeroConta(numeroConta);
            corrente.setDataAbertura(DATA_ABERTURA_CONTA_PADRAO);
            corrente.setStatus(Status.ATIVA); // Supondo que você tenha um enum Status

            // Atribuindo informações específicas da conta corrente
            corrente.setLimite(new BigDecimal("500")); // Exemplo fixo
            corrente.setTaxaManutencao(new BigDecimal("10.00")); // Exemplo fixo
            corrente.setDataVencimento(LocalDate.now().plusMonths(1)); // Exemplo

            try {
                new ContaCorrenteDao().save(corrente);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        } else if ("POUPANCA".equalsIgnoreCase(tipoConta)) {
            Poupanca poupanca = new Poupanca();
            poupanca.setCliente(cliente);
            poupanca.setAgencia(agencia);
            poupanca.setSaldo(decimalSaldo);
            poupanca.setNumeroConta(numeroConta);
            poupanca.setDataAbertura(DATA_ABERTURA_CONTA_PADRAO);
            poupanca.setStatus(Status.ATIVA);

            // ESPECIFICOS DE POUPANCA
            poupanca.setTaxaRendimento(BigDecimal.valueOf(0.5));
            poupanca.setUltimoRendimento(DATA_ABERTURA_CONTA_PADRAO);

            try {
                new ContaPoupancaDao().save(poupanca);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if ("INVESTIMENTO".equalsIgnoreCase(tipoConta)) {
            Investimento investimento = new Investimento();
            String tipoPerfilRisco = request.getParameter("perfilRisco");


            investimento.setCliente(cliente);
            investimento.setAgencia(agencia);
            investimento.setSaldo(decimalSaldo);
            investimento.setNumeroConta(numeroConta);
            investimento.setDataAbertura(DATA_ABERTURA_CONTA_PADRAO);
            investimento.setStatus(Status.ATIVA);

            //ESPECIFICOS DE INVESTIMENTO
            investimento.setPerfilRisco(PerfilRisco.valueOf(tipoPerfilRisco));
            investimento.setValorMinimo(BigDecimal.valueOf(100));
            investimento.setTaxaRendimento(BigDecimal.valueOf(0.05));

            try {
                new ContaInvestimentoDao().save(investimento);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            request.setAttribute("erro", "Tipo de conta não suportado.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            return;
        }
        request.setAttribute("mensagem", "Conta criada com sucesso!");
        request.getRequestDispatcher("novaVida/portalfuncionario.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        ClienteDao clienteDao = new ClienteDao();
        AgenciaDao agenciaDao = new AgenciaDao();

        List<Cliente> clientes = clienteDao.findAll();
        List<Agencia> agencias = agenciaDao.findAll();

        request.setAttribute("clientes", clientes);
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("novaVida/cadastrarConta.jsp");
        dispatcher.forward(request, response);
    }

}
