package calisto.service;

import calisto.model.relatorio.Relatorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



public class RelatorioService {
    public static Relatorio mapRelatorio(ResultSet rs) throws SQLException {
        Relatorio r = new Relatorio();
        r.setIdRelatorio(rs.getInt("id_relatorio"));
        r.setIdFuncioario(rs.getInt("funcionario_id"));
        r.setTipoRelatorio(rs.getString("tipo_relatorio"));
        Timestamp time = rs.getTimestamp("data_geracao");
        if (time != null) {
            r.setDataGeracao(time.toLocalDateTime());
        }
        r.setConteudo(rs.getString("conteudo"));
        return r;
    }
}
