USE calisto;

CREATE OR REPLACE VIEW view_consulta_conta_com_transacoes AS
SELECT c.id_conta,
       c.numero_conta,
       c.tipo_conta,
       u.nome      AS titular,
       u.cpf,
       c.saldo,
       cc.limite,
       cc.data_vencimento,
       t.id_transacao,
       t.tipo_transacao,
       t.valor     AS valor_transacao,
       t.data_hora AS data_transacao,
       t.descricao
FROM conta c
         JOIN cliente cl ON c.cliente_id = cl.id_cliente
         JOIN usuario u ON cl.usuario_id = u.id_usuario
         LEFT JOIN conta_corrente cc ON c.id_conta = cc.conta_id
         LEFT JOIN transacao t ON (t.id_conta_origem = c.id_conta OR t.id_conta_destino = c.id_conta)
    AND t.data_hora >= NOW() - INTERVAL 90 DAY;

SELECT * FROM view_consulta_conta_com_transacoes;
# ***************************************************************************************************
CREATE OR REPLACE VIEW vw_dados_cliente_contas AS
SELECT
    u.nome,
    u.cpf,
    u.data_nascimento,
    u.telefone,
    CONCAT(e.local, ', ', e.numero_casa, ' - ', e.bairro, ', ', e.cidade, '/', e.estado) AS endereco,
    c.score_credito,
    ct.numero_conta,
    ct.tipo_conta,
    ct.status
FROM cliente c
         JOIN usuario u ON c.usuario_id = u.id_usuario
         JOIN endereco e ON u.endereco_id = e.id_endereco
         LEFT JOIN conta ct ON ct.cliente_id = c.id_cliente;

select * from vw_dados_cliente_contas;
# ***************************************************************************************************
CREATE OR REPLACE VIEW vw_dados_funcionario_contas AS
SELECT
    f.codigo_funcionario AS codigo,
    f.cargo,
    u.nome,
    u.cpf,
    u.data_nascimento,
    u.telefone,
    CONCAT(e.local, ', ', e.numero_casa, ' - ', e.bairro, ', ', e.cidade, '/', e.estado) AS endereco_completo,
    (SELECT COUNT(*)
     FROM auditoria a
     WHERE a.usuario_id = u.id_usuario
       AND a.acao LIKE '%CRIAÇÃO DE CONTA%') AS contas_abertas
FROM funcionario f
         JOIN usuario u ON f.usuario_id = u.id_usuario
         LEFT JOIN endereco e ON u.endereco_id = e.id_endereco;
