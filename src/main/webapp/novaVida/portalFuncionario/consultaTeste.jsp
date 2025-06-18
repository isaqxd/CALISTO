<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Dados</title>
    <link rel="stylesheet" href="css/portal-cliente.css">
    <meta charset="UTF-8">
    <style>
        .tabs {
            display: flex;
            justify-content: center;
            gap: 1rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .tab-btn {
            padding: 0.8rem 1.5rem;
            border: none;
            background: #F2B950;
            color: #fff;
            border-radius: 10px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }

        .tab-btn.active, .tab-btn:hover {
            background: #BF7A24;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
            animation: fadeIn 0.3s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<div class="portal-container">
    <h2>Consulta de Dados</h2>

    <div class="tabs">
        <button class="tab-btn active" onclick="showTab('cliente')">Cliente</button>
        <button class="tab-btn" onclick="showTab('conta')">Conta</button>
        <button class="tab-btn" onclick="showTab('funcionario')">Funcionário</button>
    </div>

    <div id="cliente" class="tab-content active">
        <jsp:include page="consultaCliente.jsp" />
    </div>

    <div id="conta" class="tab-content">
        <jsp:include page="consultaConta.jsp" />
    </div>

    <div id="funcionario" class="tab-content">
        <jsp:include page="consultaFuncionario.jsp" />
    </div>
</div>

<script>
    function showTab(tabId) {
        const tabs = document.querySelectorAll(".tab-content");
        const buttons = document.querySelectorAll(".tab-btn");

        tabs.forEach(tab => {
            tab.classList.remove("active");
        });

        buttons.forEach(btn => {
            btn.classList.remove("active");
        });

        document.getElementById(tabId).classList.add("active");
        event.target.classList.add("active");
    }
</script>
</body>
</html>