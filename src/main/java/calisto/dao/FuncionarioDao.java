package calisto.dao;

import calisto.model.funcionario.Cargo;
import calisto.model.funcionario.Funcionario;
import calisto.model.usuario.Usuario;
import calisto.util.Conexao;
import org.jetbrains.annotations.NotNull;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDao {

    public void preencherPreparedStatement(@NotNull PreparedStatement ps, @NotNull Funcionario funcionario) throws SQLException {
        ps.setInt(1, funcionario.getUsuario().getIdUsuario());
        ps.setString(2, funcionario.getCodigoFuncionario());
        ps.setString(3, funcionario.getCargo().name());
        ps.setInt(4, funcionario.getSupervisor());
    }

    @NotNull
    public Funcionario montarFuncionario(@NotNull ResultSet rs) throws SQLException {
        return new Funcionario()
                .setIdFuncionario(rs.getInt("id_funcionario"))
                .setUsuario(new Usuario().setIdUsuario(rs.getInt("usuario_id")))
                .setCodigoFuncionario(rs.getString("codigo_funcionario"))
                .setCargo(Cargo.valueOf(rs.getString("cargo")))
                .setSupervisor(rs.getInt("supervisor"));
    }

    public Funcionario insert(Funcionario funcionario) {
        String sql = """
                INSERT INTO funcionario (usuario_id, codigo_funcionario, cargo, id_supervisor)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection con = Conexao.conexao()) {
            PreparedStatement ps = con.prepareStatement(sql);
            preencherPreparedStatement(ps, funcionario);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                return funcionario;
            } else {
                throw new RuntimeException("Falha ao inserir funcionário, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário.", e);
        }
    }

    public Funcionario update(Funcionario funcionario) {
        String sql = """
                UPDATE funcionario SET usuario_id = ?, codigo_funcionario = ?, cargo = ?, id_supervisor = ?
                WHERE id_funcionario = ?
                """;
        try (Connection con = Conexao.conexao()) {
            PreparedStatement ps = con.prepareStatement(sql);
            preencherPreparedStatement(ps, funcionario);
            ps.setInt(5, funcionario.getIdFuncionario());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                return funcionario;
            } else {
                throw new RuntimeException("Falha ao atualizar funcionário, nenhuma linha afetada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário.", e);
        }
    }
}
