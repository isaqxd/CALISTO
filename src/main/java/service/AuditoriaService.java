package service;

import model.auditoria.Auditoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AuditoriaService {
    public static Auditoria mapAuditoria(ResultSet rs) throws SQLException {
        Auditoria a = new Auditoria();
        a.setIdAuditoria(rs.getInt("id_auditoria"));
        a.setUsuarioId(rs.getInt("usuario_id"));
        a.setAcao(rs.getString("acao"));

        Timestamp st = rs.getTimestamp("data_hora");
        if (st != null) {
            a.setDataHora(st.toLocalDateTime());
        }

        a.setDetalhes(rs.getString("detalhes"));
        return a;
    }
}
