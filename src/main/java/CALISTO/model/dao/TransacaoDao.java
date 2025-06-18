package CALISTO.model.dao;

import CALISTO.model.persistence.Transacao.Transacao;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoDeTransacao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class TransacaoDao {

    public boolean registrar(Transacao transacao) {
        String sql = "INSERT INTO transacao (id_conta_origem, id_conta_destino, tipo_transacao, valor, descricao) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transacao.getContaOrigemId());
            stmt.setInt(2, transacao.getContaDestinoId());
            stmt.setString(3, transacao.getTipoDeTransacao().name());
            stmt.setBigDecimal(4, transacao.getValor());
            stmt.setString(5, transacao.getDescricao());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Transacao> listarPorData(int idConta, LocalDateTime inicio, LocalDateTime fim) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacao WHERE (id_conta_origem = ? OR id_conta_destino = ?) " +
                     "AND data_hora BETWEEN ? AND ? ORDER BY data_hora DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConta);
            stmt.setInt(2, idConta);
            stmt.setTimestamp(3, Timestamp.valueOf(inicio));
            stmt.setTimestamp(4, Timestamp.valueOf(fim));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transacao transacao = new Transacao();
                transacao.setIdTransacao(rs.getInt("id_transacao"));
                transacao.setContaOrigemId(rs.getInt("id_conta_origem"));
                transacao.setContaDestinoId(rs.getInt("id_conta_destino"));
                transacao.setTipoDeTransacao(TipoDeTransacao.valueOf(rs.getString("tipo_transacao")));
                transacao.setValor(rs.getBigDecimal("valor"));
                transacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                transacao.setDescricao(rs.getString("descricao"));

                transacoes.add(transacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transacoes;
    }
}
