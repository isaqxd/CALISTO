package CALISTO.model.persistence.Test;

import CALISTO.model.dao.AgenciaDao;
import CALISTO.model.persistence.Agencia.Agencia;
import CALISTO.model.persistence.Endereco.Endereco;

public class AgenciaTest {
    public static void main(String[] args) {
        Endereco endereco = new Endereco();
        endereco.setCep("72000000");
        endereco.setLocal("Rua 1");
        endereco.setNumeroCasa(1);
        endereco.setBairro("Brasília");
        endereco.setCidade("Brasília");
        endereco.setEstado("DF");
        endereco.setComplemento("Banco Calisto");

        Agencia agencia = new Agencia();
        agencia.setNome("Agencia Calisto");
        agencia.setCodigoAgencia("000000001");
        agencia.setEndereco(endereco);

        AgenciaDao agDao = new AgenciaDao();
        agDao.save(agencia);
    }
}
