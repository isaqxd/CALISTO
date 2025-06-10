package CALISTO.model.dao;

import CALISTO.model.mapper.UsuarioMapper;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static CALISTO.model.mapper.UsuarioMapper.close;
import static CALISTO.model.mapper.UsuarioMapper.rollBack;

public class FuncionarioDao {
    // CRUD
    public Funcionario save(Funcionario funcionario) {
        String sqlEndereco = """
                    INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, complemento)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        String sqlUsuario = """
                    INSERT INTO usuario (nome, cpf, data_nascimento, telefone, senha_hash, tipo_usuario, endereco_id, otp_ativo, otp_expiracao)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        String sqlFuncionario = """
                    INSERT INTO funcionario (usuario_id, codigo_funcionario, cargo, id_supervisor)
                    VALUES (?, ?, ?, ?)
                """;
        Connection con = null;

        try {
            con = Conexao.getConnection();
            con.setAutoCommit(false);

            // Inserir endereço e obter ID
            int enderecoId = UsuarioMapper.inserirEndereco(con, funcionario, sqlEndereco);
            funcionario.getEndereco().setIdEndereco(enderecoId);

            // Inserir usuário e obter ID
            int usuarioId = UsuarioMapper.inserirUsuario(con, funcionario, sqlUsuario);
            funcionario.setIdUsuario(usuarioId);

            // Inserir funcionario
            insertFuncionario(con, funcionario, sqlFuncionario);
            con.commit();

            return funcionario;
        } catch (SQLException e) {
            rollBack(con);
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        } finally {
            close(con);
        }
    }

    private int insertFuncionario(Connection con, Funcionario funcionario, String sql) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, funcionario.getIdUsuario());
            stmt.setString(2, funcionario.getCodigoFuncionario());
            stmt.setString(3, funcionario.getCargo().name());
            if (funcionario.getSupervisor() == 0) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, funcionario.getSupervisor());
            }

            stmt.executeUpdate();
        }
        return 0;
    }

}
