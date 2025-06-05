//package CALISTO.controller;
//
//import CALISTO.model.dao.ClienteDao1;
//import CALISTO.model.persistence.Endereco.Endereco;
//import CALISTO.model.persistence.Usuario.Cliente;
//import CALISTO.model.persistence.util.TipoUsuario;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//
//@WebServlet("/cliente")
//public class ClienteController extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Pega os dados do formulário
//        String nome = request.getParameter("nome");
//        String cpf = request.getParameter("cpf");
//        String senha = request.getParameter("senha");
//        String telefone = request.getParameter("telefone");
//        String cep = request.getParameter("cep");
//        String local = request.getParameter("local");
//        String numeroCasa = request.getParameter("numeroCasa");
//        String bairro = request.getParameter("bairro");
//        String cidade = request.getParameter("cidade");
//        String estado = request.getParameter("estado");
//        String complemento = request.getParameter("complemento");
//
//        // Monta os objetos
//        Endereco endereco = new Endereco(cep, local, numeroCasa, bairro, cidade, estado, complemento);
//
//        Cliente cliente = new Cliente();
//        cliente.setNome(nome);
//        cliente.setCpf(cpf);
//        cliente.setSenhaHash(senha); // aqui você pode gerar o hash se desejar já
//        cliente.setTelefone(telefone);
//        cliente.setEndereco(endereco);
//        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
//
//
//        // Salvar no banco
//        ClienteDao1 clienteDao = new ClienteDao1();
//        Cliente clienteSalvo = clienteDao.save(cliente);
//
//        if (clienteSalvo != null) {
//            response.sendRedirect("sucesso.jsp");
//        } else {
//            response.sendRedirect("erro.jsp");
//        }
//    }
//
//
//}
