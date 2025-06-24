package CALISTO.model.dao;


import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Poupanca;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaPoupancaDao extends ContaDao {


    public void save(Poupanca conta) throws SQLException {
        String sqlConta = "INSERT INTO conta (numero_conta, agencia_id, saldo, tipo_conta, cliente_id) VALUES (?, ?, ?, 'POUPANCA', ?)";
        String sqlPoupanca = "INSERT INTO conta_poupanca (conta_id, taxa_rendimento, ultimo_rendimento) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta, Statement.RETURN_GENERATED_KEYS)) {
                stmtConta.setString(1, conta.getNumeroConta());
                stmtConta.setInt(2, conta.getAgencia().getIdAgencia());
                stmtConta.setBigDecimal(3, conta.getSaldo());
                stmtConta.setInt(4, conta.getCliente().getIdCliente());
                stmtConta.executeUpdate();

                ResultSet generatedKeys = stmtConta.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int contaId = generatedKeys.getInt(1);
                    try (PreparedStatement stmtPoupanca = conn.prepareStatement(sqlPoupanca)) {
                        stmtPoupanca.setInt(1, contaId);
                        stmtPoupanca.setBigDecimal(2, conta.getTaxaRendimento());
                        stmtPoupanca.setTimestamp(3, Timestamp.valueOf(conta.getUltimoRendimento()));
                        stmtPoupanca.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean alterar(Poupanca poupanca) {
        String sqlConta = """
        UPDATE conta SET saldo = ?, status = ?, tipo_conta = ?, data_abertura = ?, 
                         numero_conta = ?, agencia_id = ?, cliente_id = ?
        WHERE id_conta = ?
    """;

        String sqlPoupanca = """
        UPDATE conta_poupanca SET taxa_rendimento = ?, ultimo_rendimento = ?
        WHERE conta_id = ?
    """;

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Atualiza a tabela CONTA
            try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta)) {
                stmtConta.setBigDecimal(1, poupanca.getSaldo());
                stmtConta.setString(2, poupanca.getStatus().name());
                stmtConta.setString(3, poupanca.getTipoConta().name());
                stmtConta.setTimestamp(4, Timestamp.valueOf(poupanca.getDataAbertura()));
                stmtConta.setString(5, poupanca.getNumeroConta());
                stmtConta.setInt(6, poupanca.getAgencia().getIdAgencia());
                stmtConta.setInt(7, poupanca.getCliente().getIdCliente());
                stmtConta.setInt(8, poupanca.getIdConta());
                stmtConta.executeUpdate();
            }

            // Atualiza a tabela CONTA_POUPANCA
            try (PreparedStatement stmtPoupanca = conn.prepareStatement(sqlPoupanca)) {
                stmtPoupanca.setBigDecimal(1, poupanca.getTaxaRendimento());
                stmtPoupanca.setTimestamp(2, Timestamp.valueOf(poupanca.getUltimoRendimento()));
                stmtPoupanca.setInt(3, poupanca.getIdConta());
                stmtPoupanca.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Poupanca> buscarTodos() {
        List<Poupanca> contas = new ArrayList<>();
        String sql = """
        SELECT\s
            cp.id_conta_poupanca, cp.taxa_rendimento, cp.ultimo_rendimento,
            c.id_conta, c.numero_conta, c.saldo, c.tipo_conta, c.status, c.data_abertura,
            cli.id_cliente, u.nome, u.cpf, u.data_nascimento, u.telefone,
            a.id_agencia, a.nome AS nome_agencia, a.codigo_agencia
        FROM conta_poupanca cp
        INNER JOIN conta c ON cp.conta_id = c.id_conta
        INNER JOIN cliente cli ON c.cliente_id = cli.id_cliente
        INNER JOIN usuario u ON cli.usuario_id = u.id_usuario
        INNER JOIN agencia a ON c.agencia_id = a.id_agencia;
    """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Poupanca poupanca = new Poupanca();
                poupanca.setIdConta(rs.getInt("id_conta"));
                poupanca.setNumeroConta(rs.getString("numero_conta"));
                poupanca.setSaldo(rs.getBigDecimal("saldo"));
                poupanca.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                poupanca.setStatus(Status.valueOf(rs.getString("status")));
                poupanca.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNome(rs.getString("nome_agencia"));
                agencia.setCodigoAgencia(rs.getString("codigo_agencia"));
                poupanca.setAgencia(agencia);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setTelefone(rs.getString("telefone"));
                poupanca.setCliente(cliente);

                poupanca.setIdContaPoupanca(rs.getInt("id_conta_poupanca"));
                poupanca.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));
                poupanca.setUltimoRendimento(rs.getTimestamp("ultimo_rendimento") != null
                        ? rs.getTimestamp("ultimo_rendimento").toLocalDateTime()
                        : null);
                poupanca.setConta(poupanca); // herança estrutural

                contas.add(poupanca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contas;
    }

    public Poupanca buscarPorConta(int contaId) {
        String sql = """
        SELECT 
            cp.id_conta_poupanca, cp.taxa_rendimento, cp.ultimo_rendimento,
            c.id_conta, c.numero_conta, c.saldo, c.tipo_conta, c.status, 
            c.data_abertura, c.agencia_id, c.cliente_id
        FROM conta_poupanca cp
        INNER JOIN conta c ON cp.conta_id = c.id_conta
        WHERE cp.conta_id = ?
    """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Conta base
                Poupanca poupanca = new Poupanca();
                poupanca.setIdConta(rs.getInt("id_conta"));
                poupanca.setNumeroConta(rs.getString("numero_conta"));
                poupanca.setSaldo(rs.getBigDecimal("saldo"));
                poupanca.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                poupanca.setStatus(Status.valueOf(rs.getString("status")));
                poupanca.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                // Relacionamentos (IDs apenas, ou você pode buscar os objetos completos)
                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("agencia_id"));
                poupanca.setAgencia(agencia);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("cliente_id"));
                poupanca.setCliente(cliente);

                // Conta poupança
                poupanca.setIdContaPoupanca(rs.getInt("id_conta_poupanca"));
                poupanca.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));
                poupanca.setUltimoRendimento(rs.getTimestamp("ultimo_rendimento").toLocalDateTime());
                poupanca.setConta(poupanca);

                return poupanca;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}