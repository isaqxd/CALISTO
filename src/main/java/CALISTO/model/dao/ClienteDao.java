package CALISTO.model.dao;

import CALISTO.model.mapper.UsuarioMapper;
import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.Conta.Corrente;
import CALISTO.model.persistence.Conta.Investimento;
import CALISTO.model.persistence.Conta.Poupanca;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;
import CALISTO.model.persistence.util.TipoUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static CALISTO.model.mapper.UsuarioMapper.close;
import static CALISTO.model.mapper.UsuarioMapper.rollBack;

public class ClienteDao {
    // CRUD
    public Cliente save(Cliente cliente) {
        String sqlEndereco = """
                    INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, complemento)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        String sqlUsuario = """
                    INSERT INTO usuario (nome, cpf, data_nascimento, telefone, senha_hash, tipo_usuario, endereco_id, otp_ativo, otp_expiracao)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        String sqlCliente = "INSERT INTO cliente (usuario_id, score_credito) VALUES (?, ?)";

        Connection con = null;
        try {
            con = Conexao.getConnection();
            con.setAutoCommit(false);

            // Inserir endereço e obter ID
            int enderecoId = UsuarioMapper.inserirEndereco(con, cliente, sqlEndereco);
            cliente.getEndereco().setIdEndereco(enderecoId);

            // Inserir usuário e obter ID
            int usuarioId = UsuarioMapper.inserirUsuario(con, cliente, sqlUsuario);
            cliente.setIdUsuario(usuarioId);

            // Inserir cliente
            inserirCliente(con, cliente, sqlCliente);

            con.commit();

            return cliente;

        } catch (SQLException e) {
            rollBack(con);
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        } finally {
            close(con);
        }
    }

    public List<Cliente> findAll() {
        String sql = """
                 SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash,\s
                        u.tipo_usuario, u.otp_ativo, u.otp_expiracao,
                        e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento,
                        c.score_credito
                FROM cliente c
                JOIN usuario u ON c.usuario_id = u.id_usuario
                JOIN endereco e ON u.endereco_id = e.id_endereco
                \s""";
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
        String sqlEndereco = """
                    UPDATE endereco SET cep = ?, local = ?, numero_casa = ?, bairro = ?, cidade = ?, estado = ?, complemento = ?\s
                    WHERE id_endereco = ?
                \s""";

        String sqlUsuario = """
                     UPDATE usuario SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, senha_hash = ?, tipo_usuario = ?,\s
                     endereco_id = ?, otp_ativo = ?, otp_expiracao = ?
                     WHERE id_usuario = ?
                \s""";

        String sqlCliente = "UPDATE cliente SET score_credito = ? WHERE usuario_id = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar endereço
            try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {
                UsuarioMapper.fillPreparedStatementEndereco(stmtEndereco, cliente.getEndereco());
                stmtEndereco.setInt(8, cliente.getEndereco().getIdEndereco());
                stmtEndereco.executeUpdate();
            }

            // Atualizar usuário
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                UsuarioMapper.fillPreparedStatementUsuario(stmtUsuario, cliente);
                stmtUsuario.setInt(10, cliente.getIdUsuario());
                stmtUsuario.executeUpdate();
            }

            // Atualizar score de crédito
            try (PreparedStatement stmtScore = conn.prepareStatement(sqlCliente)) {
                stmtScore.setBigDecimal(1, cliente.getScoreCredito());
                stmtScore.setInt(2, cliente.getIdUsuario());
                stmtScore.executeUpdate();
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
                    SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash,
                           u.tipo_usuario, u.otp_ativo, u.otp_expiracao,
                           e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento,
                           c.score_credito
                    FROM cliente c
                    JOIN usuario u ON c.usuario_id = u.id_usuario
                    JOIN endereco e ON u.endereco_id = e.id_endereco
                    WHERE u.nome LIKE ?
                """;
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
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

    public Cliente findById(int id) {
        String sql = """
                    SELECT u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.senha_hash,
                           u.tipo_usuario, u.otp_ativo, u.otp_expiracao,
                           e.id_endereco, e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado, e.complemento,
                           c.score_credito
                    FROM cliente c
                    JOIN usuario u ON c.usuario_id = u.id_usuario
                    JOIN endereco e ON u.endereco_id = e.id_endereco
                    WHERE u.id_usuario = ?
                """;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return accessCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public Cliente findByCpf(String cpf) {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSenhaHash(rs.getString("senha_hash"));
                cliente.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente innerToRequestSession(String cpf) {
        String sql = """
            SELECT u.nome, u.cpf, u.id_usuario,
                   c.id_cliente,
                   co.id_conta, co.numero_conta, co.saldo, co.tipo_conta, co.status
            FROM usuario u
            INNER JOIN cliente c ON u.id_usuario = c.usuario_id
            LEFT JOIN conta co ON c.id_cliente = co.cliente_id AND co.status = 'ATIVA'
            WHERE u.cpf = ?
            """; // LEFT JOIN para trazer cliente mesmo sem contas

        Cliente cliente = null;

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (cliente == null) {
                    cliente = new Cliente();
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setContas(new ArrayList<>());
                }

                // Verifica se há dados de conta (pode ser null por causa do LEFT JOIN)
                if (rs.getObject("id_conta") != null) {
                    Conta conta = criarConta(rs);
                    cliente.getContas().add(conta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar cliente por CPF", e);
        }

        return cliente;
    }

    private Conta criarConta(ResultSet rs) throws SQLException {
        return switch (rs.getString("tipo_conta")) {
            case "CORRENTE" -> {
                Corrente cc = new Corrente();
                cc.setIdConta(rs.getInt("id_conta"));
                cc.setNumeroConta(rs.getString("numero_conta"));
                cc.setSaldo(rs.getBigDecimal("saldo"));
                cc.setTipoConta(TipoConta.CORRENTE);
                cc.setStatus(Status.valueOf(rs.getString("status")));
                yield cc;
            }
            case "POUPANCA" -> {
                Poupanca cp = new Poupanca();
                cp.setIdConta(rs.getInt("id_conta"));
                cp.setNumeroConta(rs.getString("numero_conta"));
                cp.setSaldo(rs.getBigDecimal("saldo"));
                cp.setTipoConta(TipoConta.POUPANCA);
                cp.setStatus(Status.valueOf(rs.getString("status")));
                yield cp;
            }
            case "INVESTIMENTO" -> {
                Investimento ci = new Investimento();
                ci.setIdConta(rs.getInt("id_conta"));
                ci.setNumeroConta(rs.getString("numero_conta"));
                ci.setSaldo(rs.getBigDecimal("saldo"));
                ci.setTipoConta(TipoConta.INVESTIMENTO);
                ci.setStatus(Status.valueOf(rs.getString("status")));
                yield ci;
            }
            default -> throw new SQLException("Tipo de conta desconhecido");
        };
    }

    // FUNÇÕES AUXILIARES
    public Cliente accessCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_usuario"));
        UsuarioMapper.fillUsuarioFromResultSet(rs, cliente);
        cliente.setScoreCredito(rs.getBigDecimal("score_credito"));
        return cliente;
    }

    private int inserirCliente(Connection conn, Cliente cliente, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cliente.getIdUsuario());
            stmt.setBigDecimal(2, cliente.getScoreCredito());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setIdCliente(rs.getInt(1));
            }
        }
        return 0;
    }


}