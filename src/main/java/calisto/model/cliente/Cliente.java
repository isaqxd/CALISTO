package calisto.model.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class Cliente {
    private int idCliente;
    private int usuarioId;
    private BigDecimal scoreCredito = BigDecimal.ZERO;
}
