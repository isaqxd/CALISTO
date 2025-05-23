package model.conta.corrente;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Corrente {
    private int idContaCorrente;
    private int contaId;
    private BigDecimal limite = BigDecimal.ZERO;
    private LocalDate dataVencimento;
    private BigDecimal taxaManutencao = BigDecimal.ZERO;
}
