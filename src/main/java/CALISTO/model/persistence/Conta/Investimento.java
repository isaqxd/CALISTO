package CALISTO.model.persistence.Conta;

import java.math.BigDecimal;
import CALISTO.model.persistence.util.PerfilRisco;

public class Investimento {
    private int idContaInvestimento;
    private int contaId;
    private PerfilRisco perfilRisco;
    private BigDecimal valorMinimo;
    private BigDecimal taxaRendimentoBase;

    public int getIdContaInvestimento() {
        return idContaInvestimento;
    }

    public void setIdContaInvestimento(int idContaInvestimento) {
        this.idContaInvestimento = idContaInvestimento;
    }

    public int getContaId() {
        return contaId;
    }

    public void setContaId(int contaId) {
        this.contaId = contaId;
    }

    public PerfilRisco getPerfilRisco() {
        return perfilRisco;
    }

    public void setPerfilRisco(PerfilRisco perfilRisco) {
        this.perfilRisco = perfilRisco;
    }

    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigDecimal getTaxaRendimentoBase() {
        return taxaRendimentoBase;
    }

    public void setTaxaRendimentoBase(BigDecimal taxaRendimentoBase) {
        this.taxaRendimentoBase = taxaRendimentoBase;
    }
}
