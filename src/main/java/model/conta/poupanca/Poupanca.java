package model.conta.poupanca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Poupanca {
    private int idContaPoupanca;
    private int contaId;
    private BigDecimal taxaRendimento;
    private LocalDateTime ultimoRendimento;

}

