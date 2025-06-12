package CALISTO.model.dao;

import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoUsuario;

import java.sql.*;

public class LoginClienteDao {
    /** Método para buscar um cliente pelo CPF
     *
     * @param cpf
     * @return um objeto Cliente se encontrado, null caso contrário
     */
    public Cliente findByCpf(String cpf) {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdUsuario(rs.getInt("id_usuario"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSenhaHash(rs.getString("senha_hash"));
                cliente.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                cliente.setOtpAtivo(rs.getString("otp_ativo"));

                Timestamp ts = rs.getTimestamp("otp_expiracao");
                if (ts != null) cliente.setOtpExpiracao(ts.toLocalDateTime());
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Método para atualizar o OTP do cliente
     *
     * @param cliente
     * @return True se a atualização for bem-sucedida, False caso contrário
     */
    public boolean updateOtp(Cliente cliente) {
        String sql = "UPDATE usuario SET otp_ativo = ?, otp_expiracao = ? WHERE id_usuario = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cliente.getOtpAtivo());
            stmt.setTimestamp(2, Timestamp.valueOf(cliente.getOtpExpiracao()));
            stmt.setInt(3, cliente.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}