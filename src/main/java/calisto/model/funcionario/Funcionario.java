package calisto.model.funcionario;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import calisto.model.usuario.Usuario;

@Getter
@Setter
@Accessors(chain = true)
public class Funcionario {
    private int idFuncionario;
    private Usuario usuario;
    private String codigoFuncionario;
    private Cargo cargo;
    private int supervisor;
}
