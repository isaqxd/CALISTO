package calisto.service;

import calisto.model.agencia.Agencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgenciaService {
    public static Agencia mapAgencia(ResultSet rs) throws SQLException {
        Agencia a = new Agencia();
        a.setIdAgencia(rs.getInt("id_agencia"));
        a.setNome(rs.getString("nome"));
        a.setCodigoAgencia(rs.getString("codigo_agencia"));
        a.setEnderecoId(rs.getString("endereco_id"));
        return a;
    }
}
