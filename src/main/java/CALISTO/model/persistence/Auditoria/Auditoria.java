package CALISTO.model.persistence.Auditoria;

import CALISTO.model.persistence.Usuario.Usuario;

import java.time.LocalDateTime;

public class Auditoria {
    private int idAuditoria;
    private Usuario usuario; // ID DE USUARIO QUE FEZ A MODIFICAÇÃO
    private String acao;
    private LocalDateTime dataHora;
    private String detalhes;

    public Auditoria() {};

    public Auditoria(Usuario usuario, String acao, LocalDateTime dataHora, String detalhes) {
        this.usuario = usuario;
        this.acao = acao;
        this.dataHora = dataHora;
        this.detalhes = detalhes;
    };

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
