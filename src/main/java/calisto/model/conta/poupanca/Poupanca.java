package calisto.model.conta.poupanca;

import calisto.model.conta.Conta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Poupanca {
    private int idContaPoupanca;
    private Conta conta;
    private BigDecimal taxaRendimento;
    private LocalDateTime ultimoRendimento;

}