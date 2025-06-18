package CALISTO.model.dao;

import CALISTO.model.dto.RelatorioContaDto;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteContaDao {
    public List<RelatorioContaDto> buscarPorCpf(String cpf) {
        List<RelatorioContaDto> lista = new ArrayList<>();
        String sql = "SELECT * FROM vw_dados_cliente_contas WHERE cpf = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RelatorioContaDto dto = new RelatorioContaDto();
                dto.setNome(rs.getString("nome"));
                dto.setCpf(rs.getString("cpf"));
                dto.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                dto.setTelefone(rs.getString("telefone"));
                dto.setEndereco(rs.getString("endereco"));
                dto.setScoreCredito(rs.getBigDecimal("score_credito"));
                dto.setNumeroConta(rs.getString("numero_conta"));
                dto.setTipoConta(rs.getString("tipo_conta"));
                dto.setStatus(rs.getString("status"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

