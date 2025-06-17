package CALISTO.model.dao;

import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoUsuario;

import java.sql.*;

public class LoginFuncionarioDao {
    public Funcionario findByCpf(String cpf) {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdUsuario(rs.getInt("id_usuario"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setSenhaHash(rs.getString("senha_hash"));
                funcionario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                funcionario.setOtpAtivo(rs.getString("otp_ativo"));

                Timestamp ts = rs.getTimestamp("otp_expiracao");
                if (ts != null) funcionario.setOtpExpiracao(ts.toLocalDateTime());
                return funcionario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Método para atualizar o OTP do funcionário
     *
     * @param funcionario
     * @return True se a atualização for bem-sucedida, False caso contrário
     */
    public boolean updateOtp(Funcionario funcionario) {
        String sql = "UPDATE usuario SET otp_ativo = ?, otp_expiracao = ? WHERE id_usuario = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getOtpAtivo());
            stmt.setTimestamp(2, Timestamp.valueOf(funcionario.getOtpExpiracao()));
            stmt.setInt(3, funcionario.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}