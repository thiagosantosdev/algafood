insert into cozinha (id, nome) values (1,'Portuguesa');
insert into cozinha (id, nome) values (2, 'Italiana');
insert into cozinha (id, nome) values (3, 'Brasileira');

insert into estado(id, nome) values (1, 'Rio de Janeiro');
insert into estado(id, nome) values (2, 'São Paulo');
insert into estado(id, nome) values (3, 'Minas Gerais');

insert into cidade(id, nome, estado_id) values (1, 'Rio de Janeiro', 1);
insert into cidade(id, nome, estado_id) values (1, 'Belo Horizonte', 3);
insert into cidade(id, nome, estado_id) values (1, 'Campinas', 2);


insert into restaurante (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values (1, 'Restaurante do João', 10, 1, 1, '21211-00', 'Rua do Abacate', '100', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values (2, 'Restaurante da Maria', 10, 2, 1, '21211-00', 'Rua do Abacate', '100', 'Centro', utc_timestamp, utc_timestamp);


insert into permissao(nome, descricao) values ('ADMIN','ACESSO_TOTAL');
insert into forma_pagamento(descricao) values ('DINHEIRO');
insert into forma_pagamento(descricao) values ('CARTÃO');
insert into forma_pagamento(descricao) values ('PIX');
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,1), (2,2), (2,3);
