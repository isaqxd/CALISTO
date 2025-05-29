package calisto.dao;

import calisto.model.agencia.Agencia;
import calisto.model.cliente.Cliente;
import calisto.model.conta.Conta;
import calisto.model.conta.Status;
import calisto.model.conta.TipoConta;
import calisto.util.Conexao;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class ContaDao {
    public void preencherPreparedStatement(@NotNull PreparedStatement ps, @NotNull Conta conta) throws SQLException {
        ps.setString(1, conta.getNumeroConta());
        ps.setInt(2, conta.getAgencia().getIdAgencia());
        ps.setBigDecimal(3, conta.getSaldo());
        ps.setString(4, conta.getTipoConta().name());
        ps.setInt(5, conta.getCliente().getIdCliente());
        ps.setTimestamp(6, Timestamp.valueOf(conta.getDataAbertura()));
        ps.setString(7, conta.getStatus().name());
    }

    public Conta montarConta(@NotNull ResultSet rs) throws SQLException {
        return new Conta()
                .setIdConta(rs.getInt("id_conta"))
                .setNumeroConta(rs.getString("numero_conta"))
                .setAgencia(new Agencia().setIdAgencia(rs.getInt("agencia_id")))
                .setSaldo(rs.getBigDecimal("saldo"))
                .setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")))
                .setCliente(new Cliente().setIdCliente(rs.getInt("cliente_id")))
                .setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime())
                .setStatus(Status.valueOf(rs.getString("status")));
    }

    public int insert(Conta conta) {
        String sql = """
                INSERT INTO conta (numero_conta, agencia_id, saldo, tipo_conta, cliente_id, data_abertura, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = Conexao.conexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherPreparedStatement(ps, conta);

            int insert = ps.executeUpdate();
            if (insert > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            throw new RuntimeException("Falha ao criar conta: nenhuma linha foi afetada");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar conta.", e);
        }
    }
}
