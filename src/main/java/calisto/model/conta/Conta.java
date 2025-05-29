package calisto.model.conta;

import calisto.model.agencia.Agencia;
import calisto.model.cliente.Cliente;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Conta {
    private int idConta;
    private String numeroConta;
    private Agencia agencia;
    private BigDecimal saldo;
    private TipoConta tipoConta;
    private Cliente cliente;
    private LocalDateTime dataAbertura;
    private Status status;
}