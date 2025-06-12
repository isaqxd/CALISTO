package CALISTO.model.service.Login;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.LoginFuncionarioDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Funcionario;
import jakarta.servlet.http.HttpServletRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoginFuncionarioService {
    protected static final String SALT = "X@mpl3S@lt2025!";

    /**
     * Valida as credenciais do funcionário (Código e Senha).
     * @return O objeto Funcionario se as credenciais forem válidas, caso contrário, retorna null.
     */
    public Funcionario validateLoginCredentials(HttpServletRequest request) throws SQLException {
        String codigo = request.getParameter("cpf").replaceAll("[^A-Za-z0-9]", "");
        String senha = request.getParameter("senha");
        String tipoUsuario = request.getParameter("tipo_usuario");

        LoginFuncionarioDao dao = new LoginFuncionarioDao();
        Funcionario funcionario = dao.findByCodigo(codigo);

        if (funcionario == null) {
            System.err.println("Tentativa de login com código não encontrado: " + codigo);
            return null; // Funcionário não encontrado
        }

        String senhaHash = generateHashMD5(senha);

        // Verifica se a senha e o tipo de usuário estão corretos
        if (funcionario.getSenhaHash().equals(senhaHash) && tipoUsuario.equals(funcionario.getTipoUsuario().toString())) {
            // Credenciais corretas, gera e salva um novo OTP
            generateOTP(funcionario);
            dao.updateOtp(funcionario);
            // Imprime o OTP no console para facilitar o teste
            System.out.println("======> [SERVICE] OTP gerado para o funcionário " + codigo + ": " + funcionario.getOtpAtivo());
            // Registra a auditoria
            saveAudit(funcionario, "Credenciais válidas. Novo OTP gerado.", "Código: " + codigo);
            return funcionario; // Retorna o objeto funcionario em caso de sucesso
        } else {
            // Senha ou tipo de usuário incorretos
            saveAudit(funcionario, "Tentativa de login mal-sucedida", "Código: " + codigo + ", Senha incorreta.");
            return null; // Falha na validação
        }
    }

    /**
     * Valida o OTP fornecido pelo usuário.
     * @return true se o OTP for válido e não estiver expirado, false caso contrário.
     */
    public boolean validateOTP(String codigo, String otpSubmetido) throws SQLException {
        LoginFuncionarioDao dao = new LoginFuncionarioDao();
        Funcionario funcionario = dao.findByCodigo(codigo.replaceAll("[^A-Za-z0-9]", ""));

        if (funcionario == null || funcionario.getOtpAtivo() == null || funcionario.getOtpExpiracao() == null) {
            return false; // Não há OTP ativo para este funcionário
        }

        // Verifica se o OTP não expirou E se o código está correto
        boolean isOtpValid = LocalDateTime.now().isBefore(funcionario.getOtpExpiracao()) &&
                funcionario.getOtpAtivo().equals(otpSubmetido);

        if(isOtpValid) {
            saveAudit(funcionario, "Login concluído com sucesso (OTP Válido)", "Código: " + codigo);
        } else {
            saveAudit(funcionario, "Tentativa de login com OTP inválido", "Código: " + codigo);
        }

        return isOtpValid;
    }

    // Método auxiliar para simplificar a criação da auditoria
    private void saveAudit(Funcionario funcionario, String acao, String detalhes) throws SQLException {
        AuditoriaDao auditoriaDao = new AuditoriaDao();
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(funcionario);
        auditoria.setAcao(acao);
        auditoria.setDetalhes(detalhes);
        auditoria.setDataHora(LocalDateTime.now());
        auditoriaDao.save(auditoria);
    }

    protected void generateOTP(Funcionario funcionario) {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        funcionario.setOtpAtivo(String.valueOf(otp));
        funcionario.setOtpExpiracao(LocalDateTime.now().plusMinutes(5));
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
