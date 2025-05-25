package model.transacao;

import lombok.Getter;
import lombok.Setter;
import model.relatorio.TipoDeTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transacao {
    private int idTransacao;
    private int idCondaOrigem;
    private int idCondaDestino;
    private TipoDeTransacao tipoDeTransacao;
    private BigDecimal valor;
    private LocalDateTime dataHora = LocalDateTime.now();
    private String descricao;

}

