package CALISTO.model.service;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela validação e processamento de Cliente no sistema.
 * Esta classe gerencia as operações relacionadas aos clientes, incluindo:
 * - Validação de dados
 * - Geração de OTP (One-Time Password)
 * - Persistência no banco de dados
 *
 * @since 1.0
 */
public class ClienteService extends UsuarioService {
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
            gerarMD5(cliente);
            return clienteDao.save(cliente);
        } catch (RuntimeException e) {
            throw new RuntimeException("Falha ao processar cliente: " + e.getMessage(), e);
        }
    }

    /**
     * Valida o tipo de usuário, garantindo que seja do tipo CLIENTE.
     * Sobrescreve o método da classe base para adicionar validação específica.
     *
     * @param tipoUsuario tipo de usuário a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    @Override
    protected void validarTipoUsuario(TipoUsuario tipoUsuario, List<String> erros) {
        // Chama a validação básica da classe pai
        super.validarTipoUsuario(tipoUsuario, erros);
        
        // Adiciona validação específica para cliente
        if (tipoUsuario != null && tipoUsuario != TipoUsuario.CLIENTE) {
            erros.add("Tipo de usuário deve ser CLIENTE");
        }
    }
}