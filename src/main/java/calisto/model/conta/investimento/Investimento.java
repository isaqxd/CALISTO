package calisto.model.conta.investimento;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Investimento {
    private int idContaInvestimento;
    private int contaId;
    private PerfilRisco perfilRisco;
    private BigDecimal valorMinimo;
    private BigDecimal taxaRendimentoBase;
}
