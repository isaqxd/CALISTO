package CALISTO.model.persistence.Test;

import CALISTO.model.dao.FuncionarioDao;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.persistence.Usuario.Usuario;
import CALISTO.model.persistence.util.Cargo;
import CALISTO.model.persistence.util.TipoUsuario;
import CALISTO.model.service.UsuarioService.FuncionarioService;
import com.google.gson.*;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImportadorFuncionarios {

    public static void importarFuncionarios(String caminho) {
        try {
            Gson gson = new Gson();
            JsonArray funcionarios = JsonParser.parseReader(new FileReader(caminho)).getAsJsonArray();

            for (JsonElement elem : funcionarios) {
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
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(obj.get("nome").getAsString());
                funcionario.setCpf(obj.get("cpf").getAsString());
                funcionario.setDataNascimento(LocalDate.parse(obj.get("dataNascimento").getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                funcionario.setTelefone(obj.get("telefone").getAsString());
                funcionario.setSenhaHash(obj.get("senhaHash").getAsString());
                funcionario.setTipoUsuario(TipoUsuario.valueOf(obj.get("tipo_usuario").getAsString()));
                funcionario.setOtpAtivo(null); // ou um valor padrão
                funcionario.setOtpExpiracao(null); // ou um valor padrão

                // Funcionario


                funcionario.setEndereco(endereco);
                funcionario.setUsuario(funcionario);
                funcionario.setCargo(Cargo.valueOf(obj.get("cargo").getAsString()));
                if (!obj.has("supervisor") || obj.get("supervisor").getAsInt() == 0) {
                    funcionario.setSupervisor(0); // ou 0 se seu banco aceitar como opcional
                } else {
                    funcionario.setSupervisor(obj.get("supervisor").getAsInt());
                }
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("CPF: " + funcionario.getCpf());

                // Salvar no banco
                FuncionarioDao funcDao = new FuncionarioDao();
                FuncionarioService funService = new FuncionarioService(funcDao);
                funService.verificarFuncionario(funcionario);
            }

            System.out.println("Funcionários importados: " + funcionarios.size() +" com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
