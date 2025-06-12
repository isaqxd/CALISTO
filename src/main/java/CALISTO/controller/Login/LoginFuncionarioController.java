package CALISTO.controller.Login;

import CALISTO.model.dao.LoginFuncionarioDao;
import CALISTO.model.persistence.Usuario.Funcionario;
import CALISTO.model.service.Login.LoginFuncionarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/loginFuncionario")
public class LoginFuncionarioController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ESTE É O TESTE MAIS IMPORTANTE
        System.out.println("**************************************************");
        System.out.println("****** LoginFuncionarioController: MÉTODO doPost FOI ATINGIDO! ******");
        System.out.println("**************************************************");

        // Debug parameters
        System.out.println("Request parameters:");
        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println(paramName + ": " + paramValue);
        }

        String action = request.getParameter("action");
        System.out.println("Action parameter: " + action);

        LoginFuncionarioService service = new LoginFuncionarioService();
        try {
            if ("verifyCredentials".equals(action)) {
                // Etapa 1: Validar Código e Senha
                System.out.println("Calling handleCredentialsValidation");
                handleCredentialsValidation(request, response, service);
            } else if ("verifyOTP".equals(action)) {
                // Etapa 2: Validar o Código OTP
                System.out.println("Calling handleOtpValidation");
                handleOtpValidation(request, response, service);
            } else {
                // Ação desconhecida, redirecionar com erro genérico
                System.out.println("Unknown action: " + action);
                redirectToLogin(response, "Ação inválida.", null);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Erro de banco de dados ao processar login", e);
        } catch (Exception e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Erro inesperado ao processar login", e);
        }
    }

    private void handleCredentialsValidation(HttpServletRequest request, HttpServletResponse response, LoginFuncionarioService service)
            throws SQLException, IOException {

        System.out.println("=================================================");
        System.out.println("======> [CONTROLLER] 1. Entrou em handleCredentialsValidation");
        System.out.println("======> [CONTROLLER] Código: " + request.getParameter("cpf"));
        System.out.println("======> [CONTROLLER] Tipo Usuario: " + request.getParameter("tipo_usuario"));

        Funcionario funcionarioValidado = null; // Inicializa como nulo
        try {
            funcionarioValidado = service.validateLoginCredentials(request);
        } catch (Exception e) {
            System.out.println("======> [CONTROLLER] ERRO AO CHAMAR O SERVICE: " + e.getMessage());
            e.printStackTrace(); // Imprime o erro completo no console
        }

        System.out.println("======> [CONTROLLER] 2. Resultado da validação do serviço: Funcionario é " + (funcionarioValidado != null ? "VÁLIDO (não nulo)" : "INVÁLIDO (nulo)"));

        if (funcionarioValidado != null) {
            // Credenciais OK! Redirecionar para a mesma página para pedir o OTP.
            String codigo = request.getParameter("cpf");
            String redirectURL = "views/login.jsp?otpRequired=true&identifier=" + URLEncoder.encode(codigo, StandardCharsets.UTF_8) 
                    + "&tipo_usuario=FUNCIONARIO";

            System.out.println("======> [CONTROLLER] 3. SUCESSO! Redirecionando para a tela de OTP: " + redirectURL);
            response.sendRedirect(redirectURL);
        } else {
            // Credenciais inválidas. Redirecionar com erro.
            String codigo = request.getParameter("cpf");
            System.out.println("======> [CONTROLLER] 3. FALHA! Redirecionando para a tela de login com erro.");
            redirectToLogin(response, "Código ou senha inválidos.", codigo);
        }
        System.out.println("=================================================");
    }

    private void handleOtpValidation(HttpServletRequest request, HttpServletResponse response, LoginFuncionarioService service)
            throws SQLException, IOException {

        String codigo = request.getParameter("cpf"); // O código/identifier ainda está no formulário
        String otp = request.getParameter("otp"); // O JS montou o OTP completo neste parâmetro

        System.out.println("=================================================");
        System.out.println("======> [CONTROLLER] 1. Entrou em handleOtpValidation");
        System.out.println("======> [CONTROLLER] Código: " + codigo);
        System.out.println("======> [CONTROLLER] OTP: " + otp);

        if (service.validateOTP(codigo, otp)) {
            // OTP Correto! Login completo.
            // Agora sim, buscamos o usuário e o colocamos na sessão.
            LoginFuncionarioDao dao = new LoginFuncionarioDao();
            Funcionario funcionarioLogado = dao.findByCodigo(codigo.replaceAll("[^A-Za-z0-9]", ""));

            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", funcionarioLogado); // Colocamos o objeto COMPLETO na sessão
            session.setMaxInactiveInterval(30 * 60); // Define a sessão para expirar em 30 minutos

            System.out.println("======> [CONTROLLER] 2. OTP válido! Redirecionando para a página do portal do funcionário");
            response.sendRedirect("views/portalfuncionario.jsp");
        } else {
            // OTP Inválido. Redirecionar de volta para a tela de OTP com erro.
            String errorMessage = "Código OTP inválido ou expirado.";
            String redirectURL = "views/login.jsp?otpRequired=true&identifier=" + URLEncoder.encode(codigo, StandardCharsets.UTF_8)
                    + "&error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8)
                    + "&tipo_usuario=FUNCIONARIO";

            System.out.println("======> [CONTROLLER] 2. OTP inválido! Redirecionando de volta para a tela de OTP");
            System.out.println("======> [CONTROLLER] Redirect URL: " + redirectURL);
            response.sendRedirect(redirectURL);
        }
        System.out.println("=================================================");
    }

    // Método auxiliar para redirecionar para a página de login com mensagens
    private void redirectToLogin(HttpServletResponse response, String message, String lastIdentifier) throws IOException {
        String url = "views/login.jsp?error=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
        if (lastIdentifier != null && !lastIdentifier.isEmpty()) {
            url += "&lastIdentifier=" + URLEncoder.encode(lastIdentifier, StandardCharsets.UTF_8);
        }
        url += "&tipo_usuario=FUNCIONARIO";
        System.out.println("Redirecting to: " + url);
        response.sendRedirect(url);
    }
}
