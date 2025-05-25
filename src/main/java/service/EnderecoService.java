package service;

import model.endereco.Endereco;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoService {
    public static Endereco mapEndereco(ResultSet rs) throws SQLException {
        Endereco e = new Endereco();
        e.setIdEndereco(rs.getInt("id_endereco"));
        e.setUsuarioId(rs.getInt("usuario_id"));
        e.setCep(rs.getString("cep"));
        e.setLocal(rs.getString("local"));
        e.setNumeroCasa(rs.getInt("numero_casa"));
        e.setBairro(rs.getString("bairro"));
        e.setCidade(rs.getString("cidade"));
        e.setEstado(rs.getString("estado").charAt(0));
        e.setComplemento(rs.getString("complemento"));
        return e;
    }
}
