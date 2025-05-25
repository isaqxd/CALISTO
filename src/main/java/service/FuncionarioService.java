package service;

import model.funcionario.Cargo;
import model.funcionario.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioService {
    public static Funcionario mapFuncionario(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setUsuarioId(rs.getInt("usuario_id"));
        f.setCodigoFuncionario(rs.getString("codigo_funcionario"));

        String tipoFuncionario = rs.getString("cargo");
        if (tipoFuncionario != null) {
            try {
                f.setCargo(Cargo.valueOf(tipoFuncionario));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de funcionario invalido: " + tipoFuncionario);
            }
        }

        f.setSupervisor(rs.getInt("supervisor"));
        return f;
    }
}
