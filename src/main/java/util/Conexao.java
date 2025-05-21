package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/calisto";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con = null;

    public static Connection conexao() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Conexão efetuada com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro em se conectar ao banco.");
            e.printStackTrace();
        }
        return con;
    }
}
