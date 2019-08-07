truncate users;
truncate product;
truncate products_users;

insert into users (user_id, name, created_on, modified_on) values ('e7485042-b46b-11e9-986a-b74e614de0b0', 'Ann', null, null);
insert into users (user_id, name, created_on, modified_on) values ('e74b86d6-b46b-11e9-986b-1fa4762759f8', 'John', '2019-08-01 17:51:51.000000', '2019-08-01 17:51:51.000000');

insert into product(product_id, name, created_on, modified_on) VALUES ('f3a775de-b46b-11e9-95e4-af440b6044e6', 'product1', '2019-08-01 17:51:51.000000', '2019-08-01 17:51:51.000000');
insert into product(product_id, name, created_on, modified_on) VALUES ('f3a775de-b46b-11e9-95e4-af440b6044d4','product2', '2019-08-01 17:51:51.000000', '2019-08-01 17:51:51.000000');

insert into products_users(user_id, product_id) VALUES ('e7485042-b46b-11e9-986a-b74e614de0b0', 'f3a775de-b46b-11e9-95e4-af440b6044e6');
insert into products_users(user_id, product_id) VALUES ('e7485042-b46b-11e9-986a-b74e614de0b0', 'f3a775de-b46b-11e9-95e4-af440b6044d4');
insert into products_users(user_id, product_id) VALUES ('e74b86d6-b46b-11e9-986b-1fa4762759f8', 'f3a775de-b46b-11e9-95e4-af440b6044e6');