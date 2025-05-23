package service;

import model.cliente.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteService {
    public static Cliente mapCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setUsuarioId(rs.getInt("usuario_id"));
        c.setScoreCredito(rs.getBigDecimal("score_credito"));
        return c;
    }
}