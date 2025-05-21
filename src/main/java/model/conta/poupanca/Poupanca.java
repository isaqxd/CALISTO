package model.conta.poupanca;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Poupanca {
    private int idContaPoupanca;
    private int contaId;
    private BigDecimal taxaRendimento;
    private LocalDateTime ultimoRendimento;

    public int getIdContaPoupanca() {
        return idContaPoupanca;
    }

    public void setIdContaPoupanca(int idContaPoupanca) {
        this.idContaPoupanca = idContaPoupanca;
    }

    public int getContaId() {
        return contaId;
    }

    public void setContaId(int contaId) {
        this.contaId = contaId;
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

