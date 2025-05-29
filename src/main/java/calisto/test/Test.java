package calisto.test;


import calisto.dao.UserDao;
import calisto.model.usuario.TipoUsuario;
import calisto.model.usuario.Usuario;
import calisto.service.UsuarioService;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        UserDao dao = new UserDao();

        UsuarioService us = new UsuarioService();

        // ADICIONAR USUARIO
        Usuario u = new Usuario();
        u.setNome("Lucca Lucca Aragão");
        u.setCpf("07761207194");
        u.setDataNascimento(LocalDate.of(1958, 05, 12));
        u.setTelefone("83985182065");
        u.setTipoUsuario(TipoUsuario.FUNCIONARIO);
        u.setSenhaHash("<PASSWORD>");
        us.verificarUsuario(u);
        System.out.println(u.getOtpAtivo());
        System.out.println(u.getOtpExpiracao());

        //LIST ALL APLICADO
//        System.out.println("Iniciando busca de usuários...");
//        List<Usuario> usuarios = dao.findAll();
//
//        if (usuarios.isEmpty()) {
//            System.out.println("Nenhum usuário encontrado no banco de dados!");
//
//        } else {
//            System.out.println("Total de usuários encontrados: " + usuarios.size());
//            usuarios.forEach(System.out::println);
//        }
    }
}
