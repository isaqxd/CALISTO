package model.transacao;

import model.relatorio.TipoDeTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private int idTransacao;
    private int idCondaOrigem;
    private int idCondaDestino;
    private TipoDeTransacao tipoDeTransacao;
    private BigDecimal valor;
    private LocalDateTime dataHora = LocalDateTime.now();
    private String descricao;

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public int getIdCondaOrigem() {
        return idCondaOrigem;
    }

    public void setIdCondaOrigem(int idCondaOrigem) {
        this.idCondaOrigem = idCondaOrigem;
    }

    public int getIdCondaDestino() {
        return idCondaDestino;
    }

    public void setIdCondaDestino(int idCondaDestino) {
        this.idCondaDestino = idCondaDestino;
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

