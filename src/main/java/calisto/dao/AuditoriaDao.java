package calisto.dao;

import calisto.model.auditoria.Auditoria;
import calisto.model.usuario.Usuario;
import calisto.util.Conexao;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditoriaDao {
    private void preencherPreparedStatement(@NotNull PreparedStatement ps, @NotNull Auditoria auditoria) throws SQLException {
        ps.setInt(1, auditoria.getUsuario().getIdUsuario());
        ps.setString(2, auditoria.getAcao());
        ps.setTimestamp(3, java.sql.Timestamp.valueOf(auditoria.getDataHora()));
        ps.setString(4, auditoria.getDetalhes());
    }

    @NotNull
    private Auditoria montarAuditoria(@NotNull ResultSet rs) throws SQLException {
        return new Auditoria()
                .setIdAuditoria(rs.getInt("id_auditoria"))
                .setUsuario(new Usuario().setIdUsuario(rs.getInt("id_usuario")))
                .setAcao(rs.getString("acao"))
                .setDataHora(rs.getTimestamp("data_hora").toLocalDateTime())
                .setDetalhes(rs.getString("detalhes"));
    }

    public Auditoria insert(Auditoria auditoria) {
        String sql = "INSERT INTO auditoria (usuario_id, acao, data_hora, detalhes) VALUES (?,?,?,?)";

        try (Connection con = Conexao.conexao();) {
            PreparedStatement ps = con.prepareStatement(sql);
            preencherPreparedStatement(ps, auditoria);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                return auditoria;
            } else {
                throw new RuntimeException("Falha ao inserir auditoria, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir auditoria.", e);
        }
    }

    public Auditoria update(Auditoria auditoria) {
        String sql = "UPDATE auditoria SET usuario_id = ?, acao = ?, data_hora = ?, detalhes = ? WHERE id_auditoria = ?";

        try (Connection con = Conexao.conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            preencherPreparedStatement(ps, auditoria);
            ps.setInt(5, auditoria.getIdAuditoria());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                return auditoria;
            } else {
                throw new RuntimeException("Falha ao atualizar auditoria, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar auditoria.", e);
        }
    }
}
