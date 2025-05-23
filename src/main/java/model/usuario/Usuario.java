package model.usuario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Usuario {
    private int idUsuario;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private TipoUsuario tipoUsuario;
    private String senhaHash;
    private String otpAtivo;
    private LocalDateTime otpExpiracao;
}
