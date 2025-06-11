package CALISTO.model.persistence.Transacao;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.util.TipoDeTransacao;

public class Transacao {
    private int idTransacao;
    private Conta Conta;
    private TipoDeTransacao tipoDeTransacao;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String descricao;

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Conta getConta() {
        return Conta;
    }

    public void setConta(Conta conta) {
        Conta = conta;
    }

    public TipoDeTransacao getTipoDeTransacao() {
        return tipoDeTransacao;
    }

    public void setTipoDeTransacao(TipoDeTransacao tipoDeTransacao) {
        this.tipoDeTransacao = tipoDeTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
