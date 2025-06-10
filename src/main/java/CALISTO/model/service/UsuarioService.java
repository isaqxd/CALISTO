package CALISTO.model.service;

import CALISTO.model.persistence.Usuario.Usuario;
import CALISTO.model.persistence.util.TipoUsuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

/**
 * Classe base para serviços relacionados a usuários.
 * Contém métodos comuns de validação e processamento para todos os tipos de usuário.
 */
public abstract class UsuarioService {
    // Constantes comuns
    protected static final int IDADE_MINIMA = 18;
    protected static final int IDADE_MAXIMA = 100;
    protected static final int TAMANHO_MINIMO_SENHA = 6;
    protected static final int TAMANHO_MINIMO_NOME = 3;
    protected static final int TAMANHO_MAXIMO_NOME = 100;
    protected static final int TAMANHO_TELEFONE = 11;
    protected static final String SALT = "X@mpl3S@lt2025!";

    /**
     * Valida o nome do usuário.
     *
     * @param nome  nome a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    protected void validarNome(String nome, List<String> erros) {
        if (nome == null || nome.trim().isEmpty()) {
            erros.add("Nome não pode ser vazio");
            return;
        }

        if (nome.length() < TAMANHO_MINIMO_NOME || nome.length() > TAMANHO_MAXIMO_NOME) {
            erros.add("Nome deve ter entre " + TAMANHO_MINIMO_NOME + " e " + TAMANHO_MAXIMO_NOME + " caracteres");
        }
    }

    /**
     * Valida o CPF do usuário.
     *
     * @param cpf   CPF a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    protected void validarCPF(String cpf, List<String> erros) {
        if (cpf == null || cpf.trim().isEmpty()) {
            erros.add("CPF não pode ser vazio");
            return;
        }

        if (!isValidCPF(cpf)) {
            erros.add("CPF inválido");
        }
    }

    /**
     * Valida a data de nascimento do usuário.
     *
     * @param dataNascimento data de nascimento a ser validada
     * @param erros          lista onde serão adicionados os erros encontrados
     */
    protected void validarDataNascimento(LocalDate dataNascimento, List<String> erros) {
        if (dataNascimento == null) {
            erros.add("Data de nascimento não pode ser vazia");
            return;
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            erros.add("Data de nascimento não pode ser futura");
            return;
        }

        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(dataNascimento, hoje);
        int idade = periodo.getYears();

        if (idade < IDADE_MINIMA) {
            erros.add("Usuário deve ter pelo menos " + IDADE_MINIMA + " anos");
        }

        if (idade > IDADE_MAXIMA) {
            erros.add("Idade máxima permitida é " + IDADE_MAXIMA + " anos");
        }
    }

    /**
     * Valida o telefone do usuário.
     *
     * @param telefone telefone a ser validado
     * @param erros    lista onde serão adicionados os erros encontrados
     */
    protected void validarTelefone(String telefone, List<String> erros) {
        if (telefone == null || telefone.trim().isEmpty()) {
            erros.add("Telefone não pode ser vazio");
            return;
        }

        // Remover caracteres não numéricos
        String telefoneNumerico = telefone.replaceAll("\\D", "");

        if (telefoneNumerico.length() != TAMANHO_TELEFONE) {
            erros.add("Telefone deve ter " + TAMANHO_TELEFONE + " dígitos");
            return;
        }

        String formatoTelefone = "^[1-9]{2}9[0-9]{8}$";
        if (!telefoneNumerico.matches(formatoTelefone)) {
            erros.add("Telefone inválido");
        }
    }

    /**
     * Valida o tipo de usuário.
     *
     * @param tipoUsuario tipo de usuário a ser validado
     * @param erros       lista onde serão adicionados os erros encontrados
     */
    protected void validarTipoUsuario(TipoUsuario tipoUsuario, List<String> erros) {
        if (tipoUsuario == null) {
            erros.add("Tipo de usuário não pode ser vazio");
        }
    }

    /**
     * Valida a senha do usuário.
     *
     * @param senha senha a ser validada
     * @param erros lista onde serão adicionados os erros encontrados
     */
    protected void validarSenha(String senha, List<String> erros) {
        if (senha == null || senha.trim().isEmpty()) {
            erros.add("Senha não pode ser vazia");
            return;
        }

        if (senha.length() < TAMANHO_MINIMO_SENHA) {
            erros.add("Senha deve ter pelo menos " + TAMANHO_MINIMO_SENHA + " caracteres");
        }
    }

    /**
     * Verifica se um CPF é válido.
     *
     * @param cpf CPF a ser validado
     * @return true se o CPF for válido, false caso contrário
     */
    protected boolean isValidCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        boolean todosDigitosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if (todosDigitosIguais) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int resto = soma % 11;
        int dv1 = (resto < 2) ? 0 : 11 - resto;

        // Verifica o primeiro dígito verificador
        if (dv1 != (cpf.charAt(9) - '0')) {
            return false;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        resto = soma % 11;
        int dv2 = (resto < 2) ? 0 : 11 - resto;

        // Verifica o segundo dígito verificador
        return dv2 == (cpf.charAt(10) - '0');
    }

    /**
     * Gera um OTP (One-Time Password) para o usuário.
     *
     * @param usuario usuário para o qual o OTP será gerado
     */
    protected void gerarOTP(Usuario usuario) {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        usuario.setOtpAtivo(String.valueOf(otp));

        // Definir expiração para 5 minutos a partir de agora
        usuario.setOtpExpiracao(LocalDateTime.now().plusMinutes(5));
    }

    /**
     * Gera um hash MD5 para a senha do usuário.
     *
     * @param usuario usuário cuja senha será hasheada
     */
    public void gerarMD5(Usuario usuario) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String senha = usuario.getSenhaHash();
            String senhaComSalt = senha + SALT;

            byte[] hash = md.digest(senhaComSalt.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            usuario.setSenhaHash(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash MD5: " + e.getMessage(), e);
        }
    }
}