package CALISTO.model.persistence.Test;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.service.UsuarioService.ClienteService;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImportadorClientes {
    public static void importarClientes(String caminho) {
        try {
            Gson gson = new Gson();
            JsonArray clientes = JsonParser.parseReader(new FileReader(caminho)).getAsJsonArray();

            for (JsonElement elem : clientes) {
                JsonObject obj = elem.getAsJsonObject();

                // Endereço
                JsonObject enderecoJson = obj.getAsJsonObject("endereco");
                Endereco endereco = new Endereco();
                endereco.setCep(enderecoJson.get("cep").getAsString());
                endereco.setLocal(enderecoJson.get("local").getAsString());
                endereco.setNumeroCasa(enderecoJson.get("numeroCasa").getAsInt());
                endereco.setBairro(enderecoJson.get("bairro").getAsString());
                endereco.setCidade(enderecoJson.get("cidade").getAsString());
                endereco.setEstado(enderecoJson.get("estado").getAsString());
                endereco.setComplemento(enderecoJson.get("complemento").getAsString());

                // Usuario
                Cliente cliente = new Cliente();
                cliente.setNome(obj.get("nome").getAsString());
                cliente.setCpf(obj.get("cpf").getAsString());
                cliente.setDataNascimento(LocalDate.parse(obj.get("dataNascimento").getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cliente.setTelefone(obj.get("telefone").getAsString());
                cliente.setSenhaHash(obj.get("senhaHash").getAsString());
                cliente.setTipoUsuario(TipoUsuario.valueOf(obj.get("tipo_usuario").getAsString()));
                cliente.setOtpAtivo(null); // ou um valor padrão
                cliente.setOtpExpiracao(null); // ou um valor padrão



                // Cliente
                cliente.setEndereco(endereco);
                cliente.setUsuario(cliente);
                cliente.setScoreCredito(BigDecimal.valueOf(0));

                // Salvar no banco
                ClienteDao clienteDao = new ClienteDao();
                ClienteService clienteService = new ClienteService(clienteDao);
                clienteService.verifyUsuario(cliente);
            }
            System.out.println("Clientes importados: " + clientes.size() + " com sucesso!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
