package CALISTO.model.dao;

import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.persistence.util.Cargo;

import java.sql.*;

public class LoginFuncionarioDao {
    /** Método para buscar um funcionário pelo código
     *
     * @param codigo
     * @return um objeto Funcionario se encontrado, null caso contrário
     */
    public Funcionario findByCodigo(String codigo) {
        String sql = "SELECT u.*, f.codigo_funcionario, f.cargo, f.id_supervisor FROM usuario u " +
                     "JOIN funcionario f ON u.id_usuario = f.usuario_id " +
                     "WHERE f.codigo_funcionario = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdUsuario(rs.getInt("id_usuario"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setSenhaHash(rs.getString("senha_hash"));
                funcionario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                funcionario.setOtpAtivo(rs.getString("otp_ativo"));
                funcionario.setCodigoFuncionario(rs.getString("codigo_funcionario"));
                funcionario.setCargo(Cargo.valueOf(rs.getString("cargo")));
                funcionario.setSupervisor(rs.getInt("id_supervisor"));

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