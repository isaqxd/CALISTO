package CALISTO.model.persistence.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;

public abstract class Conta {

    private int idConta;
    private String numeroConta;
    private Agencia agencia;
    private BigDecimal saldo;
    private TipoConta tipoConta;
    private Cliente cliente;
    private LocalDateTime dataAbertura;
    private Status status;

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
