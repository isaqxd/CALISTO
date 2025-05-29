package calisto.model.auditoria;

import calisto.model.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Auditoria {
    private int idAuditoria; // chave primaria
    private Usuario usuario;
    private String acao;
    private LocalDateTime dataHora = LocalDateTime.now();
    private String detalhes;
}
