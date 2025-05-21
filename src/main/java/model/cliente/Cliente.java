package model.cliente;

import java.math.BigDecimal;

public class Cliente {
    private int idCliente;
    private int usuarioId;
    private BigDecimal scoreCredito = BigDecimal.ZERO;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getScoreCredito() {
        return scoreCredito;
    }

    public void setScoreCredito(BigDecimal scoreCredito) {
        this.scoreCredito = scoreCredito;
    }
}
