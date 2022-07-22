CREATE TABLE employee (
	id bigint auto_increment NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	job_position varchar NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY (id)
);

CREATE TABLE cr_user (
	id bigint auto_increment NOT NULL,
	first_name varchar NOT NULL,
    last_name varchar NOT NULL,
    job_position varchar NOT NULL,
	login varchar NOT NULL,
	"password" varchar NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT cr_user_un UNIQUE (login)
);

CREATE TABLE report (
	id bigint auto_increment NOT NULL,
	user_id int4 NOT NULL,
	date_from timestamp NOT NULL,
	date_to timestamp NOT NULL,
	number_of_orders int4 NULL,
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
	date_of_creation timestamp NOT NULL,
	user_id int4 NOT NULL,
	CONSTRAINT order_pk PRIMARY KEY (id)
);

CREATE TABLE order_details (
	id bigint auto_increment NOT NULL,
	stock_id bigint NOT NULL,
	quantity_ordered int4 NOT NULL,
	order_id bigint NOT NULL,
	CONSTRAINT order_details_pk PRIMARY KEY (id)
);

ALTER TABLE report ADD CONSTRAINT report_fk FOREIGN KEY (user_id) REFERENCES cr_user(id);
ALTER TABLE purchase_order ADD CONSTRAINT order_fk FOREIGN KEY (user_id) REFERENCES cr_user(id);
ALTER TABLE order_details ADD CONSTRAINT order_details_fk FOREIGN KEY (order_id) REFERENCES purchase_order(id);
ALTER TABLE order_details ADD CONSTRAINT order_details_fk_1 FOREIGN KEY (stock_id) REFERENCES stock(id);

insert into cr_user(first_name, last_name, job_position, login, "password") values ('John', 'Smith', 'Cashier', 'johsmi1', 'test');
insert into cr_user(first_name, last_name, job_position, login, "password") values ('Mary', 'Kay', 'Senior Cashier', 'markay2', 'test');
insert into cr_user(first_name, last_name, job_position, login, "password") values ('John', 'Williams', 'Senior Cashier', 'johwil3', 'test');
insert into cr_user(first_name, last_name, job_position, login, "password") values ('Tom', 'Smith', 'Commodity Expert', 'tomsmi4', 'test');

insert into stock (product_code, product_name, quantity, price) values ('aaa55', 'Book', 3, 1500);
insert into stock (product_code, product_name, quantity, price) values ('nnn6', 'Велосипед', 10, 35);

insert into report(user_id, date_from, date_to, number_of_orders, report_type) values (2, '2022-07-23 11:45:58.299', '2022-07-23 14:45:58.299', 10, 'x');
