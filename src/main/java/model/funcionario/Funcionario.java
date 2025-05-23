package model.funcionario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Funcionario {
    private int idFuncionario;
    private int usuarioId;
    private String codigoFuncionario;
    private Cargo cargo;
    private int supervisor;
}
