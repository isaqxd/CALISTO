package calisto.service;

import calisto.model.conta.Conta;
import calisto.model.conta.Status;
import calisto.model.conta.TipoConta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContaService {
    public static Conta mapConta(ResultSet rs) throws SQLException {
        Conta c = new Conta();
        c.setIdConta(rs.getInt("id_conta"));
        c.setNumeroConta(rs.getString("numero_conta"));
        c.setAgenciaId(rs.getInt("agencia_id"));
        c.setSaldo(rs.getBigDecimal("saldo"));

        String tipoConta = rs.getString("tipo_conta");
        if (tipoConta != null) {
            try {
                c.setTipoConta(TipoConta.valueOf(tipoConta));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de conta invalido: " + tipoConta);
            }
        }

        c.setClienteId(rs.getInt("cliente_id"));

        Timestamp ts = rs.getTimestamp("data_abertura");
        if (ts != null) {
            c.setDataAbertura(ts.toLocalDateTime());
        }

        String status = rs.getString("status");
        if (status != null) {
            try {
                c.setStatus(Status.valueOf(status));
            } catch (IllegalArgumentException e) {
                System.out.println("Status da conta invalido: " + status);
            }
        }

        return c;
    }
}
