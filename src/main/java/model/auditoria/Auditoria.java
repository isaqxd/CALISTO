package model.auditoria;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Auditoria {
    private int idAuditoria; // chave primaria
    private int usuarioId;
    private String acao;
    private LocalDateTime dataHora = LocalDateTime.now();
    private String detalhes;
}
