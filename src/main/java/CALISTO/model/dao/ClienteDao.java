package CALISTO.model.dao;

import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoUsuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    public Endereco accessEndereco(ResultSet rs) throws SQLException {
        Endereco end = new Endereco(
                rs.getString("cep"),
                rs.getString("local"),
                rs.getString("numero_casa"),
                rs.getString("bairro"),
                rs.getString("cidade"),
                rs.getString("estado"),
                rs.getString("complemento")
        );
        end.setIdEndereco(rs.getInt("id_endereco"));
        return end;
    }
    public Cliente accessCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(rs.getInt("id_usuario"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        Date data = rs.getDate("data_nascimento");
        if (data != null) {
            cliente.setDataNascimento(data.toLocalDate());
        }
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setSenhaHash(rs.getString("senha_hash"));
        cliente.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
        cliente.setEndereco(accessEndereco(rs));
        return cliente;
    }

    public void statementToEndereco(PreparedStatement stmt, Endereco endereco) throws SQLException {
        stmt.setString(1, endereco.getCep());
        stmt.setString(2, endereco.getLocal());
        stmt.setInt(3, endereco.getNumeroCasa());
        stmt.setString(4, endereco.getBairro());
        stmt.setString(5, endereco.getCidade());
        stmt.setString(6, endereco.getEstado());
        stmt.setString(7, endereco.getComplemento());
    }
    public void statementToCliente(PreparedStatement stmt, Cliente cliente) throws SQLException {
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getCpf());
        LocalDate dataNascimento = cliente.getDataNascimento();
        if (dataNascimento != null) {
            stmt.setDate(3, java.sql.Date.valueOf(dataNascimento));
        } else {
            stmt.setNull(3, java.sql.Types.DATE);
        }
        stmt.setString(4, cliente.getTelefone());
        stmt.setString(5, cliente.getSenhaHash());
        stmt.setString(6, cliente.getTipoUsuario().name());
        stmt.setInt(7, cliente.getEndereco().getIdEndereco());
        stmt.setString(8, cliente.getOtpAtivo());
        if (cliente.getOtpExpiracao() != null) {
            stmt.setTimestamp(9, java.sql.Timestamp.valueOf(cliente.getOtpExpiracao()));
        } else {
            stmt.setNull(9, java.sql.Types.TIMESTAMP);
        }
    }


    public Cliente save(Cliente cliente) {
        String sqlEndereco = "INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, complemento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, data_nascimento, telefone, senha_hash, tipo_usuario, endereco_id, otp_ativo, otp_expiracao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (usuario_id) VALUES (?)";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Inserir endereço
            int enderecoId;
            try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                statementToEndereco(stmtEndereco, cliente.getEndereco());
                stmtEndereco.executeUpdate();

                try (ResultSet rs = stmtEndereco.getGeneratedKeys()) {
                    if (rs.next()) {
                        enderecoId = rs.getInt(1);
                        cliente.getEndereco().setIdEndereco(enderecoId);
                    } else {
                        throw new SQLException("Falha ao obter o ID do endereço.");
                    }
                }
            }

            // Inserir usuário
            int usuarioId;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                // Atualiza o id do endereço no cliente antes de preencher o statement
                statementToCliente(stmtUsuario, cliente);
                stmtUsuario.setInt(7, cliente.getEndereco().getIdEndereco()); // Garante o id correto
                stmtUsuario.executeUpdate();

                try (ResultSet rs = stmtUsuario.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuarioId = rs.getInt(1);
                    } else {
                        throw new SQLException("Falha ao obter o ID do usuário.");
                    }
                }
            }

            // Inserir cliente
            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
                stmtCliente.setInt(1, usuarioId);
                stmtCliente.executeUpdate();
            }

            conn.commit();
            return cliente;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> findAll() {
        String sql = """
                SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash, u.tipo_usuario,
                e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento
                FROM cliente c
                JOIN usuario u ON c.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                """;
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(accessCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public Cliente update(Cliente cliente) {
        String sqlEndereco = "UPDATE endereco SET cep = ?, local = ?, numero_casa = ?, bairro = ?, cidade = ?, estado = ?, complemento = ? WHERE id_endereco = ?";
        String sqlUsuario = "UPDATE usuario SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, senha_hash = ?, tipo_usuario = ?, endereco_id = ?, otp_ativo = ?, otp_expiracao = ? WHERE id_usuario = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar endereço
            try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {
                statementToEndereco(stmtEndereco, cliente.getEndereco());
                stmtEndereco.setInt(8, cliente.getEndereco().getIdEndereco());
                stmtEndereco.executeUpdate();
            }

            // Atualizar usuário
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                statementToCliente(stmtUsuario, cliente);
                stmtUsuario.setInt(10, cliente.getIdUsuario());
                stmtUsuario.executeUpdate();
            }

            conn.commit();
            return cliente;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> findByName(String nome) {
        String sql = """
                SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash, u.tipo_usuario,
                e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento
                FROM cliente c
                JOIN usuario u ON c.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                WHERE u.nome LIKE ?
                """;
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%"); // Filtro pelo nome
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(accessCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public Cliente findByid(int id) {
        String sql = """
                SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash, u.tipo_usuario,
                e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento
                FROM cliente c
                JOIN usuario u ON c.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                WHERE usuario_id = ?
                """;
        Cliente cliente = null;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, id); // Filtro pelo nome
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cliente = accessCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public boolean delete(int id) {
        String sqlCliente = "DELETE FROM cliente WHERE usuario_id = ?";
        String sqlUsuario = "DELETE FROM usuario WHERE id_usuario = ?";
        String sqlEndereco = "DELETE FROM endereco WHERE id_endereco = (SELECT endereco_id FROM usuario WHERE id_usuario = ?)";
        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
                stmtCliente.setInt(1, id);
                stmtCliente.executeUpdate();
            }
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                stmtUsuario.setInt(1, id);
                stmtUsuario.executeUpdate();
            }
            try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {
                stmtEndereco.setInt(1, id);
                stmtEndereco.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}