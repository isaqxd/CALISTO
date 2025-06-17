package CALISTO.model.persistence.Usuario;

import CALISTO.model.persistence.Conta.Conta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private int idCliente;
    private Usuario usuario;
    private BigDecimal scoreCredito;

    public Cliente(int idCliente) {
        super();
    }

    public Cliente() {
    }

    private List<Conta> contas = new ArrayList<>();

    public List<Conta> getContas() {
        return contas;
    }

    public void addConta(Conta conta) {
        contas.add(conta);
    }


    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

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
