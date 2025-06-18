package CALISTO.model.dao;

import CALISTO.model.dto.FuncionarioContasDto;
import CALISTO.model.persistence.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaFuncionarioDao {
    public List<FuncionarioContasDto> listarFuncionariosComContas(String cpf) {
        List<FuncionarioContasDto> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM vw_dados_funcionario_contas WHERE cpf = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf.trim());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FuncionarioContasDto dto = new FuncionarioContasDto();
                dto.setCodigoFuncionario(rs.getString("codigo"));
                dto.setCargo(rs.getString("cargo"));
                dto.setNome(rs.getString("nome"));
                dto.setCpf(rs.getString("cpf"));
                dto.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                dto.setTelefone(rs.getString("telefone"));
                dto.setEnderecoCompleto(rs.getString("endereco"));
                dto.setContasAbertas(rs.getInt("contas_abertas"));
                funcionarios.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }
}
