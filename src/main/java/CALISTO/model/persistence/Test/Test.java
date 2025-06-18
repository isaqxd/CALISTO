package CALISTO.model.persistence.Test;


public class Test {
    public static void main(String[] args) {
        String caminhoF = "D:\\CALISTO\\src\\main\\util\\funcionarios.json";
        String caminhoC = "D:\\CALISTO\\src\\main\\util\\cliente.json";
        ImportadorFuncionarios.importarFuncionarios(caminhoF);
        ImportadorClientes.importarClientes(caminhoC);
    }
}