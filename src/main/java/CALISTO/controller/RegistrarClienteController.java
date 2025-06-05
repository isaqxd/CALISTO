package CALISTO.controller;

import CALISTO.model.dao.ClienteDao;
import CALISTO.model.persistence.Endereco.Endereco;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.util.TipoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/registrarCliente")
public class RegistrarClienteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Pegando parâmetros do formulário
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String dataNascimentoStr = request.getParameter("data_nascimento");
            String telefone = request.getParameter("telefone");
            String senha = request.getParameter("senha");
            String cep = request.getParameter("cep");
            String local = request.getParameter("local");
            String numeroCasa = request.getParameter("numeroCasa");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String complemento = request.getParameter("complemento");

            // Convertendo data
            LocalDate dataNascimento = null;
            if (dataNascimentoStr != null && !dataNascimentoStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
            }

            // Criando objetos
            Endereco endereco = new Endereco(cep, local, numeroCasa, bairro, cidade, estado, complemento);
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setDataNascimento(dataNascimento);
            cliente.setTelefone(telefone);
            cliente.setSenhaHash(senha);
            cliente.setEndereco(endereco);
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);

            // Salvando no banco
            ClienteDao clienteDao = new ClienteDao();
            Cliente clienteSalvo = clienteDao.save(cliente);

            if (clienteSalvo != null) {
                response.sendRedirect("sucesso.jsp");
            } else {
                response.sendRedirect("erro.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp");
        }
    }
}