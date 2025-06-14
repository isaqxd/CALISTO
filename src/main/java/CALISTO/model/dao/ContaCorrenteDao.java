package CALISTO.model.dao;

import CALISTO.model.persistence.Conta.Corrente;
import CALISTO.model.persistence.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ContaCorrenteDao extends ContaDao {

    public boolean cadastrar(Corrente conta) {
        String sql = "INSERT INTO conta_corrente (conta_id, limite) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, conta.getIdContaCorrente());
            stmt.setBigDecimal(2, conta.getLimite());
            return stmt.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(Corrente conta) {
        String sql = "UPDATE conta_corrente SET limite = ? WHERE conta_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, conta.getLimite());
            stmt.setInt(2, conta.getIdContaCorrente());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Corrente> buscarTodos() {
        List<Corrente> contas = new ArrayList<>();
        String sql = "SELECT * FROM conta_corrente";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Corrente conta = new Corrente();
                conta.setIdContaCorrente(rs.getInt("conta_id"));
                conta.setLimite(rs.getBigDecimal("limite"));
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

    public Corrente buscarPorConta(int contaId) {
        String sql = "SELECT * FROM conta_corrente WHERE conta_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Corrente conta = new Corrente();
                conta.setIdContaCorrente(rs.getInt("conta_id"));
                conta.setLimite(rs.getBigDecimal("limite"));
                return conta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
