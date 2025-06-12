package CALISTO.model.dao;

import CALISTO.model.mapper.UsuarioMapper;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.Conexao;
import CALISTO.model.persistence.util.TipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static CALISTO.model.mapper.UsuarioMapper.close;
import static CALISTO.model.mapper.UsuarioMapper.rollBack;


public class AgenciaDao {

    public Agencia save(Agencia agencia) {
        //language=sql
        String sqlEndereco = """
                    INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado, complemento)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        String sqlAgencia = """
                    INSERT INTO agencia (nome, codigo_agencia, endereco_id)
                    VALUES (?, ?, ?)
                """;

        Connection con = null;
        try {
            con = Conexao.getConnection();
            con.setAutoCommit(false);

            int enderecoId = inserirEnderecoAgencia(con, agencia, sqlEndereco);
            agencia.getEndereco().setIdEndereco(enderecoId);

            try (PreparedStatement stmt = con.prepareStatement(sqlAgencia)) {
                stmt.setString(1, agencia.getNome());
                stmt.setString(2, agencia.getCodigoAgencia());
                stmt.setInt(3, enderecoId);
                stmt.executeUpdate();

                con.commit();
                return agencia;
            }
        } catch (SQLException e) {
            rollBack(con);
            throw new RuntimeException("Erro ao salvar Agencia: " + e.getMessage(), e);
        } finally {
            close(con);
        }
    }

    public Agencia update(Agencia agencia) {
        String sqlEndereco = """
                    UPDATE endereco SET cep = ?, local = ?, numero_casa = ?, bairro = ?, cidade = ?, estado = ?, complemento = ?\s
                    WHERE id_endereco = ?
                \s""";

        String sqlAgencia = """
                    UPDATE agencia SET nome = ?, codigo_agencia = ?, endereco_id = ?
                    WHERE id_agencia = ?
                """;

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar endereço
            try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {
                Endereco e = agencia.getEndereco();
                stmtEndereco.setString(1, e.getCep());
                stmtEndereco.setString(2, e.getLocal());
                stmtEndereco.setInt(3, e.getNumeroCasa());
                stmtEndereco.setString(4, e.getBairro());
                stmtEndereco.setString(5, e.getCidade());
                stmtEndereco.setString(6, e.getEstado());
                stmtEndereco.setString(7, e.getComplemento());
                stmtEndereco.setInt(8, e.getIdEndereco());
                stmtEndereco.executeUpdate();
            }

            // Atualizar a agência
            try (PreparedStatement stmtAgencia = conn.prepareStatement(sqlAgencia)) {
                stmtAgencia.setString(1, agencia.getNome());
                stmtAgencia.setString(2, agencia.getCodigoAgencia());
                stmtAgencia.setInt(3, agencia.getEndereco().getIdEndereco());
                stmtAgencia.setInt(4, agencia.getIdAgencia());
                stmtAgencia.executeUpdate();
            }

            conn.commit();
            return agencia;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Agencia> findAll() {
        String sql = """
                SELECT a.id_agencia,a.nome, a.codigo_agencia,
                       e.id_endereco,e.cep,e.local,e.numero_casa,e.bairro,e.cidade,e.estado,e.complemento
                       FROM agencia a
                       JOIN endereco e ON a.endereco_id = e.id_endereco
                       """;
        List<Agencia> agencias = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Endereco endereco = UsuarioMapper.fillEnderecoFromResultSet(rs);

                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNome(rs.getString("nome"));
                agencia.setCodigoAgencia(rs.getString("codigo_agencia"));
                agencia.setEndereco(endereco);

                agencias.add(agencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agencias;
    }

    public Agencia findByCode(int code) {
        String sql = """
                SELECT a.id_agencia,a.nome, a.codigo_agencia,
                       e.id_endereco,e.cep,e.local,e.numero_casa,e.bairro,e.cidade,e.estado,e.complemento
                       FROM agencia a
                       JOIN endereco e ON a.endereco_id = e.id_endereco
                       WHERE a.codigo_agencia = ?
                       """;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Endereco endereco = UsuarioMapper.fillEnderecoFromResultSet(rs);
                    Agencia agencia = new Agencia();
                    agencia.setIdAgencia(rs.getInt("id_agencia"));
                    agencia.setNome(rs.getString("nome"));
                    agencia.setCodigoAgencia(rs.getString("codigo_agencia"));
                    agencia.setEndereco(endereco);

                    return agencia;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //FUNÇÔES AUXILIARES
    public static int inserirEnderecoAgencia(Connection conn, Agencia agencia, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            UsuarioMapper.fillPreparedStatementEndereco(stmt, agencia.getEndereco());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Falha ao obter o ID do endereço.");
            }
        }
    }

}
