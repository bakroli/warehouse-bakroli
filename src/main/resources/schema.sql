DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS product_category CASCADE;
DROP TABLE IF EXISTS product_price CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS order_details CASCADE;

CREATE TABLE products (
    id int8 generated by default as identity,
    article_number int8 not null,
    description varchar(255) not null,
    name varchar(255) not null,
    valid boolean not null,
    product_category_prefix varchar(255),
    primary key (id)
);

CREATE TABLE product_category (
    prefix varchar(255) not null,
    name varchar(255) not null,
    primary key (prefix)
);

CREATE TABLE product_price (
    id int8 not null,
    list_price float8 not null,
    min_price float8 not null,
    primary key (id)
);

ALTER TABLE products
    ADD FOREIGN KEY (product_category_prefix)
    REFERENCES product_category (prefix);

ALTER TABLE product_price
    ADD FOREIGN KEY (id)
    REFERENCES products (id);

CREATE TABLE orders (
    order_number int8 generated by default as identity,
    comment varchar(255),
    date date not null,
    order_type varchar(255),
    primary key (order_number)
);

create table order_details (
    details_row int8 generated by default as identity,
    number_of_item int8, price_per_item float8,
    order_number int8,
    product_id int8,
    primary key (details_row)
);

ALTER TABLE order_details
    ADD FOREIGN KEY (order_number)
    REFERENCES orders (order_number);

ALTER TABLE order_details
    ADD FOREIGN KEY (product_id)
    REFERENCES products (id);

