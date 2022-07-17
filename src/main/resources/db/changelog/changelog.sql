--CREATE SCHEMA cash_register;

CREATE TABLE employee (
	id bigint auto_increment NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	job_position varchar NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY (id)
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
	employee_id int4 NOT NULL,
	CONSTRAINT order_pk PRIMARY KEY (id)
);

CREATE TABLE order_details (
	id bigint auto_increment NOT NULL,
	stock_id bigint NOT NULL,
	quantity_ordered int4 NOT NULL,
	order_id bigint NOT NULL,
	CONSTRAINT order_details_pk PRIMARY KEY (id)
);

ALTER TABLE purchase_order ADD CONSTRAINT order_fk FOREIGN KEY (employee_id) REFERENCES employee(id);
ALTER TABLE order_details ADD CONSTRAINT order_details_fk FOREIGN KEY (order_id) REFERENCES purchase_order(id);
ALTER TABLE order_details ADD CONSTRAINT order_details_fk_1 FOREIGN KEY (stock_id) REFERENCES stock(id);

insert into employee(first_name, last_name, job_position) values ('John', 'Smith', 'Cashier');
insert into employee(first_name, last_name, job_position) values ('Mary', 'Kay', 'Senior Cashier');
insert into employee(first_name, last_name, job_position) values ('John', 'Williams', 'Senior Cashier');
insert into employee(first_name, last_name, job_position) values ('Tom', 'Smith', 'Commodity Expert');

insert into stock (product_code, product_name, quantity, price) values ('aaa55', 'Book', 3, 1500);
insert into stock (product_code, product_name, quantity, price) values ('nnn6', 'Велосипед', 10, 35);