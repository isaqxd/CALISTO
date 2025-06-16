package CALISTO.model.service.UsuarioService;

import CALISTO.model.dao.FuncionarioDao;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Cargo;
import CALISTO.model.persistence.util.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela validação e processamento de Funcionários no sistema.
 * Estende a classe base UsuarioService para reutilizar funcionalidades comuns.
 */
public class FuncionarioService extends UsuarioService {
    private final FuncionarioDao funcionarioDao;

    /**
     * Construtor que inicializa o serviço com o DAO necessário.
     *
     * @param funcionarioDao DAO responsável pela persistência de funcionários
     */
    public FuncionarioService(FuncionarioDao funcionarioDao) {
        this.funcionarioDao = funcionarioDao;
    }

    /**
     * Verifica se o funcionário é válido e o processa antes de salvar
     * @param funcionario O funcionário a ser verificado e processado
     * @return O funcionário salvo ou null se houver erros de validação
     */
    public Funcionario verificarFuncionario(Funcionario funcionario) {
        List<String> erros = new ArrayList<>();

        // Validações
        validarNome(funcionario.getNome(), erros);
        validarCPF(funcionario.getCpf(), erros);
        validarDataNascimento(funcionario.getDataNascimento(), erros);
        validarTelefone(funcionario.getTelefone(), erros);
        validarTipoUsuario(funcionario.getTipoUsuario(), erros);
        validarSenha(funcionario.getSenhaHash(), erros);
        validarCargo(funcionario.getCargo(), erros);

        if (!erros.isEmpty()) {
            System.out.println("Erros de validação: " + String.join(", ", erros));
            return null;
        }

        return processarFuncionario(funcionario);
    }

    /**
     * Processa o funcionário antes de salvar (gera OTP, hash de senha, etc.)
     * @param funcionario O funcionário a ser processado
     * @return O funcionário salvo
     */
    private Funcionario processarFuncionario(Funcionario funcionario) {
        // Gerar OTP e hash de senha
        gerarMD5(funcionario);
        gerarCodigo(funcionario);

        // Salvar no banco
        return funcionarioDao.save(funcionario);
    }

    /**
     * Valida o tipo de usuário, garantindo que seja do tipo FUNCIONARIO.
     * Sobrescreve o método da classe base para adicionar validação específica.
     *
     * @param tipoUsuario tipo de usuário a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    @Override
    protected void validarTipoUsuario(TipoUsuario tipoUsuario, List<String> erros) {
        // Chama a validação básica da classe pai
        super.validarTipoUsuario(tipoUsuario, erros);
        
        // Adiciona validação específica para funcionário
        if (tipoUsuario != null && tipoUsuario != TipoUsuario.FUNCIONARIO) {
            erros.add("Tipo de usuário deve ser FUNCIONARIO");
        }
    }

    /**
     * Valida o cargo do funcionário.
     *
     * @param cargo cargo a ser validado
     * @param erros lista onde serão adicionados os erros encontrados
     */
    private void validarCargo(Cargo cargo, List<String> erros) {
        if (cargo == null) {
            erros.add("Cargo não pode ser vazio");
        }
    }

    /**
     * Cria um codigo de funcionário
     */
    private void gerarCodigo(Funcionario funcionario) {
        String cargo = funcionario.getCargo().name().substring(0, 3).toUpperCase();
        String codigoUnico = String.valueOf(System.currentTimeMillis());
        String codigoFuncionario = cargo + "-" + codigoUnico;
        // Garante que não ultrapasse 20 caracteres
        if (codigoFuncionario.length() > 20) {
            codigoFuncionario = codigoFuncionario.substring(0, 20);
        }
        funcionario.setCodigoFuncionario(codigoFuncionario);
    }
    
}