package CALISTO.model.service.Login;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.LoginClienteDao;
import CALISTO.model.dao.LoginFuncionarioDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Cliente;
import CALISTO.model.persistence.Usuario.Funcionario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoginFuncionarioService {
    protected static final String SALT = "X@mpl3S@lt2025!";
    protected static final LocalDateTime HORA_ATUAL = LocalDateTime.now();

    public boolean validateCpfSenhaForFuncionario(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        String cpf = request.getParameter("cpf").replaceAll("[^0-9]", "");
        session.setAttribute("cpfLogin", cpf);

        String senha = request.getParameter("senha");
        String senhaHash = generateHashMD5(senha);

        String tipoUsuario = request.getParameter("tipo_usuario");

        AuditoriaDao auditoriaDao = new AuditoriaDao();
        LoginFuncionarioDao dao = new LoginFuncionarioDao();
        Funcionario funcionario = dao.findByCpf(cpf);

        Auditoria a = new Auditoria();
        // BLOQUEIA MULTIPLAS TENTATIVAS DE LOGIN
        if (funcionario != null && auditoriaDao.blockLoginFromAuditoria(funcionario.getIdUsuario())) {
            a.setAcao("LOGIN_BLOQUEADO");
            a.setDataHora(HORA_ATUAL);
            a.setDetalhes("Login bloqueado para o FUNCIONARIO com CPF: " + cpf);
            a.setUsuario(funcionario);
            auditoriaDao.save(a);

            return false;
        }

        // CPF ou senha inválidos
        if (funcionario == null || !tipoUsuario.equals(funcionario.getTipoUsuario().toString()) || !funcionario.getSenhaHash().equals(senhaHash)) {
            a.setAcao("LOGIN_FALHA");
            a.setDetalhes("Tentativa de login com CPF: " + cpf);
            a.setDataHora(LocalDateTime.now());
            if (funcionario != null) a.setUsuario(funcionario);
            auditoriaDao.save(a);
            return false;
        }

        // OTP ainda válida (verifica se expiracao não é nula)
        if (funcionario.getOtpAtivo() != null && !funcionario.getOtpAtivo().isEmpty() && funcionario.getOtpExpiracao() != null && HORA_ATUAL.isBefore(funcionario.getOtpExpiracao())) {
            a.setAcao("OTP_PEDENTE");
            a.setDataHora(LocalDateTime.now());
            a.setDetalhes("OTP ainda válida para o FUNCIONARIO com CPF: " + cpf);
            a.setUsuario(funcionario);
            auditoriaDao.save(a);
            return true;
        }

        // Geração de nova OTP (verifica se expiracao é nula)
        if (funcionario.getOtpAtivo() == null || funcionario.getOtpAtivo().isEmpty() || funcionario.getOtpExpiracao() == null || HORA_ATUAL.isAfter(funcionario.getOtpExpiracao())) {
            a.setAcao("OTP_GERADA");
            a.setDataHora(LocalDateTime.now());
            a.setDetalhes("OTP gerada para o FUNCIONARIO com CPF: " + cpf);
            a.setUsuario(funcionario);
            auditoriaDao.save(a);

            generateOTP(funcionario);
            dao.updateOtp(funcionario);
        }
        return true;
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
