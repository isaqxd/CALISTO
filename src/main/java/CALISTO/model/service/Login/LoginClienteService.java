// SUBSTITUA O CONTEÚDO DO SEU ARQUIVO: LoginClienteService.java POR ESTE

package CALISTO.model.service.Login;

import CALISTO.model.dao.AuditoriaDao;
import CALISTO.model.dao.LoginClienteDao;
import CALISTO.model.persistence.Auditoria.Auditoria;
import CALISTO.model.persistence.Usuario.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoginClienteService {
    protected static final String SALT = "X@mpl3S@lt2025!";
    protected static final LocalDateTime HORA_ATUAL = LocalDateTime.now();

    /**
     * Valida as credenciais do cliente (CPF e Senha).
     * @return O objeto Cliente se as credenciais forem válidas, caso contrário, retorna null.
     */
    public boolean validateCpfSenha(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        String cpf = request.getParameter("cpf").replaceAll("[^0-9]", "");
        session.setAttribute("cpfLogin", cpf);

        String senha = request.getParameter("senha");
        String senhaHash = generateHashMD5(senha);

        String tipoUsuario = request.getParameter("tipo_usuario");

        AuditoriaDao auditoriaDao = new AuditoriaDao();
        LoginClienteDao dao = new LoginClienteDao();


        Cliente cliente = dao.findByCpf(cpf);


        Auditoria a = new Auditoria();
        // SE TIPO DE USUARIO É IGUAL AO DO BANCO E SENHA HASH É IGUAL AO DO BANCO
        if (!tipoUsuario.equals(cliente.getTipoUsuario().toString()) || !cliente.getSenhaHash().equals(senhaHash)) {
            a.setAcao("LOGIN_FALHA");
            a.setDetalhes("Tentativa de login com CPF: " + cpf);
            a.setDataHora(LocalDateTime.now());
            a.setUsuario(cliente);
            // Salva a auditoria de falha de login
            auditoriaDao.save(a);
            return false;
        }

        // Se já existe uma OTP ativa e ainda não expirou, registra auditoria de OTP pendente
        if (cliente.getOtpAtivo() != null && HORA_ATUAL.isBefore(cliente.getOtpExpiracao())) {
            a.setAcao("OTP_PEDENTE");
            a.setDataHora(LocalDateTime.now());
            a.setDetalhes("OTP ainda válida para o CLIENTE com CPF: " + cpf);
            a.setUsuario(cliente);
            auditoriaDao.save(a);

            return true;
        }

        // SE AGORA FOR 5 MINUTOS DEPOIS DO OTP EXPIRADO OU FOR NULL OU TIVER VAZIO, GERAR NOVA OTP;
        if (HORA_ATUAL.isAfter(cliente.getOtpExpiracao()) || cliente.getOtpAtivo() == null || cliente.getOtpAtivo().isEmpty()) {
            // REGISTRA EM AUDITORIA QUE O OTP FOI GERADO
            a.setAcao("OTP_GERADA");
            a.setDataHora(LocalDateTime.now());
            a.setDetalhes("OPT ENVIADA PARA O CLIENTE COM CPF: " + cpf);
            a.setUsuario(cliente);
        }

        auditoriaDao.save(a);
        generateOTP(cliente);
        dao.updateOtp(cliente);
        return true;
    }

    public boolean validateOtp(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        // Obtém o CPF do cliente da sessão
        String cpf = session.getAttribute("cpfLogin").toString();
        String otp = request.getParameter("otp");

        LoginClienteDao dao = new LoginClienteDao();
        Cliente cliente = dao.findByCpf(cpf);
        AuditoriaDao auditoriaDao = new AuditoriaDao();
        Auditoria a = new Auditoria();
        // Verifica se o OTP é válido e ainda não expirou
        if (cliente.getOtpAtivo() != null && cliente.getOtpAtivo().equals(otp) && HORA_ATUAL.isBefore(cliente.getOtpExpiracao())) {
            // Registra auditoria de sucesso no login
            a.setAcao("LOGIN_SUCESSO");
            a.setDetalhes("Login bem-sucedido para o CLIENTE com CPF: " + cpf);
            a.setDataHora(HORA_ATUAL);
            a.setUsuario(cliente);
            auditoriaDao.save(a);
            // Limpa o OTP após o login bem-sucedido
            cliente.setOtpAtivo(null);
            dao.updateOtp(cliente);
            return true;
        }
        if (cliente.getOtpAtivo() != null && HORA_ATUAL.isAfter(cliente.getOtpExpiracao())) {
            // Registra auditoria de falha de login por OTP expirado
            a.setAcao("OTP_EXPIRADO");
            a.setDetalhes("Tentativa de login com OTP expirado para o CLIENTE com CPF: " + cpf);
            a.setDataHora(HORA_ATUAL);
            a.setUsuario(cliente);
            auditoriaDao.save(a);
            return false;
        } else {
            // Registra auditoria de falha de login por OTP inválido
            a.setAcao("OTP_INVALIDO");
            a.setDetalhes("Tentativa de login com OTP inválido para o CLIENTE com CPF: " + cpf);
            a.setDataHora(HORA_ATUAL);
            a.setUsuario(cliente);
            auditoriaDao.save(a);
            return false;
        }
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