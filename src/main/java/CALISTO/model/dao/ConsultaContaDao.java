package CALISTO.model.dao;

import CALISTO.model.dto.RelatorioTransacaoDto;
import CALISTO.model.persistence.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaContaDao {

    public List<RelatorioTransacaoDto> buscarContaComTransacoes(String cpf) {
        List<RelatorioTransacaoDto> resultado = new ArrayList<>();
        String sql = "SELECT * FROM view_consulta_conta_com_transacoes WHERE cpf = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RelatorioTransacaoDto dto = new RelatorioTransacaoDto();
                dto.setNumeroConta(rs.getString("numero_conta"));
                dto.setTipoConta(rs.getString("tipo_conta"));
                dto.setNome(rs.getString("titular"));
                dto.setCpf(rs.getString("cpf"));
                dto.setSaldo(rs.getBigDecimal("saldo"));
                dto.setLimite(rs.getBigDecimal("limite"));
                dto.setDataVencimento(rs.getDate("data_vencimento"));
                dto.setTipoTransacao(rs.getString("tipo_transacao"));
                dto.setValorTransacao(rs.getBigDecimal("valor_transacao"));
                dto.setDataTransacao(rs.getTimestamp("data_transacao"));
                dto.setDescricao(rs.getString("descricao"));
                resultado.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
