package calisto.model.agencia;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Agencia {
    private int idAgencia;
    private String nome;
    private String codigoAgencia;
    private String enderecoId;
}
