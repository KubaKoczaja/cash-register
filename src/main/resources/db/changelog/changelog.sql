CREATE TABLE cr_user (
	id bigint auto_increment NOT NULL,
	first_name varchar NOT NULL,
    last_name varchar NOT NULL,
    job_position varchar NOT NULL,
	username varchar NOT NULL,
	user_password varchar NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT cr_user_un UNIQUE (username)
);

CREATE TABLE report (
	id bigint auto_increment NOT NULL,
	user_id int4 NOT NULL,
	date_from timestamp NOT NULL,
	date_to timestamp NOT NULL,
	content varchar NULL,
	report_type varchar NOT NULL,
	CONSTRAINT report_pk PRIMARY KEY (id)
);

CREATE TABLE stock (
	id bigint auto_increment NOT NULL,
	product_code varchar NOT NULL,
	product_name varchar NOT NULL,
	quantity int4 NOT NULL,
	price int4 NULL,
	CONSTRAINT stock_pk PRIMARY KEY (id)
);

CREATE TABLE purchase_order (
	id bigint auto_increment NOT NULL,
	open_date timestamp NOT NULL,
	close_date timestamp,
	user_id int4 NOT NULL,
	CONSTRAINT order_pk PRIMARY KEY (id)
);

CREATE TABLE order_item (
	id bigint auto_increment NOT NULL,
	stock_id bigint NOT NULL,
	quantity_ordered int4 NOT NULL,
	order_id bigint NOT NULL,
	CONSTRAINT order_item_pk PRIMARY KEY (id)
);
create table auth_user_group (
    auth_user_group_id bigint auto_increment not null,
    username varchar not null,
    auth_group varchar not null,
CONSTRAINT auth_user_group_pk PRIMARY KEY (auth_user_group_id),
CONSTRAINT auth_user_group_un_1 UNIQUE (username)
);

ALTER TABLE report ADD CONSTRAINT report_fk FOREIGN KEY (user_id) REFERENCES cr_user(id);
ALTER TABLE purchase_order ADD CONSTRAINT order_fk FOREIGN KEY (user_id) REFERENCES cr_user(id);
ALTER TABLE order_item ADD CONSTRAINT order_item_fk FOREIGN KEY (order_id) REFERENCES purchase_order(id);
ALTER TABLE order_item ADD CONSTRAINT order_item_fk_1 FOREIGN KEY (stock_id) REFERENCES stock(id);
ALTER TABLE auth_user_group ADD CONSTRAINT auth_user_group_fk FOREIGN KEY (username) REFERENCES cr_user(username);

insert into cr_user(first_name, last_name, job_position, username, user_password) values ('John', 'Smith', 'Cashier', 'johsmi1', '$2a$10$hOZ9M9h49Y4.guaMIVSA4eHpGlk5smP8KnnHjx7l1hwK24GSQtt4O');
insert into cr_user(first_name, last_name, job_position, username, user_password) values ('Mary', 'Kay', 'Senior Cashier', 'markay2', '$2a$10$8LP6XUNr7KULYJmUva5aneLTrebRVxf4TIwzRmG0FZPCfSL3QnaYq');
insert into cr_user(first_name, last_name, job_position, username, user_password) values ('John', 'Williams', 'Senior Cashier', 'johwil3', '$2a$10$mNe3QPZB.zh0OwKmbPKWXOj6RefFD9X6g2pbP5mG5KcMPsLfsdtJm');
insert into cr_user(first_name, last_name, job_position, username, user_password) values ('Tom', 'Smith', 'Commodity Expert', 'tomsmi4', '$2a$10$iyujZOCRp4/BeDfN1ywdMOP2U/D3Q/Wpbi.yaxjAxfIXFVkn479Vy');

insert into stock (product_code, product_name, quantity, price) values ('aaa55', 'Book', 3, 1500);
insert into stock (product_code, product_name, quantity, price) values ('uio6', 'Велосипед', 10, 35);
insert into stock (product_code, product_name, quantity, price) values ('wer7', 'Hammer', 4, 70);
insert into stock (product_code, product_name, quantity, price) values ('pob89', 'Table', 8, 90);
insert into stock (product_code, product_name, quantity, price) values ('ddd', 'Łóżko', 2, 1400);
insert into stock (product_code, product_name, quantity, price) values ('eee45', 'Wichajster', 20, 50);

insert into purchase_order(open_date, close_date, user_id) values ('2022-07-27 11:45:58.299', '2022-07-27 12:45:58.299', 1);
insert into purchase_order(open_date, close_date, user_id) values ('2022-07-27 13:45:58.299', '2022-07-27 13:55:58.299', 1);
insert into purchase_order(open_date, close_date, user_id) values ('2022-07-27 14:45:58.299', '2022-07-27 14:55:58.299', 2);
insert into purchase_order(open_date, close_date, user_id) values ('2022-07-27 15:45:58.299', '2022-07-27 16:05:58.299', 3);

insert into order_item(stock_id, quantity_ordered, order_id) values (2, 1, 1);
insert into order_item(stock_id, quantity_ordered, order_id) values (2, 1, 1);
insert into order_item(stock_id, quantity_ordered, order_id) values (2, 1, 1);
insert into order_item(stock_id, quantity_ordered, order_id) values (2, 1, 1);

insert into report(user_id, date_from, date_to, content, report_type) values (2, '2022-07-23 11:45:58.299', '2022-07-23 14:45:58.299', 'test', 'x');

insert into auth_user_group(username, auth_group) values ('johsmi1','CASHIER');
insert into auth_user_group(username, auth_group) values ('markay2','SENIOR_CASHIER');
insert into auth_user_group(username, auth_group) values ('johwil3','SENIOR_CASHIER');
insert into auth_user_group(username, auth_group) values ('tomsmi4','COMMODITY_EXPERT');