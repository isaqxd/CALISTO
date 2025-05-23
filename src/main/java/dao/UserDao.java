package dao;

import model.usuario.Usuario;
import service.UsuarioService.UsuarioService;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public List<Usuario> buscarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection con = Conexao.conexao()) {
            PreparedStatement state = con.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                usuarios.add(UsuarioService.mapUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario findByIdUsuario(int idUsuario) {
        Usuario u = null;
        try (Connection con = Conexao.conexao()) {
            String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
            PreparedStatement state = con.prepareStatement(sql);
            state.setInt(1, idUsuario);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                u = UsuarioService.mapUsuario(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
}
