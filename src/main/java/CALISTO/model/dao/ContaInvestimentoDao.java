package CALISTO.model.dao;

import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Conta.Investimento;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.PerfilRisco;
import CALISTO.model.persistence.util.Status;
import CALISTO.model.persistence.util.TipoConta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaInvestimentoDao extends ContaDao {

    public void save(Investimento conta) throws SQLException {
        String sqlConta = "INSERT INTO conta (numero_conta, agencia_id, saldo, tipo_conta, cliente_id) VALUES (?, ?, ?, 'INVESTIMENTO', ?)";
        String sqlInvestimento = "INSERT INTO conta_investimento (conta_id, perfil_risco, valor_minimo, taxa_rendimento) VALUES (?, ?, ?, ?)";
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
                    try (PreparedStatement stmtInvestimento = conn.prepareStatement(sqlInvestimento)) {
                        stmtInvestimento.setInt(1, contaId);
                        stmtInvestimento.setString(2, conta.getPerfilRisco().name());
                        stmtInvestimento.setBigDecimal(3, conta.getValorMinimo());
                        stmtInvestimento.setBigDecimal(4, conta.getTaxaRendimento());
                        stmtInvestimento.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean alterar(Investimento investimento) {
        String sqlConta = """
        UPDATE conta SET saldo = ?, status = ?, tipo_conta = ?, data_abertura = ?,
                         numero_conta = ?, agencia_id = ?, cliente_id = ?
        WHERE id_conta = ?
        """;

        String sqlInvestimento = """
        UPDATE conta_investimento SET perfil_risco = ?, valor_minimo = ?, taxa_rendimento = ?
        WHERE conta_id = ?
        """;

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta)) {
                stmtConta.setBigDecimal(1, investimento.getSaldo());
                stmtConta.setString(2, investimento.getStatus().name());
                stmtConta.setString(3, investimento.getTipoConta().name());
                stmtConta.setTimestamp(4, Timestamp.valueOf(investimento.getDataAbertura()));
                stmtConta.setString(5, investimento.getNumeroConta());
                stmtConta.setInt(6, investimento.getAgencia().getIdAgencia());
                stmtConta.setInt(7, investimento.getCliente().getIdCliente());
                stmtConta.setInt(8, investimento.getIdConta());
                stmtConta.executeUpdate();
            }

            try (PreparedStatement stmtInv = conn.prepareStatement(sqlInvestimento)) {
                stmtInv.setString(1, investimento.getPerfilRisco().name());
                stmtInv.setBigDecimal(2, investimento.getValorMinimo());
                stmtInv.setBigDecimal(3, investimento.getTaxaRendimento());
                stmtInv.setInt(4, investimento.getIdConta());
                stmtInv.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Investimento> buscarTodos() {
        List<Investimento> contas = new ArrayList<>();
        String sql = """
        SELECT
            ci.id_conta_investimento, ci.perfil_risco, ci.valor_minimo, ci.taxa_rendimento,
            c.id_conta, c.numero_conta, c.saldo, c.tipo_conta, c.status, c.data_abertura,
            cli.id_cliente, u.nome, u.cpf, u.data_nascimento, u.telefone,
            a.id_agencia, a.nome AS nome_agencia, a.codigo_agencia
        FROM conta_investimento ci
        INNER JOIN conta c ON ci.conta_id = c.id_conta
        INNER JOIN cliente cli ON c.cliente_id = cli.id_cliente
        INNER JOIN usuario u ON cli.usuario_id = u.id_usuario
        INNER JOIN agencia a ON c.agencia_id = a.id_agencia;
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Investimento investimento = new Investimento();
                investimento.setIdConta(rs.getInt("id_conta"));
                investimento.setNumeroConta(rs.getString("numero_conta"));
                investimento.setSaldo(rs.getBigDecimal("saldo"));
                investimento.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                investimento.setStatus(Status.valueOf(rs.getString("status")));
                investimento.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNome(rs.getString("nome_agencia"));
                agencia.setCodigoAgencia(rs.getString("codigo_agencia"));
                investimento.setAgencia(agencia);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setTelefone(rs.getString("telefone"));
                investimento.setCliente(cliente);

                investimento.setIdContaInvestimento(rs.getInt("id_conta_investimento"));
                investimento.setPerfilRisco(PerfilRisco.valueOf(rs.getString("perfil_risco")));
                investimento.setValorMinimo(rs.getBigDecimal("valor_minimo"));
                investimento.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));
                investimento.setConta(investimento);

                contas.add(investimento);
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

    public Investimento buscarPorConta(int contaId) {
        String sql = """
        SELECT
            ci.id_conta_investimento, ci.perfil_risco, ci.valor_minimo, ci.taxa_rendimento,
            c.id_conta, c.numero_conta, c.saldo, c.tipo_conta, c.status,
            c.data_abertura, c.agencia_id, c.cliente_id
        FROM conta_investimento ci
        INNER JOIN conta c ON ci.conta_id = c.id_conta
        WHERE ci.conta_id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Investimento investimento = new Investimento();
                investimento.setIdConta(rs.getInt("id_conta"));
                investimento.setNumeroConta(rs.getString("numero_conta"));
                investimento.setSaldo(rs.getBigDecimal("saldo"));
                investimento.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
                investimento.setStatus(Status.valueOf(rs.getString("status")));
                investimento.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("agencia_id"));
                investimento.setAgencia(agencia);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("cliente_id"));
                investimento.setCliente(cliente);

                investimento.setIdContaInvestimento(rs.getInt("id_conta_investimento"));
                investimento.setPerfilRisco(PerfilRisco.valueOf(rs.getString("perfil_risco")));
                investimento.setValorMinimo(rs.getBigDecimal("valor_minimo"));
                investimento.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));
                investimento.setConta(investimento);

                return investimento;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
