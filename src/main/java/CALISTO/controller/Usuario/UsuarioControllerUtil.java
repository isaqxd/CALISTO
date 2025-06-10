package CALISTO.controller.Usuario;

import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para operações comuns entre controladores de usuários
 * Evita duplicação de código entre ClienteController e FuncionarioController
 */
public class UsuarioControllerUtil {
    
    /**
     * Cria um objeto Endereco a partir dos parâmetros da requisição
     * @param request A requisição HTTP contendo os parâmetros do endereço
     * @return Um objeto Endereco preenchido com os dados da requisição
     */
    public static Endereco criarEndereco(HttpServletRequest request) {
        String cep = request.getParameter("cep");
        String local = request.getParameter("local");
        String numeroCasa = request.getParameter("numeroCasa");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String complemento = request.getParameter("complemento");
        
        return new Endereco(cep, local, numeroCasa, bairro, cidade, estado, complemento);
    }
    
    /**
     * Converte uma string de data para LocalDate
     * @param dataNascimentoStr A string contendo a data de nascimento
     * @return Um objeto LocalDate representando a data de nascimento, ou null se a string for inválida
     */
    public static LocalDate converterData(String dataNascimentoStr) {
        if (dataNascimentoStr == null || dataNascimentoStr.isEmpty()) {
            return null;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dataNascimentoStr, formatter);
    }
    
    /**
     * Preenche os dados básicos de um usuário a partir dos parâmetros da requisição
     * @param usuario O objeto Usuario a ser preenchido
     * @param request A requisição HTTP contendo os parâmetros do usuário
     * @param endereco O objeto Endereco a ser associado ao usuário
     */
    public static void preencherDadosUsuario(Usuario usuario, HttpServletRequest request, Endereco endereco) {
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String dataNascimentoStr = request.getParameter("data_nascimento");
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");
        
        LocalDate dataNascimento = converterData(dataNascimentoStr);
        
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setDataNascimento(dataNascimento);
        usuario.setTelefone(telefone);
        usuario.setSenhaHash(senha);
        usuario.setEndereco(endereco);
    }
}