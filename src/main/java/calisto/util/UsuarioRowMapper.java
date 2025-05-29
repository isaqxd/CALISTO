package calisto.util;

import calisto.model.usuario.TipoUsuario;
import calisto.model.usuario.Usuario;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));
        Date nascimento = rs.getDate("data_nascimento");
        if (nascimento != null) {
            u.setDataNascimento(nascimento.toLocalDate());
        }
        u.setTelefone(rs.getString("telefone"));
        String tipoUser = rs.getString("tipo_usuario");
        if (tipoUser != null) {
            try {
                u.setTipoUsuario(TipoUsuario.valueOf(tipoUser));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de usuario invalido: " + tipoUser);
            }
        }
        u.setSenhaHash(rs.getString("senha_hash"));
        u.setOtpAtivo(rs.getString("otp_ativo"));
        Timestamp st = rs.getTimestamp("otp_expiracao");
        if (st != null) {
            u.setOtpExpiracao(st.toLocalDateTime());
        }
        return u;
    }
}
