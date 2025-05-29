package calisto.service;

import calisto.model.transacao.TipoDeTransacao;
import calisto.model.transacao.Transacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransacaoService {
    public static Transacao mapTransacao(ResultSet rs) throws SQLException {
        Transacao t = new Transacao();
        t.setIdTransacao(rs.getInt("id_transacao"));
        t.setContaOrigemId(rs.getInt("conta_origem_id"));
        t.setContaDestinoId(rs.getInt("conta_destino_id"));

        String tt = rs.getString("tipo_transacao");
        if (tt != null) {
            try {
                t.setTipoDeTransacao(TipoDeTransacao.valueOf(tt));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de transacao invalido: " + tt);
            }
        }

        t.setValor(rs.getBigDecimal("valor"));

        Timestamp ts = rs.getTimestamp("data_hora");
        if (ts != null) {
            t.setDataHora(ts.toLocalDateTime());
        }

        t.setDescricao(rs.getString("descricao"));
        return t;
    }
}
