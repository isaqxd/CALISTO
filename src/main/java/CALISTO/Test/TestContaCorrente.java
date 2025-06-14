package CALISTO.Test;

import CALISTO.model.dao.AgenciaDao;
import CALISTO.model.dao.ClienteDao;
import CALISTO.model.dao.ContaCorrenteDao;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Corrente;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;
import CALISTO.model.persistence.util.TipoUsuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class TestContaCorrente {
    public static void main(String[] args) {
        ContaCorrenteDao dao = new ContaCorrenteDao();

//        // ========== SALVAR ==========
//        try {
//            Endereco endereco = new Endereco();
//            endereco.setCep("01234000");
//            endereco.setLocal("Rua das Correntes");
//            endereco.setNumeroCasa(15);
//            endereco.setBairro("Bairro Novo");
//            endereco.setCidade("São Paulo");
//            endereco.setEstado("SP");
//            endereco.setComplemento("Casa");
//
//            Agencia agencia = new Agencia();
//            agencia.setNome("Agência Norte");
//            agencia.setCodigoAgencia("007");
//            agencia.setEndereco(endereco);
//            new AgenciaDao().save(agencia);
//
//            Cliente cliente = new Cliente();
//            cliente.setNome("Carlos da Silva");
//            cliente.setCpf("98765432100");
//            cliente.setTelefone("11999998888");
//            cliente.setDataNascimento(LocalDate.of(1985, 5, 20));
//            cliente.setTipoUsuario(TipoUsuario.CLIENTE);
//            cliente.setSenhaHash("abcd");
//            cliente.setEndereco(endereco);
//            new ClienteDao().save(cliente);
//
//            Corrente conta = new Corrente();
//            conta.setNumeroConta("9999-CC");
//            conta.setAgencia(agencia);
//            conta.setSaldo(new BigDecimal("5000.00"));
//            conta.setCliente(cliente);
//            conta.setTipoConta(TipoConta.CORRENTE);
//            conta.setDataAbertura(LocalDateTime.now());
//            conta.setConta(conta);
//            conta.setLimite(new BigDecimal("1000.00"));
//            conta.setTaxaManutencao(new BigDecimal("10.00"));
//            conta.setDataVencimento(LocalDate.now().plusMonths(1));
//            dao.cadastrar(conta);
//
//            System.out.println("Conta corrente criada com sucesso!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        ========== ALTERAR ==========
//        Corrente contaAlterar = dao.buscarPorConta(2);
//        if (contaAlterar != null) {
//            contaAlterar.setSaldo(new BigDecimal("4500.00"));
//            contaAlterar.setStatus(Status.ATIVA);
//            contaAlterar.setNumeroConta("9999-ALTERADA");
//            contaAlterar.setLimite(new BigDecimal("1500.00"));
//            contaAlterar.setTaxaManutencao(new BigDecimal("12.00"));
//            contaAlterar.setDataVencimento(LocalDate.now().plusMonths(2));
//
//            Agencia agencia = new Agencia();
//            agencia.setIdAgencia(1);
//            contaAlterar.setAgencia(agencia);
//
//            Cliente cliente = new Cliente();
//            cliente.setIdCliente(1);
//            contaAlterar.setCliente(cliente);
//
//            boolean sucesso = dao.alterar(contaAlterar);
//            System.out.println(sucesso ? "Conta corrente alterada com sucesso!" : "Falha ao alterar a conta corrente.");
//        }

        // ========== LISTAR ==========
//        List<Corrente> contas = dao.buscarTodos();
//        for (Corrente c : contas) {
//            System.out.println("===== Conta Corrente ID: " + c.getIdContaCorrente() + " =====");
//            System.out.println("Número da conta: " + c.getNumeroConta());
//            System.out.println("Saldo: R$" + c.getSaldo());
//            System.out.println("Status: " + c.getStatus());
//            System.out.println("Limite: R$" + c.getLimite());
//            System.out.println("Taxa manutenção: R$" + c.getTaxaManutencao());
//            System.out.println("Data vencimento: " + c.getDataVencimento());
//            System.out.println();
//        }

        // ========== BUSCAR POR ID ==========
//        Corrente c1 = dao.buscarPorConta(2);
//        if (c1 != null) {
//            System.out.println("Conta encontrada: " + c1.getNumeroConta() + " com saldo: " + c1.getSaldo());
//        }

//        // ========== ALTERAR STATUS ==========
//        boolean statusOk = dao.alterarStatus(2, "ENCERRADA");
//        System.out.println(statusOk ? "Status alterado com sucesso!" : "Falha ao alterar status.");
//
//        Corrente statusAlterada = dao.buscarPorConta(1);
//        if (statusAlterada != null) {
//            System.out.println("Novo status da conta: " + statusAlterada.getStatus());
//        }


    }
}
