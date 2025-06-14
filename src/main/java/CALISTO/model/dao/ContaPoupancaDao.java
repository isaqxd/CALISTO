package CALISTO.model.dao;


import CALISTO.model.persistence.Conta.Poupanca;
import CALISTO.model.persistence.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ContaPoupancaDao extends ContaDao {

    public void cadastrar(Poupanca conta) throws SQLException {
        String sqlConta = "INSERT INTO conta (numero_conta, agencia_id, saldo, tipo_conta, cliente_id) VALUES (?, ?, ?, 'POUPANCA', ?)";
        String sqlPoupanca = "INSERT INTO conta_poupanca (conta_id, taxa_rendimento, ultimo_rendimento) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta, Statement.RETURN_GENERATED_KEYS)) {
                stmtConta.setString(1, conta.getNumeroConta());
                stmtConta.setInt(2, conta.getAgenciaId());
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


//    public boolean cadastrar(Poupanca conta) {
//
//
//        String sql = "INSERT INTO conta_poupanca (conta_id, taxa_rendimento) VALUES (?, ?)";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, conta.getIdContaPoupanca());
//            stmt.setBigDecimal(2, conta.getTaxaRendimento());
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean alterar(Poupanca conta) {
        String sql = "UPDATE conta_poupanca SET taxa_rendimento = ? WHERE conta_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, conta.getTaxaRendimento());
            stmt.setInt(2, conta.getIdContaPoupanca());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Poupanca> buscarTodos() {
        List<Poupanca> contas = new ArrayList<>();
        String sql = "SELECT * FROM conta_poupanca";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Poupanca conta = new Poupanca();
                conta.setIdContaPoupanca(rs.getInt("conta_id"));
                conta.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));
                contas.add(conta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contas;
    }

    public boolean alterarStatus(int contaId, boolean status) {
        String sql = "UPDATE conta SET status = ? WHERE id_conta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, contaId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Poupanca buscarPorConta(int contaId) {
        String sql = "SELECT * FROM conta_poupanca WHERE conta_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Poupanca conta = new Poupanca();
                conta.setIdContaPoupanca(rs.getInt("conta_id"));
                conta.setTaxaRendimento(rs.getBigDecimal("taxa_rendimento"));
                return conta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}