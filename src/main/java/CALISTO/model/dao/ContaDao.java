package CALISTO.model.dao;


import CALISTO.model.persistence.Conta.Conta;
import CALISTO.model.persistence.util.Conexao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContaDao {

    public boolean depositar(int idConta, BigDecimal valor) {
        String sql = "UPDATE conta SET saldo = saldo + ? WHERE id_conta = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, valor);
            stmt.setInt(2, idConta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sacar(int idConta, BigDecimal valor) {
        String sql = "UPDATE conta SET saldo = saldo - ? WHERE id_conta = ? AND saldo >= ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, valor);
            stmt.setInt(2, idConta);
            stmt.setBigDecimal(3, valor);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean transferir(int origemId, int destinoId, BigDecimal valor) {
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

    public Conta buscarPorId(int idConta) {
        String sql = "SELECT * FROM conta WHERE id_conta = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo_conta");

                switch (tipo) {
                    case "CORRENTE":
                        return new ContaCorrenteDao().buscarPorConta(idConta);
                    case "POUPANCA":
                        return new ContaPoupancaDao().buscarPorConta(idConta);
                    case "INVESTIMENTO":
                        return new ContaInvestimentoDao().buscarPorConta(idConta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
