package CALISTO.model.persistence.Agencia;

import CALISTO.model.persistence.Endereco.Endereco;

public class Agencia {
    private int idAgencia;
    private String nome;
    private String codigoAgencia;
    private Endereco endereco;

    public Agencia(int idAgencia) {
    }

    public Agencia() {
    }

    public int getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(int idAgencia) {
        this.idAgencia = idAgencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
