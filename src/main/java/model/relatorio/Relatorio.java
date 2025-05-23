package model.relatorio;

import java.time.LocalDateTime;

public class Relatorio {
    private int idRelatorio;
    private int idFuncioario;
    private String tipoRelatorio;
    private LocalDateTime dataGeracao = LocalDateTime.now();
    private String conteudo;

    public int getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(int idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public int getIdFuncioario() {
        return idFuncioario;
    }

    public void setIdFuncioario(int idFuncioario) {
        this.idFuncioario = idFuncioario;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
