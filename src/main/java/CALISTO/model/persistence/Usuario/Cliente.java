package CALISTO.model.persistence.Usuario;

import java.math.BigDecimal;

public class Cliente extends Usuario {
    private int idCliente;
    private Usuario usuario;
    private BigDecimal scoreCredito;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getScoreCredito() {
        return scoreCredito;
    }

    public void setScoreCredito(BigDecimal scoreCredito) {
        this.scoreCredito = scoreCredito;
    }
}
