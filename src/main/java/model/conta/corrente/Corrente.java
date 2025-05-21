package model.conta.corrente;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Corrente {
    private int idContaCorrente;
    private int contaId;
    private BigDecimal limite = BigDecimal.ZERO;
    private LocalDate dataVencimento;
    private BigDecimal taxaManutencao = BigDecimal.ZERO;

    public int getIdContaCorrente() {
        return idContaCorrente;
    }

    public void setIdContaCorrente(int idContaCorrente) {
        this.idContaCorrente = idContaCorrente;
    }

    public int getContaId() {
        return contaId;
    }

    public void setContaId(int contaId) {
        this.contaId = contaId;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getTaxaManutencao() {
        return taxaManutencao;
    }

    public void setTaxaManutencao(BigDecimal taxaManutencao) {
        this.taxaManutencao = taxaManutencao;
    }
}
