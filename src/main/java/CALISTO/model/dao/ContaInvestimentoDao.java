//package CALISTO.model.dao;
//
//import CALISTO.model.persistence.Conta.Investimento;
//import CALISTO.model.persistence.util.Conexao;
//
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//class ContaInvestimentoDao extends ContaDao {
//
//    public boolean cadastrar(Investimento conta) {
//        String sql = "INSERT INTO conta_investimento (conta_id, taxa_administracao) VALUES (?, ?)";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, conta.getContaId());
//            stmt.setDouble(2, conta.getTaxaAdministracao());
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean alterar(Investimento conta) {
//        String sql = "UPDATE conta_investimento SET taxa_administracao = ? WHERE conta_id = ?";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setDouble(1, conta.getTaxaAdministracao());
//            stmt.setInt(2, conta.getContaId());
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public List<Investimento> buscarTodos() {
//        List<Investimento> contas = new ArrayList<>();
//        String sql = "SELECT * FROM conta_investimento";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Investimento conta = new Investimento();
//                conta.setContaId(rs.getInt("conta_id"));
//                conta.setTaxaAdministracao(rs.getDouble("taxa_administracao"));
//                contas.add(conta);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return contas;
//    }
//
//    public boolean alterarStatus(int contaId, boolean status) {
//        String sql = "UPDATE conta SET status = ? WHERE id_conta = ?";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setBoolean(1, status);
//            stmt.setInt(2, contaId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public Investimento buscarPorConta(int contaId) {
//        String sql = "SELECT * FROM conta_investimento WHERE conta_id = ?";
//        try (Connection conn = Conexao.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, contaId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Investimento conta = new Investimento();
//                conta.setContaId(rs.getInt("conta_id"));
//                conta.set(rs.getBigDecimal("taxa_administracao"));
//                return conta;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
//
