insert into cozinha (id, nome) values (1,'Portuguesa');
insert into cozinha (id, nome) values (2, 'Italiana');
insert into cozinha (id, nome) values (3, 'Brasileira');
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1, 'Restaurante da João', 10, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2, 'Restaurante da Maria', 10, 2);

insert into estado(nome) values ('Rio de Janeiro');
insert into cidade(nome) values ('Rio de Janeiro');
insert into permissao(nome, descricao) values ('ADMIN','ACESSO_TORAL');
insert into forma_pagamento(descricao) values ('DINHEIRO');
