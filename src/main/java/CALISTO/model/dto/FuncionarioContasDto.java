package CALISTO.model.dto;

import java.time.LocalDate;


public class FuncionarioContasDto {
    private String codigoFuncionario;
    private String cargo;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String enderecoCompleto;
    private int contasAbertas;

    // Construtor vazio
    public FuncionarioContasDto() {
    }

    // Construtor com todos os campos
    public FuncionarioContasDto(String codigoFuncionario, String cargo, String nome,
                                String cpf, LocalDate dataNascimento, String telefone,
                                String enderecoCompleto, int contasAbertas) {
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.enderecoCompleto = enderecoCompleto;
        this.contasAbertas = contasAbertas;
    }

    // Getters e Setters

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public int getContasAbertas() {
        return contasAbertas;
    }

    public void setContasAbertas(int contasAbertas) {
        this.contasAbertas = contasAbertas;
    }

}
