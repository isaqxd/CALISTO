package calisto.service;

import calisto.dao.UserDao;
import calisto.model.usuario.TipoUsuario;
import calisto.model.usuario.Usuario;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela validação e processamento de usuários no sistema.
 * Esta classe gerencia as operações relacionadas aos usuários, incluindo:
 * - Validação de dados
 * - Geração de OTP (One-Time Password)
 * - Persistência no banco de dados
 *
 * @author isaqxd
 * @since 1.0
 */
public class UsuarioService {
    private final UserDao userDao;

    /**
     * Construtor que inicializa o serviço com o DAO necessário.
     *
     * @param userDao DAO responsável pela persistência de usuários
     */
    public UsuarioService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Realiza a validação completa dos dados do usuário e processa sua inserção.
     * Verifica todos os campos obrigatórios e suas respectivas regras de negócio.
     *
     * @param usuario o usuário a ser validado e processado
     * @throws IllegalArgumentException quando encontrados erros de validação
     */
    public Usuario verificarUsuario(@NotNull Usuario usuario) {
        List<String> erros = new ArrayList<>();

        validarNome(usuario.getNome(), erros);
        validarCPF(usuario.getCpf(), erros);
        validarDataNascimento(usuario.getDataNascimento(), erros);
        validarTelefone(usuario.getTelefone(), erros);
        validarTipoUsuario(usuario.getTipoUsuario(), erros);
        validarSenha(usuario.getSenhaHash(), erros);

        if (!erros.isEmpty()) {
            throw new RuntimeException("Erro ao cadastrar usuário. " + String.join(", ", erros));
        }

        return processarUsuario(usuario);
    }

    /**
     * Realiza o processamento de um usuário já validado.
     * Gera o OTP e persiste o usuário no banco de dados.
     *
     * @param usuario usuário válido a ser processado
     */
    private Usuario processarUsuario(Usuario usuario) {
        try {
            gerarOTP(usuario);
            return userDao.insert(usuario);
        } catch (RuntimeException e) {
            throw new RuntimeException("Falha ao processar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Valida o nome do usuário.
     *
     * @param nome  nome a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    private void validarNome(String nome, List<String> erros) {
        if (nome == null || nome.trim().isEmpty()) {
            erros.add("Nome é obrigatório.");
            return;
        }

        if (nome.trim().length() < 3) {
            erros.add("Nome deve ter no mínimo 3 caracteres.");
        }

        if (!nome.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            erros.add("Nome deve conter apenas letras e espaços.");
        }
    }

    /**
     * Valida o CPF do usuário.
     *
     * @param cpf   CPF a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    private void validarCPF(String cpf, List<String> erros) {
        if (cpf == null || cpf.trim().isEmpty()) {
            erros.add("CPF é obrigatório.");
            return;
        }
        if (!isValidCPF(cpf)) {
            erros.add("CPF inválido.");
        }
    }

    /**
     * Valida a data de nascimento do usuário.
     *
     * Regras de validação:
     * - A data não pode ser nula (obrigatória).
     * - A data não pode ser futura em relação à data atual.
     * - O usuário deve ter pelo menos 18 anos (data mínima permitida é 18 anos atrás da data atual).
     *
     * Caso alguma dessas regras não seja atendida, mensagens de erro descritivas
     * são adicionadas à lista informada.
     *
     * @param dataNascimento data de nascimento a ser validada
     * @param erros          lista onde serão adicionados os erros encontrados durante a validação
     */
    private void validarDataNascimento(LocalDate dataNascimento, List<String> erros) {
        if (dataNascimento == null) {
            erros.add("Data de nascimento é obrigatória.");
            return;
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            erros.add("Data de nascimento não pode ser futura.");
        }

        if (dataNascimento.plusYears(18).isAfter(LocalDate.now())) {
            erros.add("Usuário deve ter no mínimo 18 anos.");
        }
    }

    /**
     * Valida o telefone do usuário no formato brasileiro de números celulares.
     *
     * Regras de validação:
     * - O telefone é obrigatório (não pode ser nulo ou vazio).
     * - Deve conter exatamente 11 dígitos numéricos após remover qualquer caractere especial.
     * - Formato válido: DDD (dois dígitos entre 1 e 9) + '9' (prefixo de celulares) + 8 dígitos numéricos.
     * - Exemplos válidos: "21987654321", "11912345678".
     * - Não aceita telefones fixos, apenas números de celular no padrão atual brasileiro.
     *
     * Caso alguma dessas regras não seja atendida, mensagens de erro são adicionadas
     * na lista de erros informada.
     *
     * @param telefone telefone a ser validado, pode conter ou não caracteres especiais
     * @param erros    lista onde serão adicionadas mensagens descritivas caso ocorram falhas na validação
     */
    private void validarTelefone(String telefone, List<String> erros) {
        if (telefone == null || telefone.trim().isEmpty()) {
            erros.add("Telefone é obrigatório.");
            return;
        }

        telefone = telefone.replaceAll("\\D", "");

        if (telefone.length() != 11) {
            erros.add("Telefone deve ter 11 dígitos.");
            return;
        }

        String formatoTelefone = "^[1-9]{2}9[0-9]{8}$";

        if (!telefone.matches(formatoTelefone)) {
            erros.add("Telefone inválido.");
        }
    }

    /**
     * Valida o tipo de usuário do usuário.
     *
     * @param tipoUsuario tipo de usuário a ser validado
     * @param erros       lista onde serão adicionados os erros encontrados
     */
    private void validarTipoUsuario(TipoUsuario tipoUsuario, List<String> erros) {
        if (tipoUsuario == null) {
            erros.add("Tipo de usuário é obrigatório.");
        }
    }

    /**
     * Valida a senha do usuário.
     *
     * @param senha senha a ser validada
     * @param erros lista onde serão adicionados os erros encontrados
     */
    private void validarSenha(String senha, List<String> erros) {
        if (senha == null || senha.trim().isEmpty()) {
            erros.add("Senha é obrigatória.");
        }
    }

    /**
     * Verifica se um CPF é válido.
     *
     * @param cpf CPF a ser validado
     * @return true se o CPF for válido, false caso contrário
     */
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

    /**
     * Gera um OTP (One-Time Password) para o usuário.
     *
     * @param usuario usuário para o qual o OTP será gerado
     */
    public void gerarOTP(@NotNull Usuario usuario) {
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(1_000_000);

        String otp = String.format("%06d", number);

        usuario.setOtpAtivo(otp);
        usuario.setOtpExpiracao(LocalDateTime.now().plusMinutes(5));
    }
}
