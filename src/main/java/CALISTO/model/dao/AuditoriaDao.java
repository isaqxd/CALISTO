package CALISTO.model.dao;

import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditoriaDao {
    public Auditoria save(Auditoria auditoria) throws SQLException {
        String sqlAuditoria = """
                INSERT INTO auditoria (usuario_id, acao, data_hora, detalhes)
                VALUES (?, ?, ?, ?)
                """;
        Connection con = null;
        try {
            con = Conexao.getConnection();
            con.setAutoCommit(false);

            // Inserir auditoria
            try (PreparedStatement stmt = con.prepareStatement(sqlAuditoria)) {
                stmt.setInt(1, auditoria.getUsuario().getIdUsuario());
                stmt.setString(2, auditoria.getAcao());
                stmt.setObject(3, auditoria.getDataHora());
                stmt.setString(4, auditoria.getDetalhes());
                stmt.executeUpdate();
                con.commit();
            }
            return auditoria;
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw new SQLException("Erro ao salvar auditoria: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
