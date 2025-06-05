package CALISTO.model.persistence.Usuario;

import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.util.Cargo;

public class Funcionario extends Usuario{
    private int idFuncionario;
    private Usuario usuario;
    private String codigoFuncionario;
    private Cargo cargo;
    private int supervisor;
    private Endereco endereco;
}
