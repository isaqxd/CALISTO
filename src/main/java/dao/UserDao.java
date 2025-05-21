package dao;

import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection con = Conexao.conexao()) {
            String sql = "SELECT * FROM usuario";
            PreparedStatement state = con.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));

                Date nasc = rs.getDate("data_nascimento");
                if (nasc != null) {
                    u.setDataNascimento(nasc.toLocalDate());
                }

                u.setTelefone(rs.getString("telefone"));

                String tipoUserString = rs.getString("tipo_usuario");
                if (tipoUserString != null) {
                    try {
                        u.setTipoUsuario(TipoUsuario.valueOf(tipoUserString));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de usuário invalido " + tipoUserString);
                    }
                }

                u.setSenhaHash(rs.getString("senha_hash"));
                u.setOtpAtivo(rs.getString("otp_ativo"));

                Timestamp ts = rs.getTimestamp("otp_expiracao");
                if (ts != null) {
                    u.setOtpExpiracao(ts.toLocalDateTime());
                }
                usuarios.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
