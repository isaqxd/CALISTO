package CALISTO.model.service.Login;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.LoginClienteDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoginClienteService {
    protected static final String SALT = "X@mpl3S@lt2025!";

    public boolean validateLoginCliente(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String cpf = request.getParameter("cpf");
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos do CPF
        String senha = request.getParameter("senha");
        String tipoUsuario = request.getParameter("tipo_usuario");

        String senhaHash = generateHashMD5(senha);

        LoginClienteDao dao = new LoginClienteDao();
        Cliente cliente = dao.findByCpf(cpf);

        // COMEÇANDO A AUDITORIA
        AuditoriaDao auditoriaDao = new AuditoriaDao();
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(cliente);

        if (cliente.getSenhaHash().equals(senhaHash) && tipoUsuario.equals(cliente.getTipoUsuario().toString())) {
            // Se o login for bem-sucedido, registrar a tentativa de login na auditoria
            auditoria.setDetalhes("CPF: " + cpf + ", Tipo de usuário: " + tipoUsuario);
            auditoria.setDataHora(LocalDateTime.now());

            LocalDateTime agora = LocalDateTime.now();
            if (cliente.getOtpAtivo() == null || cliente.getOtpExpiracao() == null || !agora.isBefore(cliente.getOtpExpiracao())) {
                generateOTP(cliente);
                // Se a OTP não estiver ativo ou expirou, gerar relatorio de auditoria
                auditoria.setAcao("Tentativa de login com OTP gerado");
                auditoriaDao.save(auditoria);
                dao.updateOtp(cliente);
            }
            return true;
        } else {
            // Se o login falhar, registrar a tentativa de login na auditoria
            auditoria.setAcao("Tentativa de login mal-sucedida");
            auditoria.setDetalhes("CPF: " + cpf + ", Tipo de usuário: " + tipoUsuario + ", Senha incorreta ou tipo de usuário inválido");
            auditoria.setDataHora(LocalDateTime.now());
            // Salvar auditoria no banco de dados
            auditoriaDao.save(auditoria);
            return false;
        }
    }

    /**
     * Gera um OTP (One-Time Password) para o usuário.
     *
     * @param cliente usuário para o qual o OTP será gerado
     */
    protected void generateOTP(Cliente cliente) {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        cliente.setOtpAtivo(String.valueOf(otp));

        // Definir expiração para 5 minutos a partir de agora
        cliente.setOtpExpiracao(LocalDateTime.now().plusMinutes(5));
    }

    private String generateHashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String senhaComSalt = input + SALT;

            byte[] hash = md.digest(senhaComSalt.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash MD5: " + e.getMessage(), e);
        }
    }
}
