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
    public Endereco makeEndereco(ResultSet rs) throws SQLException {
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
    public Cliente makeCliente(ResultSet rs) throws SQLException {
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
        cliente.setEndereco(makeEndereco(rs));
        return cliente;
    }

    public Cliente save(Cliente cliente) {
        String sqlEndereco = "INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, complemento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, data_nascimento, telefone, senha_hash, tipo_usuario, endereco_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (usuario_id) VALUES (?)";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Inserir endereço
            int enderecoId;
            try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                Endereco end = cliente.getEndereco();
                stmtEndereco.setString(1, end.getCep());
                stmtEndereco.setString(2, end.getLocal());
                stmtEndereco.setInt(3, end.getNumeroCasa());
                stmtEndereco.setString(4, end.getBairro());
                stmtEndereco.setString(5, end.getCidade());
                stmtEndereco.setString(6, end.getEstado());
                stmtEndereco.setString(7, end.getComplemento());
                stmtEndereco.executeUpdate();

                try (ResultSet rs = stmtEndereco.getGeneratedKeys()) {
                    if (rs.next()) {
                        enderecoId = rs.getInt(1);
                        end.setIdEndereco(enderecoId);
                    } else {
                        throw new SQLException("Falha ao obter o ID do endereço.");
                    }
                }
            }

            // Inserir usuário
            int usuarioId;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, cliente.getNome());
                stmtUsuario.setString(2, cliente.getCpf());
                LocalDate dataNascimento = cliente.getDataNascimento();
                if (dataNascimento != null) {
                    stmtUsuario.setDate(3, java.sql.Date.valueOf(dataNascimento));
                } else {
                    stmtUsuario.setNull(3, java.sql.Types.DATE);
                }
                stmtUsuario.setString(4, cliente.getTelefone());
                stmtUsuario.setString(5, cliente.getSenhaHash());
                stmtUsuario.setString(6, cliente.getTipoUsuario().name());
                stmtUsuario.setInt(7, enderecoId);
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
                clientes.add(makeCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}