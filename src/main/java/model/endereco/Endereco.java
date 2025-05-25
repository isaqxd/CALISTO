package model.endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {
    private int idEndereco;
    private int usuarioId;
    private String cep;
    private String local;
    private int numeroCasa;
    private String bairro;
    private String cidade;
    private char estado;
    private String complemento;
}
