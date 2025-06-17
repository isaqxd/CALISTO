package CALISTO.model.persistence.Transacao;


import CALISTO.model.persistence.util.TipoDeTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private int idTransacao;
    private int contaOrigemId;
    private int contaDestinoId;
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

    public int getContaOrigemId() {return contaOrigemId;}

    public void setContaOrigemId(int contaOrigemId) {this.contaOrigemId = contaOrigemId;}

    public int getContaDestinoId() {return contaDestinoId;}

    public void setContaDestinoId(int contaDestinoId) {this.contaDestinoId = contaDestinoId;}
}
