package CALISTO.service;

import CALISTO.dao.UsuadioDao;
import CALISTO.model.Usuario;

import java.sql.SQLException;

public class UsuarioService {
    private UsuadioDao dao;

    public UsuarioService() {
        this.dao = new UsuadioDao();
    }

    public void insert(Usuario usuario) throws Exception {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()){
            throw new Exception("Nome não pode ser vazio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()){
            throw new Exception("Email não pode ser vazio");
        }

        if (usuario.getSenha() == null || usuario.getSenha().length() < 4){
            throw new Exception("Senha deve ter no minimo 4 caracteres");
        }

        dao.insert(usuario);
    }

    public boolean autenticar(String email, String senha) throws SQLException {
        Usuario usuario = dao.selectEmailESenha(email, senha);
        return usuario != null;
    }
}
