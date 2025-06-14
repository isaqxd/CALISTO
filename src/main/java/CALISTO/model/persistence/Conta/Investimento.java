package CALISTO.model.persistence.Conta;

import java.math.BigDecimal;
import CALISTO.model.persistence.util.PerfilRisco;

public class Investimento extends Conta {
    private int idContaInvestimento;
    private Conta conta;
    private PerfilRisco perfilRisco;
    private BigDecimal valorMinimo;
    private BigDecimal taxaRendimento;

    public int getIdContaInvestimento() {
        return idContaInvestimento;
    }

    public void setIdContaInvestimento(int idContaInvestimento) {
        this.idContaInvestimento = idContaInvestimento;
    }

    public Conta getConta() { return conta; }

    public void setConta(Conta conta) { this.conta = conta; }

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

    public BigDecimal getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(BigDecimal taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }
}
