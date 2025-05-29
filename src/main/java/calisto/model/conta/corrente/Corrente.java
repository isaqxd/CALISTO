package calisto.model.conta.corrente;

import calisto.model.conta.Conta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Corrente {
    private int idContaCorrente;
    private Conta conta;
    private BigDecimal limite = BigDecimal.ZERO;
    private LocalDate dataVencimento;
    private BigDecimal taxaManutencao = BigDecimal.ZERO;
}
