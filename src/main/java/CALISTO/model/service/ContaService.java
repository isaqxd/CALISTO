package CALISTO.model.service;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.ContaDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;


public class ContaService {
    private final AuditoriaDao auditoriaDao = new AuditoriaDao();
    private static final LocalDateTime AGORA = LocalDateTime.now();

    public boolean gerarAuditoriaConta(Funcionario funcionario) throws SQLException {
        Auditoria auditoria = new Auditoria(
                funcionario,
                "CRIAR_CONTA",
                AGORA,
                "CONTA CRIADA PARA O USUARIO"
        );
        new AuditoriaDao().save(auditoria);
        return true;
    }

    public boolean podeEncerrarConta(Cliente cliente, int idConta) {
        if (cliente == null || cliente.getContas() == null) return false;

        for (Conta conta : cliente.getContas()) {
            // Verifica apenas a conta específica
            if (conta.getIdConta() == idConta) {
                return conta.getSaldo().compareTo(BigDecimal.ZERO) >= 0;
            }
        }

        // Caso não encontre a conta, não permite encerrar
        return false;
    }

    public boolean encerrarContaComAuditoria(ContaDao contaDao, int idConta, String novoStatus,
                                             Funcionario funcionario, String acao, String detalhes) {
        try {
            // Altera status da conta
            boolean sucesso = contaDao.alterarStatus(idConta, novoStatus);

            if (sucesso) {
                registrarAuditoria(funcionario, acao, detalhes, idConta);
            }

            return sucesso;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao encerrar conta com auditoria", e);
        }
    }

    private void registrarAuditoria(Funcionario funcionario, String acao, String detalhes, int idConta) throws SQLException {
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(funcionario); // Aqui usamos o Funcionario como Usuario (polimorfismo)
        auditoria.setAcao(acao);
        auditoria.setDataHora(LocalDateTime.now());
        auditoria.setDetalhes(detalhes + " (Conta ID: " + idConta + ")");

        auditoriaDao.save(auditoria);
    }

    public String gerarNumeroConta() {
        String base = gerarBaseAleatoria();
        int digito = calcularDigitoLuhn(base);
        return base + digito;
    }

    private String gerarBaseAleatoria() {
        Random rand = new Random();
        StringBuilder base = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            base.append(rand.nextInt(10));
        }
        return base.toString();
    }

    private int calcularDigitoLuhn(String base) {
        int soma = 0;
        boolean alternar = true;

        for (int i = base.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(base.charAt(i));
            if (alternar) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            soma += n;
            alternar = !alternar;
        }

        int digitoVerificador = (10 - (soma % 10)) % 10;
        return digitoVerificador;
    }
}