delete from accounts;

insert into  accounts (id,name,balance,currency) values ('fc35ad28-91fc-449b-af3e-918417266f9d', 'main account', 10000.10, 'PLN');
insert into  accounts (id,name,balance,currency) values ('5fd82e4e-c0ae-4771-a9d5-e18e3df32d65', 'savings account', 0, 'PLN');
insert into  accounts (id,name,balance,currency) values ('5f73cec7-6ac1-46ee-a203-794c35d8800c', 'EUR account', 50, 'EUR');
insert into  accounts (id,name,balance,currency) values ('3d9c2f62-d66c-4e31-89a7-ccdf271a7591', 'USD account', 50, 'USD');
insert into  accounts (id,name,balance,currency) values ('e62ee6a0-4549-4956-a0dd-685f23526961', 'CHF account', 50, 'CHF');
insert into  accounts (id,name,balance,currency) values ('7b93e505-2c0f-47a6-8ad6-6f6125c1c9a3', 'GBP account', 50, 'GBP');
insert into  accounts (id,name,balance,currency) values ('c7c6c077-931e-42f9-982a-8c836ab6b932', 'GBP account', 250.5, 'PLN');

insert into card (id,account_id,name,surname,max_limit,max_debet,card_type) values ('ac3a7a5a-1949-4182-b2ca-7f138bd19915','fc35ad28-91fc-449b-af3e-918417266f9d','Sara','Przebinda',100.0,100.0,'CREDIT');
insert into card (id,account_id,name,surname,max_limit,max_debet,card_type) values ('71781a31-ed7f-4127-9734-aa4c23cb74a7','fc35ad28-91fc-449b-af3e-918417266f9d','Sara','Przebinda',100.0,100.0,'PAYMENT');
insert into card (id,account_id,name,surname,max_limit,max_debet,card_type) values ('5e6e4432-1723-4e77-80d5-70cf1f83c80a','5f73cec7-6ac1-46ee-a203-794c35d8800c','Albert','Podraza',100.0,100.0,'PAYMENT');
insert into card (id,account_id,name,surname,max_limit,max_debet,card_type) values ('bd8bcbc5-0a0b-47d0-8af9-2afc824a3a96','5f73cec7-6ac1-46ee-a203-794c35d8800c','Albert','Podraza',100.0,100.0,'CREDIT');
