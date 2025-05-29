package calisto.model.cliente;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Cliente {
    private int idCliente;
    private int usuarioId;
    private BigDecimal scoreCredito = BigDecimal.ZERO;
}
