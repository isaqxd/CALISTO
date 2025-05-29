package calisto.dao;

import calisto.model.funcionario.Funcionario;
import calisto.util.Conexao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuncionarioDao {

    public void criarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (usuario_id, codigo_funcionario, cargo, supervisor) VALUES (?,?,?,?)";

        try (Connection con = Conexao.conexao()) {
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, funcionario.getUsuario().getIdUsuario());
            st.setString(2, funcionario.getCodigoFuncionario());
            st.setString(3, funcionario.getCargo().name());
            st.setInt(4, funcionario.getSupervisor());

            int insert = st.executeUpdate();

            if (insert > 0) {
                System.out.println("Funcionário inserido com sucesso!");
            } else {
                System.out.println("Erro ao inserir funcionário.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar funcionário.", e);
        }
    }
}
