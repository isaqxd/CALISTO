package CALISTO.model.dao;


import CALISTO.model.persistence.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ContaDao {

    public boolean depositar(int idConta, double valor) {
        String sql = "UPDATE conta SET saldo = saldo + ? WHERE id_conta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, valor);
            stmt.setInt(2, idConta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sacar(int idConta, double valor) {
        String sql = "UPDATE conta SET saldo = saldo - ? WHERE id_conta = ? AND saldo >= ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, valor);
            stmt.setInt(2, idConta);
            stmt.setDouble(3, valor);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean transferir(int origemId, int destinoId, double valor) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false);

            if (!sacar(origemId, valor)) {
                conn.rollback();
                return false;
            }

            if (!depositar(destinoId, valor)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public double extrato(int idConta) {
        String sql = "SELECT saldo FROM conta WHERE id_conta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
