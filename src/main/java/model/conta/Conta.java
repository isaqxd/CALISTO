package model.conta;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Conta {
    private int idConta;
    private String numeroConta;
    private int agenciaId;
    private BigDecimal saldo = BigDecimal.ZERO;
    private TipoConta tipoConta;
    private int clienteId;
    private LocalDateTime dataAbertura = LocalDateTime.now();
    private Status status = Status.ATIVO;
}

