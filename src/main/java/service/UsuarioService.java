package service;

import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import java.sql.*;

public class UsuarioService {
    public static Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));
        Date nascimento = rs.getDate("data_nascimento");
        if (nascimento != null) {
            u.setDataNascimento(nascimento.toLocalDate());
        }
        u.setTelefone(rs.getString("telefone"));
        String tipoUser = rs.getString("tipo_user");
        if (tipoUser != null) {
            try {
                u.setTipoUsuario(TipoUsuario.valueOf(tipoUser));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de usuario invalido: " + tipoUser);
            }
        }
        u.setSenhaHash(rs.getString("senha"));
        u.setOtpAtivo(rs.getString("otp"));
        Timestamp st = rs.getTimestamp("data_criacao");
        if (st != null) {
            u.setOtpExpiracao(st.toLocalDateTime());
        }
        return u;
    }
}
