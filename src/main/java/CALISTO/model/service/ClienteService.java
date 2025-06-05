package CALISTO.model.service;


import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.TipoUsuario;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private static final LocalDate IDADE_MINIMA = LocalDate.now().minusYears(18);
    /**
     * Serviço responsável pela validação e processamento de Cliente no sistema.
     * Esta classe gerencia as operações relacionadas aos clientes, incluindo:
     * - Validação de dados
     * - Geração de OTP (One-Time Password)
     * - Persistência no banco de dados
     *
     * @since 1.0
     */

    private final ClienteDao clienteDao;

    /**
     * Construtor que inicializa o serviço com o DAO necessário.
     *
     * @param clienteDao DAO responsável pela persistência de usuários
     */
    public ClienteService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    /**
     * Realiza a validação completa dos dados do usuário e processa sua inserção.
     * Verifica todos os campos obrigatórios e suas respectivas regras de negócio.
     *
     * @param cliente o usuário a ser validado e processado
     * @throws IllegalArgumentException quando encontrados erros de validação
     */
    public Cliente verificarCliente(Cliente cliente) {
        List<String> erros = new ArrayList<>();

        // VERIFICAÇÃO DE CAMPOS USUARIO
        validarNome(cliente.getNome(), erros);
        validarCPF(cliente.getCpf(), erros);
        validarDataNascimento(cliente.getDataNascimento(), erros);
        validarTelefone(cliente.getTelefone(), erros);
        validarTipoUsuario(cliente.getTipoUsuario(), erros);
        validarSenha(cliente.getSenhaHash(), erros);

        // VERIFICAÇÃO DE CAMPOS ENDEREÇO

        // VERIFICAÇÃO DE CAMPOS CLIENTE

        if (!erros.isEmpty()) {
            throw new RuntimeException("Erro ao cadastrar cliente. " + String.join(", ", erros));
        }

        return processarCliente(cliente);
    }

    /**
     * Realiza o processamento de um cliente já validado.
     * Gera o OTP e persiste o cliente no banco de dados.
     *
     * @param cliente cliente válido a ser processado
     */
    private Cliente processarCliente(Cliente cliente) {
        try {
            gerarOTP(cliente);
            return clienteDao.save(cliente);
        } catch (RuntimeException e) {
            throw new RuntimeException("Falha ao processar cliente: " + e.getMessage(), e);
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
     * <p>
     * Regras de validação:
     * - A data não pode ser nula (obrigatória).
     * - A data não pode ser futura em relação à data atual.
     * - O usuário deve ter pelo menos 18 anos (data mínima permitida é 18 anos atrás da data atual).
     * <p>
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

        if (dataNascimento.isAfter(IDADE_MINIMA)) {
            erros.add("Usuário deve ter no mínimo 18 anos.");
        }
    }

    /**
     * Valida o telefone do usuário no formato brasileiro de números celulares.
     * <p>
     * Regras de validação:
     * - O telefone é obrigatório (não pode ser nulo ou vazio).
     * - Deve conter exatamente 11 dígitos numéricos após remover qualquer caractere especial.
     * - Formato válido: DDD (dois dígitos entre 1 e 9) + '9' (prefixo de celulares) + 8 dígitos numéricos.
     * - Exemplos válidos: "21987654321", "11912345678".
     * - Não aceita telefones fixos, apenas números de celular no padrão atual brasileiro.
     * <p>
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
     * @param cliente usuário para o qual o OTP será gerado
     */
    public void gerarOTP(Cliente cliente) {
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(1_000_000);

        String otp = String.format("%06d", number);

        cliente.setOtpAtivo(otp);
        cliente.setOtpExpiracao(LocalDateTime.now().plusMinutes(5));
    }
}
