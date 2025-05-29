package calisto.service;

import calisto.dao.UserDao;
import calisto.model.usuario.Usuario;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class UsuarioService {
    private UserDao userDao;

    public UsuarioService() {
        this.userDao = new UserDao();
    }

    public void verificarUsuario(Usuario usuario) throws IllegalArgumentException {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório.");
        }

        if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do usuário é obrigatório.");
        }

        if (!isValidCPF(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        if (usuario.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento do usuário é obrigatório.");
        }

        if (usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do usuário é obrigatório.");
        }

        if (usuario.getTipoUsuario() == null) {
            throw new IllegalArgumentException("Tipo de usuário é obrigatório.");
        }

        if (usuario.getSenhaHash() == null || usuario.getSenhaHash().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha do usuário é obrigatório.");
        }

        gerarOTP(usuario);

        userDao.criarUsuario(usuario);
    }

    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            return false;
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = cpf.charAt(i) - '0';
        }

        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += digitos[i] * (10 - i);
        }

        int resto1 = soma1 % 11;
        int dv1 = (resto1 < 2) ? 0 : 11 - resto1;

        if (digitos[9] != dv1) {
            return false;
        }

        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += digitos[i] * (11 - i);
        }
        int resto2 = soma2 % 11;
        int dv2 = (resto2 < 2) ? 0 : 11 - resto2;

        return digitos[10] == dv2;
    }

    public void gerarOTP(Usuario usuario) {
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(1_000_000);

        String otp = String.format("%06d", number);

        usuario.setOtpAtivo(otp);
        usuario.setOtpExpiracao(LocalDateTime.now().plusMinutes(5));
    }
}
