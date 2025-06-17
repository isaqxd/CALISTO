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