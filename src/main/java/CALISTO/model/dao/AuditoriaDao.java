package CALISTO.model.dao;

import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public boolean blockLoginFromAuditoria(int usuarioId) {
        String sql = """
                SELECT COUNT(*)
                FROM auditoria
                WHERE usuario_id = ?
                    AND acao = 'LOGIN_FALHA'
                    AND data_hora > (NOW() - INTERVAL 10 MINUTE)
                """;
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int tentativasFalhas = rs.getInt(1);
                return tentativasFalhas >= 3;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
