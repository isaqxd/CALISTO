package calisto.service;

import calisto.model.conta.poupanca.Poupanca;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



public class ContaPoupancaService {
    public static Poupanca mapCP(ResultSet rs) throws SQLException {
        Poupanca cpo = new Poupanca();
        cpo.setIdContaPoupanca(rs.getInt("id_conta_poupanca"));
        cpo.setContaId(rs.getInt("conta_id"));
        cpo.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));

        Timestamp st = rs.getTimestamp("ultimo_rendimento");
        if (st != null) {
            cpo.setUltimoRendimento(st.toLocalDateTime());
        }

        return cpo;
    }
}
