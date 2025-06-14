package CALISTO.model.dao;

import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Corrente;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaCorrenteDao extends ContaDao {

    public void cadastrar(Corrente conta) throws SQLException {
        String sqlConta = "INSERT INTO conta (numero_conta, agencia_id, saldo, tipo_conta, cliente_id) VALUES (?, ?, ?, 'CORRENTE', ?)";
        String sqlCorrente = "INSERT INTO conta_corrente (conta_id, limite, data_vencimento, taxa_manutencao) VALUES (?, ?, ?, ?)";

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
                    try (PreparedStatement stmtCorrente = conn.prepareStatement(sqlCorrente)) {
                        stmtCorrente.setInt(1, contaId);
                        stmtCorrente.setBigDecimal(2, conta.getLimite());
                        stmtCorrente.setDate(3, Date.valueOf(conta.getDataVencimento()));
                        stmtCorrente.setBigDecimal(4, conta.getTaxaManutencao());
                        stmtCorrente.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean alterar(Corrente conta) {
        String sqlConta = """
            UPDATE conta SET saldo = ?, status = ?, tipo_conta = ?, data_abertura = ?, 
                             numero_conta = ?, agencia_id = ?, cliente_id = ?
            WHERE id_conta = ?
        """;

        String sqlCorrente = """
            UPDATE conta_corrente SET limite = ?, data_vencimento = ?, taxa_manutencao = ?
            WHERE conta_id = ?
        """;

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta)) {
                stmtConta.setBigDecimal(1, conta.getSaldo());
                stmtConta.setString(2, conta.getStatus().name());
                stmtConta.setString(3, conta.getTipoConta().name());
                stmtConta.setTimestamp(4, Timestamp.valueOf(conta.getDataAbertura()));
                stmtConta.setString(5, conta.getNumeroConta());
                stmtConta.setInt(6, conta.getAgencia().getIdAgencia());
                stmtConta.setInt(7, conta.getCliente().getIdCliente());
                stmtConta.setInt(8, conta.getIdConta());
                stmtConta.executeUpdate();
            }

            try (PreparedStatement stmtCorrente = conn.prepareStatement(sqlCorrente)) {
                stmtCorrente.setBigDecimal(1, conta.getLimite());
                stmtCorrente.setDate(2, Date.valueOf(conta.getDataVencimento()));
                stmtCorrente.setBigDecimal(3, conta.getTaxaManutencao());
                stmtCorrente.setInt(4, conta.getIdConta());
                stmtCorrente.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Corrente> buscarTodos() {
        List<Corrente> contas = new ArrayList<>();
        String sql = """
        SELECT 
            cc.id_conta_corrente, cc.limite, cc.data_vencimento, cc.taxa_manutencao,
            c.id_conta, c.numero_conta, c.saldo, c.tipo_conta, c.status, c.data_abertura,
            cli.id_cliente, u.nome, u.cpf, u.data_nascimento, u.telefone,
            a.id_agencia, a.nome AS nome_agencia, a.codigo_agencia
        FROM conta_corrente cc
        INNER JOIN conta c ON cc.conta_id = c.id_conta
        INNER JOIN cliente cli ON c.cliente_id = cli.id_cliente
        INNER JOIN usuario u ON cli.usuario_id = u.id_usuario
        INNER JOIN agencia a ON c.agencia_id = a.id_agencia;
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Corrente corrente = new Corrente();
                corrente.setIdConta(rs.getInt("id_conta"));
                corrente.setNumeroConta(rs.getString("numero_conta"));
                corrente.setSaldo(rs.getBigDecimal("saldo"));
                corrente.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                corrente.setStatus(Status.valueOf(rs.getString("status")));
                corrente.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNome(rs.getString("nome_agencia"));
                agencia.setCodigoAgencia(rs.getString("codigo_agencia"));
                corrente.setAgencia(agencia);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setTelefone(rs.getString("telefone"));
                corrente.setCliente(cliente);

                corrente.setIdContaCorrente(rs.getInt("id_conta_corrente"));
                corrente.setLimite(rs.getBigDecimal("limite"));
                corrente.setDataVencimento(rs.getDate("data_vencimento").toLocalDate());
                corrente.setTaxaManutencao(rs.getBigDecimal("taxa_manutencao"));
                corrente.setConta(corrente);

                contas.add(corrente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contas;
    }

    public boolean alterarStatus(int contaId, String novoStatus) {
        String sql = "UPDATE conta SET status = ? WHERE id_conta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, contaId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Corrente buscarPorConta(int contaId) {
        String sql = """
        SELECT 
            cc.id_conta_corrente, cc.limite, cc.data_vencimento, cc.taxa_manutencao,
            c.id_conta, c.numero_conta, c.saldo, c.tipo_conta, c.status, 
            c.data_abertura, c.agencia_id, c.cliente_id
        FROM conta_corrente cc
        INNER JOIN conta c ON cc.conta_id = c.id_conta
        WHERE cc.conta_id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Corrente corrente = new Corrente();
                corrente.setIdConta(rs.getInt("id_conta"));
                corrente.setNumeroConta(rs.getString("numero_conta"));
                corrente.setSaldo(rs.getBigDecimal("saldo"));
                corrente.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                corrente.setStatus(Status.valueOf(rs.getString("status")));
                corrente.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("agencia_id"));
                corrente.setAgencia(agencia);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("cliente_id"));
                corrente.setCliente(cliente);

                corrente.setIdContaCorrente(rs.getInt("id_conta_corrente"));
                corrente.setLimite(rs.getBigDecimal("limite"));
                corrente.setDataVencimento(rs.getDate("data_vencimento").toLocalDate());
                corrente.setTaxaManutencao(rs.getBigDecimal("taxa_manutencao"));
                corrente.setConta(corrente);

                return corrente;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
