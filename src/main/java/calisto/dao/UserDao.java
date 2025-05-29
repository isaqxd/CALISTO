package calisto.dao;


import calisto.model.usuario.TipoUsuario;
import calisto.model.usuario.Usuario;
import calisto.util.Conexao;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    /**
     * Preenche o prepared statement com os dados do usuário.
     * @param ps
     * @param usuario
     * @throws SQLException
     */
    private void preencherPreparedStatement(@NotNull PreparedStatement ps, @NotNull Usuario usuario) throws SQLException {
        ps.setString(1, usuario.getNome());
        ps.setString(2, usuario.getCpf());
        ps.setDate(3, Date.valueOf(usuario.getDataNascimento()));
        ps.setString(4, usuario.getTelefone());
        ps.setString(5, usuario.getSenhaHash());
        ps.setString(6, usuario.getTipoUsuario().name());
        ps.setString(7, usuario.getOtpAtivo());
        ps.setTimestamp(8, Timestamp.valueOf(usuario.getOtpExpiracao()));
    }

    /**
     * Monta um objeto do tipo Usuario a partir de um ResultSet.
     * @param rs
     * @return
     * @throws SQLException
     */
    @NotNull
    private Usuario montarUsuario(@NotNull ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));
        u.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        u.setTelefone(rs.getString("telefone"));
        u.setSenhaHash(rs.getString("senha_hash"));
        u.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
        u.setOtpAtivo(rs.getString("otp_ativo"));
        u.setOtpExpiracao(rs.getTimestamp("otp_expiracao").toLocalDateTime());
        return u;
    }


    /**
     * Insere um usuário no banco de dados.
     * @param usuario
     * @return
     */
    public Usuario insert(Usuario usuario) {
        String sql = """
            INSERT INTO usuario (nome, cpf, data_nascimento, telefone, senha_hash, tipo_usuario, otp_ativo, otp_expiracao)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = Conexao.conexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherPreparedStatement(ps, usuario);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Falha ao inserir usuário, nenhuma linha afetada.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                    return usuario;
                } else {
                    throw new RuntimeException("Falha ao obter ID do usuário inserido.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário.", e);
        }
    }

    /**
     * Atualiza os dados de um usuário no banco de dados.
     * @param usuario
     */
    public void update(Usuario usuario) {
        String sql = """
            UPDATE usuario SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, senha_hash = ?, tipo_usuario = ?, otp_ativo = ?, otp_expiracao = ?
            WHERE id_usuario = ?
        """;

        try (Connection con = Conexao.conexao();
             PreparedStatement st = con.prepareStatement(sql)) {

            preencherPreparedStatement(st, usuario);
            st.setInt(9, usuario.getIdUsuario());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Usuário não encontrado para atualização.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário.", e);
        }
    }

    /**
     * Busca todos os usuários no banco de dados.
     * @return
     */
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection con = Conexao.conexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaUsuarios.add(montarUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os usuários.", e);
        }

        return listaUsuarios;
    }

    /**
     * Busca um usuário pelo ID.
     * @param id
     * @return
     */
    public Usuario findById(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection con = Conexao.conexao();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return montarUsuario(rs);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID.", e);
        }
    }

    /**
     * Deleta um usuário pelo ID.
     * @param id
     * @return
     */
    public Usuario deleteById(int id) {
        Usuario usuario = findById(id);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado para deletar.");
        }

        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection con = Conexao.conexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário.", e);
        }

        return usuario;
    }
}
