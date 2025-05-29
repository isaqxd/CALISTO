package calisto.model.transacao;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transacao {
    private int idTransacao;
    private int ContaOrigemId;
    private int contaDestinoId;
    private TipoDeTransacao tipoDeTransacao;
    private BigDecimal valor;
    private LocalDateTime dataHora = LocalDateTime.now();
    private String descricao;

}

