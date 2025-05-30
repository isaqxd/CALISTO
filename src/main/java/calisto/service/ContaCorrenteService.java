package calisto.service;

import calisto.model.conta.corrente.Corrente;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaCorrenteService {
    public static Corrente mapCC(ResultSet rs) throws SQLException {
        Corrente cc = new Corrente();
        cc.setIdContaCorrente(rs.getInt("id_conta_corrente"));
        cc.setContaId(rs.getInt("conta_id"));
        cc.setLimite(rs.getBigDecimal("limite"));

        Date vencimento = rs.getDate("data_vencimento");
        if (vencimento != null) {
            cc.setDataVencimento(vencimento.toLocalDate());
        }

        cc.setTaxaManutencao(rs.getBigDecimal("taxa_manutencao"));

        return cc;
    }
}
