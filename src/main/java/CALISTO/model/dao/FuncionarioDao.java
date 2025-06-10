package CALISTO.model.dao;

import CALISTO.model.mapper.UsuarioMapper;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.Cargo;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Funcionario> findAll() {
        String sql = """
                SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash,
                       u.tipo_usuario, u.otp_ativo, u.otp_expiracao,
                       e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento,
                       f.codigo_funcionario, f.cargo, f.id_supervisor
                FROM funcionario f
                JOIN usuario u ON f.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                """;
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                UsuarioMapper.fillUsuarioFromResultSet(rs, funcionario);
                funcionario.setCodigoFuncionario(rs.getString("codigo_funcionario"));
                funcionario.setCargo(Cargo.valueOf(rs.getString("cargo")));
                funcionario.setSupervisor(rs.getInt("id_supervisor"));

                funcionarios.add(funcionario);
            }
            return funcionarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários: " + e.getMessage(), e);
        }
    }

    public Funcionario findById(int id) {
        String sql = """
                SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash,
                       u.tipo_usuario, u.otp_ativo, u.otp_expiracao,
                       e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento,
                       f.codigo_funcionario, f.cargo, f.id_supervisor
                FROM funcionario f
                JOIN usuario u ON f.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                WHERE u.id_usuario = ?
                """;
        Funcionario funcionario = null;

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                funcionario = new Funcionario();
                UsuarioMapper.fillUsuarioFromResultSet(rs, funcionario);
                funcionario.setCodigoFuncionario(rs.getString("codigo_funcionario"));
                funcionario.setCargo(Cargo.valueOf(rs.getString("cargo")));
                funcionario.setSupervisor(rs.getInt("id_supervisor"));
            }
            return funcionario;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário por ID: " + e.getMessage(), e);
        }
    }

    public List<Funcionario> findByName(String nome) {
        String sql = """
                SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash,
                       u.tipo_usuario, u.otp_ativo, u.otp_expiracao,
                       e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento,
                       f.codigo_funcionario, f.cargo, f.id_supervisor
                FROM funcionario f
                JOIN usuario u ON f.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                WHERE u.nome LIKE ?
                """;
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);)
        {
            stmt.setString(1, "%" + nome + "%");
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    UsuarioMapper.fillUsuarioFromResultSet(rs, funcionario);
                    funcionario.setCodigoFuncionario(rs.getString("codigo_funcionario"));
                    funcionario.setCargo(Cargo.valueOf(rs.getString("cargo")));
                    funcionario.setSupervisor(rs.getInt("id_supervisor"));

                    funcionarios.add(funcionario);
                }
            }
            return funcionarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários pelo Nome: " + e.getMessage(), e);
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
