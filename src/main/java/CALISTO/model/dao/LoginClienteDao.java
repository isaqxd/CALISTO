package CALISTO.model.dao;

import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoUsuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginClienteDao {

//    public static final String USUARIO_IS_CLIENTE = TipoUsuario.CLIENTE.toString();
//    public boolean autenticarCliente(String cpf, String senha) {
//
//        String senhaHash = gerarHashMD5(senha);
//
//        String sql = "SELECT * FROM usuario WHERE cpf = ? AND senha_hash = ?";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, cpf);
//            stmt.setString(2, senhaHash);
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (USUARIO_IS_CLIENTE.equals(rs.getString("tipo_usuario"))) {
//                return rs.next();
//                }
//            } else {
//                System.out.println("Usuário não é um cliente.");
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}