CREATE DATABASE calisto;
USE calisto;

CREATE TABLE `calisto`.`endereco` (
    `id_endereco` INT NOT NULL AUTO_INCREMENT,
    `cep` VARCHAR(10) NOT NULL,
    `local` VARCHAR(100) NOT NULL,
    `bairro` VARCHAR(50) NOT NULL,
    `cidade` VARCHAR(50) NOT NULL,
    `estado` CHAR(2) NOT NULL,
    `complemento` VARCHAR(50),
    PRIMARY KEY (`id_endereco`),
    INDEX `cep_idx` (`cep`)
);

CREATE TABLE `calisto`.`usuario` (
    `id_usuario` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(100) NOT NULL,
    `cpf` VARCHAR(11) NOT NULL,
    `data_nascimento` DATE NOT NULL,
    `telefone` VARCHAR(15) NOT NULL,
    `tipo_usuario` ENUM('FUNCIONARIO', 'CLIENTE') NOT NULL,
    `senha_hash` VARCHAR(32) NOT NULL,
    `otp_ativo` VARCHAR(6),
    `otp_expiracao` DATETIME,
    `endereco_id` int,
    PRIMARY KEY (`id_usuario`),
    foreign key (`endereco_id`) references `endereco`(id_endereco),
    UNIQUE (`cpf`)
);

CREATE TABLE `calisto`.`funcionario` (
    `id_funcionario`INT NOT NULL AUTO_INCREMENT,
    `usuario_id` INT,
    `codigo_funcionario` VARCHAR(20) NOT NULL,
    `cargo` ENUM('ESTAGIARIO', 'ATENDENTE', 'GERENTE') NOT NULL,
    `id_supervisor` INT,
    `endereco_id` INT,
    PRIMARY KEY (`id_funcionario`),
    FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(id_usuario),
    FOREIGN KEY (`id_supervisor`) REFERENCES `funcionario`(id_funcionario),
    FOREIGN KEY (`endereco_id`) REFERENCES `endereco`(id_endereco),
    UNIQUE (`codigo_funcionario`)
);

CREATE TABLE `calisto`.`cliente` (
    `id_cliente` INT NOT NULL AUTO_INCREMENT,
    `usuario_id` INT,
    `score_credito` DECIMAL(5,2) DEFAULT 0,
    PRIMARY KEY (`id_cliente`),
    FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(id_usuario)
);

CREATE TABLE `calisto`.`agencia`(
    `id_agencia` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(50) NOT NULL,
    `codigo_agencia` VARCHAR(10),
    `endereco_id` INT,
    PRIMARY KEY (`id_agencia`),
    FOREIGN KEY (`endereco_id`) REFERENCES `endereco`(id_endereco),
	UNIQUE (`codigo_agencia`)
);

CREATE TABLE `calisto`.`conta`(
    `id_conta` INT NOT NULL AUTO_INCREMENT,
    `numero_conta` VARCHAR(20) NOT NULL,
    `agencia_id` INT,
    `saldo` DECIMAL(15,2) NOT NULL DEFAULT 0,
    `tipo_conta` ENUM('POUPANCA','CORRENTE', 'INVESTIMENTO') NOT NULL,
    `cliente_id` INT,
    `data_abertura` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` ENUM('ATIVA','ENCERRADA','BLOQUEADA') not null default 'ATIVA',
    PRIMARY KEY (`id_conta`),
    FOREIGN KEY (`agencia_id`) REFERENCES `agencia`(id_agencia),
    FOREIGN KEY (`cliente_id`) REFERENCES `cliente`(id_cliente),
    UNIQUE (`numero_conta`),
    INDEX `numero_conta_idx` (`numero_conta`)
);

CREATE TABLE `calisto`.`conta_poupanca` (
    `id_conta_poupanca` INT NOT NULL AUTO_INCREMENT,
    `conta_id` INT,
    `taxa_rendimento` DECIMAL(5,2) NOT NULL,
    `ultimo_rendimento` DATETIME,
    PRIMARY KEY (`id_conta_poupanca`),
    FOREIGN KEY (`conta_id`) REFERENCES `conta`(id_conta),
    UNIQUE (`conta_id`)
);

CREATE TABLE `calisto`.`conta_corrente`(
    `id_conta_corrente` INT NOT NULL AUTO_INCREMENT,
    `conta_id` INT,
    `limite` DECIMAL(15,2) NOT NULL DEFAULT 0,
    `data_vencimento` DATE NOT NULL,
    `taxa_manutencao` DECIMAL (5,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id_conta_corrente`),
    FOREIGN KEY (`conta_id`) REFERENCES `conta`(id_conta),
    UNIQUE (`conta_id`)
);

CREATE TABLE `calisto`.`conta_investimento`(
    `id_conta_investimento` int not null auto_increment,
    `conta_id` int,
    `perfil_risco` enum('BAIXO','MEDIO','ALTO') not null,
    `valor_minimo` decimal(15,2) not null,
    `taxa_rendimento` decimal (5,2) not null,
    PRIMARY KEY (`id_conta_investimento`),
    FOREIGN KEY (`conta_id`) REFERENCES `conta`(id_conta),
    unique (`conta_id`)
);

CREATE TABLE `calisto`.`transacao`(
    `id_transacao` int not null auto_increment,
    `id_conta_origem` int,
    `id_conta_destino` int,
    `tipo_transacao` enum('DEPOSITO', 'SAQUE', 'TRANSFERENCIA', 'TAXA', 'RENDIMENTO') not null,
    `valor` decimal(15,2) not null,
    `data_hora` timestamp not null default current_timestamp,
    `descricao` varchar(100),
    PRIMARY KEY (`id_transacao`),
    FOREIGN KEY (`id_conta_origem`) REFERENCES `conta`(id_conta),
    FOREIGN KEY (`id_conta_destino`) references `conta` (id_conta),
    INDEX `data_hora_idx` (`data_hora`)
);

CREATE TABLE `calisto`.`auditoria`(
    `id_auditoria` int not null auto_increment,
    `usuario_id` int,
    `acao` varchar(50) not null,
    `data_hora` timestamp not null default current_timestamp,
    `detalhes` text,
    PRIMARY KEY (`id_auditoria`),
    foreign key (`usuario_id`) references `usuario`(id_usuario)
);

CREATE TABLE `calisto`.`relatorio`(
    `id_relatorio` int not null auto_increment,
    `funcionario_id` int,
    `tipo_relatorio` varchar(50) not null,
    `data_geracao` timestamp not null default current_timestamp,
    `conteudo` text not null,
    PRIMARY KEY (`id_relatorio`),
    foreign key (`funcionario_id`) references `funcionario`(id_funcionario)
);

SELECT
    c.id_cliente, c.score_credito,
    u.id_usuario, u.nome, u.cpf, u.data_nascimento, u.telefone, u.tipo_usuario,
    e.id_endereco, e.cep, e.local, e.bairro, e.cidade, e.estado, e.complemento
FROM cliente c
INNER JOIN usuario u ON c.usuario_id = u.id_usuario
INNER JOIN endereco e ON u.endereco_id = e.id_endereco;