package CALISTO.model.dao;

import CALISTO.model.dto.FuncionarioContasDto;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaFuncionarioDao {
    public List<FuncionarioContasDto> listarFuncionariosComContas(String cpf) {
        String sql = "SELECT * FROM vw_dados_funcionario_contas  WHERE cpf = ?";
        List<FuncionarioContasDto> funcionarios = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                FuncionarioContasDto dto = new FuncionarioContasDto(
                        rs.getString("codigo"),
                        rs.getString("cargo"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("telefone"),
                        rs.getString("endereco_completo"),
                        rs.getInt("contas_abertas")
                );
                funcionarios.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }
}
