package CALISTO.model.mapper;

import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Usuario;
import CALISTO.model.persistence.util.TipoUsuario;

import java.sql.*;


public class UsuarioMapper {

    public static void fillUsuarioFromResultSet(ResultSet rs, Usuario usuario) throws SQLException {
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNome(rs.getString("nome"));
        usuario.setCpf(rs.getString("cpf"));

        Date data = rs.getDate("data_nascimento");
        if (data != null) usuario.setDataNascimento(data.toLocalDate());

        usuario.setTelefone(rs.getString("telefone"));
        usuario.setSenhaHash(rs.getString("senha_hash"));
        usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
        usuario.setOtpAtivo(rs.getString("otp_ativo"));

        Timestamp ts = rs.getTimestamp("otp_expiracao");
        if (ts != null) usuario.setOtpExpiracao(ts.toLocalDateTime());

        usuario.setEndereco(fillEnderecoFromResultSet(rs));
    }

    public static Endereco fillEnderecoFromResultSet(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(rs.getInt("id_endereco"));
        endereco.setCep(rs.getString("cep"));
        endereco.setLocal(rs.getString("local"));
        endereco.setNumeroCasa(rs.getInt("numero_casa"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setComplemento(rs.getString("complemento"));
        return endereco;
    }

    public static void fillPreparedStatementUsuario(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getCpf());

        if (usuario.getDataNascimento() != null) {
            stmt.setDate(3, Date.valueOf(usuario.getDataNascimento()));
        } else {
            stmt.setNull(3, Types.DATE);
        }

        stmt.setString(4, usuario.getTelefone());
        stmt.setString(5, usuario.getSenhaHash());
        stmt.setString(6, usuario.getTipoUsuario().name());
        stmt.setInt(7, usuario.getEndereco().getIdEndereco());
        stmt.setString(8, usuario.getOtpAtivo());

        if (usuario.getOtpExpiracao() != null) {
            stmt.setTimestamp(9, Timestamp.valueOf(usuario.getOtpExpiracao()));
        } else {
            stmt.setNull(9, Types.TIMESTAMP);
        }
    }

    public static void fillPreparedStatementEndereco(PreparedStatement stmt, Endereco endereco) throws SQLException {
        stmt.setString(1, endereco.getCep());
        stmt.setString(2, endereco.getLocal());
        stmt.setInt(3, endereco.getNumeroCasa());
        stmt.setString(4, endereco.getBairro());
        stmt.setString(5, endereco.getCidade());
        stmt.setString(6, endereco.getEstado());
        stmt.setString(7, endereco.getComplemento());
    }

    public static int inserirEndereco(Connection conn, Usuario usuario, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            UsuarioMapper.fillPreparedStatementEndereco(stmt, usuario.getEndereco());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Falha ao obter o ID do endereço.");
            }
        }
    }

    public static int inserirUsuario(Connection conn, Usuario usuario, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            UsuarioMapper.fillPreparedStatementUsuario(stmt, usuario);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Falha ao obter o ID do usuário.");
            }
        }
    }

    public static void rollBack(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}