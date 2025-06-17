package CALISTO.model.service;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.Usuario.Usuario;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;


public class ContaService {
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
