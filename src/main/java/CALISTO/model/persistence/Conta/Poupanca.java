package CALISTO.model.persistence.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Poupanca {
    private int idContaPoupanca;
    private Conta conta;
    private BigDecimal taxaRendimento;
    private LocalDateTime ultimoRendimento;

    public int getIdContaPoupanca() {
        return idContaPoupanca;
    }

    public void setIdContaPoupanca(int idContaPoupanca) {
        this.idContaPoupanca = idContaPoupanca;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public BigDecimal getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(BigDecimal taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public LocalDateTime getUltimoRendimento() {
        return ultimoRendimento;
    }

    public void setUltimoRendimento(LocalDateTime ultimoRendimento) {
        this.ultimoRendimento = ultimoRendimento;
    }
}
