package calisto.service;

import calisto.model.conta.investimento.Investimento;
import calisto.model.conta.investimento.PerfilRisco;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaInvestimentoService {
    public static Investimento mapCI(ResultSet rs) throws SQLException {
        Investimento ci = new Investimento();
        ci.setIdContaInvestimento(rs.getInt("id_conta_investimento"));
        ci.setContaId(rs.getInt("conta_id"));

        String perfilRisco = rs.getString("perfil_risco");
        if(perfilRisco != null){
            try {
                ci.setPerfilRisco(PerfilRisco.valueOf(perfilRisco));
            } catch (IllegalArgumentException e) {
                System.out.println("Perfil de risco invalido: " + perfilRisco);
            }
        }

        ci.setValorMinimo(rs.getBigDecimal("valor_minimo"));
        ci.setTaxaRendimentoBase(rs.getBigDecimal("taxa_rendimento_base"));
        return ci;
    }
}