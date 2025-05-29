package calisto.dao;

import calisto.model.usuario.Usuario;
import calisto.util.Conexao;
import calisto.util.RowMapper;
import calisto.util.UsuarioRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final RowMapper<Usuario> usuarioRowMapper = new UsuarioRowMapper();

    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection con = Conexao.conexao()) {
            PreparedStatement state = con.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            int count = 0;
            while (rs.next()) {
                usuarios.add(usuarioRowMapper.mapRow(rs));
                count++;
            }
            System.out.println("Encontrou " + count + " usuários");

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario findById(int idUsuario) {
        Usuario u = null;
        try (Connection con = Conexao.conexao()) {
            String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
            PreparedStatement state = con.prepareStatement(sql);
            state.setInt(1, idUsuario);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                u = usuarioRowMapper.mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public void criarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, cpf, data_nascimento, telefone, senha_hash, tipo_usuario, otp_ativo, otp_expiracao) VALUES (?,?,?,?,?,?,?, ?)";

        try (Connection con = Conexao.conexao()) {
            PreparedStatement state = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            state.setString(1, usuario.getNome());
            state.setString(2, usuario.getCpf());
            state.setDate(3, Date.valueOf(usuario.getDataNascimento()));
            state.setString(4, usuario.getTelefone());
            state.setString(5, usuario.getSenhaHash());
            state.setString(6, usuario.getTipoUsuario().name());
            state.setString(7, usuario.getOtpAtivo());
            state.setTimestamp(8, Timestamp.valueOf(usuario.getOtpExpiracao()));

            int linhasAfetadas = state.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = state.getGeneratedKeys();
                if (rs.next()) {
                    int pegarId = rs.getInt(1);
                    System.out.println("Usuario criado com ID: " + pegarId);
                }
            } else {
                System.out.println("Erro ao inserir usuário.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar usuário.", e);
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, senha_hash = ?, tipo_usuario = ?, otp_ativo = ?, otp_expiracao = ? WHERE id_usuario = ?";
    }
}
