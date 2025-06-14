package CALISTO.Test;

import CALISTO.model.dao.AgenciaDao;
import CALISTO.model.dao.ClienteDao;
import CALISTO.model.dao.ContaInvestimentoDao;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Investimento;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.PerfilRisco;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;
import CALISTO.model.persistence.util.TipoUsuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestContaInvestimento {
    public static void main(String[] args) {

        ContaInvestimentoDao ContaInvestimentoDao = new ContaInvestimentoDao();

        // ============ TESTE: CADASTRAR ============
//        try {
//            Endereco endereco = new Endereco();
//            endereco.setCep("04567890");
//            endereco.setLocal("Avenida dos Investidores");
//            endereco.setNumeroCasa(123);
//            endereco.setBairro("Jardim Econômico");
//            endereco.setCidade("São Paulo");
//            endereco.setEstado("SP");
//            endereco.setComplemento("Torre A");
//
//            Agencia agencia = new Agencia();
//            agencia.setNome("Agência Investimentos");
//            agencia.setCodigoAgencia("023");
//            agencia.setEndereco(endereco);
//            new AgenciaDao().save(agencia);
//
//            Cliente cliente = new Cliente();
//            cliente.setNome("Gilmar Investidor");
//            cliente.setCpf("98765432129");
//            cliente.setTelefone("11999990000");
//            cliente.setDataNascimento(LocalDate.of(1985, 5, 15));
//            cliente.setTipoUsuario(TipoUsuario.CLIENTE);
//            cliente.setSenhaHash("invest123");
//            cliente.setEndereco(endereco);
//            new ClienteDao().save(cliente);
//
//            Investimento investimento = new Investimento();
//            investimento.setNumeroConta("INV-9001");
//            investimento.setAgencia(agencia);
//            investimento.setCliente(cliente);
//            investimento.setSaldo(new BigDecimal("5000.00"));
//            investimento.setTipoConta(TipoConta.INVESTIMENTO);
//            investimento.setDataAbertura(LocalDateTime.now());
//            investimento.setPerfilRisco(PerfilRisco.ALTO);
//            investimento.setValorMinimo(new BigDecimal("1000.00"));
//            investimento.setTaxaRendimento(new BigDecimal("1.50"));
//
//            new ContaInvestimentoDao().cadastrar(investimento);
//
//            System.out.println("Conta investimento criada com sucesso!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // ============ TESTE: BUSCAR TODOS ============
//        ContaInvestimentoDao investimentoDao = new ContaInvestimentoDao();
//        List<Investimento> investimentos = investimentoDao.buscarTodos();
//
//        for (Investimento inv : investimentos) {
//            System.out.println("===== Conta Investimento ID: " + inv.getIdContaInvestimento() + " =====");
//            System.out.println("Número: " + inv.getNumeroConta());
//            System.out.println("Saldo: " + inv.getSaldo());
//            System.out.println("Tipo: " + inv.getTipoConta());
//            System.out.println("Status: " + inv.getStatus());
//            System.out.println("Data Abertura: " + inv.getDataAbertura());
//            System.out.println("Perfil Risco: " + inv.getPerfilRisco());
//            System.out.println("Valor Mínimo: " + inv.getValorMinimo());
//            System.out.println("Taxa Rendimento: " + inv.getTaxaRendimento());
//
//            System.out.println("--- Agência ---");
//            System.out.println(inv.getAgencia().getNome() + " (" + inv.getAgencia().getCodigoAgencia() + ")");
//
//            System.out.println("--- Cliente ---");
//            System.out.println(inv.getCliente().getNome() + " - CPF: " + inv.getCliente().getCpf());
//
//            System.out.println();
//        }

//        // ============ TESTE: BUSCAR POR ID ============
//        Investimento buscada = ContaInvestimentoDao.buscarPorConta(11);
//        System.out.println("Buscada - Saldo: " + buscada.getSaldo() + ", Perfil: " + buscada.getPerfilRisco());

//        // ============ TESTE: ALTERAR ============
//        Investimento buscada = ContaInvestimentoDao.buscarPorConta(11);
//        buscada.setSaldo(new BigDecimal("8000.00"));
//        buscada.setTaxaRendimento(new BigDecimal("2.00"));
//        buscada.setValorMinimo(new BigDecimal("1500.00"));
//        buscada.setStatus(Status.ATIVA);
//        boolean alterado = ContaInvestimentoDao.alterar(buscada);
//        System.out.println("Alteração: " + (alterado ? "sucesso" : "falha"));

//        // ============ TESTE: ALTERAR STATUS ============
//        Investimento buscada = ContaInvestimentoDao.buscarPorConta(11);
//        boolean statusAlterado = ContaInvestimentoDao.alterarStatus(buscada.getIdConta(), "BLOQUEADA");
//        System.out.println("Status alterado? " + statusAlterado);
//        System.out.println("Novo Status: " + ContaInvestimentoDao.buscarPorConta(buscada.getIdConta()).getStatus());
    }
}
