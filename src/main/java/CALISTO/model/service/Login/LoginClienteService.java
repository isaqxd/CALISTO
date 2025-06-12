// SUBSTITUA O CONTEÚDO DO SEU ARQUIVO: LoginClienteService.java POR ESTE

package CALISTO.model.service.Login;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.LoginClienteDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Cliente;
import jakarta.servlet.http.HttpServletRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoginClienteService {
    protected static final String SALT = "X@mpl3S@lt2025!";

    /**
     * Valida as credenciais do cliente (CPF e Senha).
     * @return O objeto Cliente se as credenciais forem válidas, caso contrário, retorna null.
     */
    public Cliente validateLoginCredentials(HttpServletRequest request) throws SQLException {
        String cpf = request.getParameter("cpf").replaceAll("[^0-9]", "");
        String senha = request.getParameter("senha");
        String tipoUsuario = request.getParameter("tipo_usuario");

        LoginClienteDao dao = new LoginClienteDao();
        Cliente cliente = dao.findByCpf(cpf);

        if (cliente == null) {
            System.err.println("Tentativa de login com CPF não encontrado: " + cpf);
            return null; // Cliente não encontrado
        }

        String senhaHash = generateHashMD5(senha);

        // Verifica se a senha e o tipo de usuário estão corretos
        if (cliente.getSenhaHash().equals(senhaHash) && tipoUsuario.equals(cliente.getTipoUsuario().toString())) {
            // Credenciais corretas, gera e salva um novo OTP
            generateOTP(cliente);
            dao.updateOtp(cliente);
            // Registra a auditoria
            saveAudit(cliente, "Credenciais válidas. Novo OTP gerado.", "CPF: " + cpf);
            return cliente; // Retorna o objeto cliente em caso de sucesso
        } else {
            // Senha ou tipo de usuário incorretos
            saveAudit(cliente, "Tentativa de login mal-sucedida", "CPF: " + cpf + ", Senha incorreta.");
            return null; // Falha na validação
        }
    }

    /**
     * Valida o OTP fornecido pelo usuário.
     * @return true se o OTP for válido e não estiver expirado, false caso contrário.
     */
    public boolean validateOTP(String cpf, String otpSubmetido) throws SQLException {
        LoginClienteDao dao = new LoginClienteDao();
        Cliente cliente = dao.findByCpf(cpf.replaceAll("[^0-9]", ""));

        if (cliente == null || cliente.getOtpAtivo() == null || cliente.getOtpExpiracao() == null) {
            return false; // Não há OTP ativo para este cliente
        }

        // Verifica se o OTP não expirou E se o código está correto
        boolean isOtpValid = LocalDateTime.now().isBefore(cliente.getOtpExpiracao()) &&
                cliente.getOtpAtivo().equals(otpSubmetido);

        if(isOtpValid) {
            saveAudit(cliente, "Login concluído com sucesso (OTP Válido)", "CPF: " + cpf);
        } else {
            saveAudit(cliente, "Tentativa de login com OTP inválido", "CPF: " + cpf);
        }

        return isOtpValid;
    }

    // Método auxiliar para simplificar a criação da auditoria
    private void saveAudit(Cliente cliente, String acao, String detalhes) throws SQLException {
        AuditoriaDao auditoriaDao = new AuditoriaDao();
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(cliente);
        auditoria.setAcao(acao);
        auditoria.setDetalhes(detalhes);
        auditoria.setDataHora(LocalDateTime.now());
        auditoriaDao.save(auditoria);
    }

    protected void generateOTP(Cliente cliente) {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        cliente.setOtpAtivo(String.valueOf(otp));
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