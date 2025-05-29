package calisto.model.relatorio;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Relatorio {
    private int idRelatorio;
    private int idFuncioario;
    private String tipoRelatorio;
    private LocalDateTime dataGeracao = LocalDateTime.now();
    private String conteudo;
}
