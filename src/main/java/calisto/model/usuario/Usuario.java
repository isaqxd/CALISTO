package calisto.model.usuario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@ToString
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
