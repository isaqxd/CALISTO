package CALISTO.Test;

import CALISTO.model.dao.AgenciaDao;
import CALISTO.model.dao.ClienteDao;
import CALISTO.model.dao.ContaDao;
import CALISTO.model.dao.ContaPoupancaDao;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.Conta.Poupanca;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;
import CALISTO.model.persistence.util.TipoUsuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestContaPoupanca {
    public static void main(String[] args) {

//        **************** Alter ****************
//        ContaPoupancaDao dao = new ContaPoupancaDao();
//
//        // Suponha que exista uma conta poupança com conta_id = 1
//        Poupanca conta = dao.buscarPorConta(1);
//
//        if (conta != null) {
//            // Modificações nos dados da conta
//            conta.setSaldo(new BigDecimal("2500.75"));
//            conta.setStatus(Status.BLOQUEADA); // Alterando status
//            conta.setTaxaRendimento(new BigDecimal("1.75")); // Nova taxa
//            conta.setUltimoRendimento(LocalDateTime.now()); // Atualizando rendimento
//
//            // Modificando dados auxiliares, se necessário
//            conta.setNumeroConta("0001-XYZ");
//            conta.setTipoConta(TipoConta.POUPANCA);
//            conta.setDataAbertura(LocalDateTime.now().minusYears(1));
//
//            // Exemplo: agência e cliente simulados
//            Agencia agencia = new Agencia();
//            agencia.setIdAgencia(1); // id válido já existente
//            conta.setAgencia(agencia);
//
//            Cliente cliente = new Cliente();
//            cliente.setIdCliente(1); // id válido já existente
//            conta.setCliente(cliente);
//
//            // Chama o método de alteração
//            boolean sucesso = dao.alterar(conta);
//
//            if (sucesso) {
//                System.out.println("Conta poupança alterada com sucesso!");
//            } else {
//                System.out.println("Falha ao alterar a conta poupança.");
//            }
//        } else {
//            System.out.println("Conta poupança não encontrada.");
//        }
//        **************************************************************

//        **************** FIND ALL ****************
//        ContaPoupancaDao poupancaDao = new ContaPoupancaDao();
//        List<Poupanca> poupancas = poupancaDao.buscarTodos();
//
//        if (poupancas.isEmpty()) {
//            System.out.println("Nenhuma conta poupança encontrada.");
//        } else {
//            for (Poupanca p : poupancas) {
//                System.out.println("===== Conta Poupança ID: " + p.getIdContaPoupanca() + " =====");
//                System.out.println("Número da conta: " + p.getNumeroConta());
//                System.out.println("Saldo: R$" + p.getSaldo());
//                System.out.println("Tipo da conta: " + p.getTipoConta());
//                System.out.println("Status: " + p.getStatus());
//                System.out.println("Data de abertura: " + p.getDataAbertura());
//                System.out.println("Taxa de rendimento: " + p.getTaxaRendimento());
//                System.out.println("Último rendimento: " + p.getUltimoRendimento());
//
//                Agencia agencia = p.getAgencia();
//                if (agencia != null) {
//                    System.out.println("--- Agência ---");
//                    System.out.println("ID: " + agencia.getIdAgencia());
//                    System.out.println("Nome: " + agencia.getNome());
//                    System.out.println("Código: " + agencia.getCodigoAgencia());
//                }
//
//                Cliente cliente = p.getCliente();
//                if (cliente != null) {
//                    System.out.println("--- Cliente ---");
//                    System.out.println("ID: " + cliente.getIdCliente());
//                    System.out.println("Nome: " + cliente.getNome());
//                    System.out.println("CPF: " + cliente.getCpf());
//                    System.out.println("Data de nascimento: " + cliente.getDataNascimento());
//                    System.out.println("Telefone: " + cliente.getTelefone());
//                }
//
//                System.out.println(); // Linha em branco entre contas
//            }
//        }
//        ********************************************

//        **************** SAVE ****************
//        try {
//
//            Endereco endereco = new Endereco();
//            endereco.setCep("01234000");
//            endereco.setLocal("Rua das Contas");
//            endereco.setNumeroCasa(12);
//            endereco.setBairro("Centro");
//            endereco.setCidade("São Paulo");
//            endereco.setEstado("SP");
//            endereco.setComplemento("Ap 45");
//
//            // 1. Criar agência
//            Agencia agencia = new Agencia();
//            agencia.setNome("Agência Central");
//            agencia.setCodigoAgencia("006");
//            agencia.setEndereco(endereco);
//            new AgenciaDao().save(agencia);
//
//            // 2. Criar cliente completo
//            Cliente cliente = new Cliente();
//            cliente.setNome("Gilmar Viriato");
//            cliente.setCpf("12345678908");
//            cliente.setTelefone("11988887777");
//            cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
//            cliente.setTipoUsuario(TipoUsuario.CLIENTE);
//            cliente.setSenhaHash("1234");
//            cliente.setEndereco(endereco);
//
//            new ClienteDao().save(cliente);
//
//            Poupanca contaPoupanca = new Poupanca();
//
//            // 3. Criar contaPoupanca poupança
//            contaPoupanca.setNumeroConta("123456-8");
//            contaPoupanca.setAgencia(agencia);
//            contaPoupanca.setSaldo(new BigDecimal("1000.00"));
//            contaPoupanca.setCliente(cliente);
//            contaPoupanca.setTipoConta(TipoConta.POUPANCA);
//            contaPoupanca.setDataAbertura(LocalDateTime.now());
//            contaPoupanca.setConta(contaPoupanca);
//            contaPoupanca.setTaxaRendimento(new BigDecimal("0.5"));
//            contaPoupanca.setUltimoRendimento(LocalDateTime.now());
//            new ContaPoupancaDao().cadastrar(contaPoupanca);
//
//            System.out.println("Conta poupança criada com sucesso!");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            *******************************************************

//            **************** FIND BY ID ****************
//            ContaPoupancaDao contaPoupancaDao = new ContaPoupancaDao();
//            Poupanca poupanca = contaPoupancaDao.buscarPorConta(1);
//            System.out.println(poupanca.getSaldo());
//            ********************************************

//        **************** ALTERAR STATUS ****************
//        ContaPoupancaDao contaPoupancaDao = new ContaPoupancaDao();
//        boolean alterou = contaPoupancaDao.alterarStatus(1,"BLOQUEADA");
//        System.out.println(alterou);
//
//        Poupanca poupanca = contaPoupancaDao.buscarPorConta(1);
//        System.out.println(poupanca.getStatus());
//        ************************************************

   }
}
