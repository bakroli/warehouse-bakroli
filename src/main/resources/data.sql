INSERT INTO product_category (name, prefix) VALUES ('TelescopeStar', 'TS');
INSERT INTO product_category (name, prefix) VALUES ('TelescopeLine', 'TL');
INSERT INTO product_category (name, prefix) VALUES ('GoTelescope','GT');
INSERT INTO product_category (name, prefix) VALUES ('Telescopean', 'TP');
INSERT INTO product_category (name, prefix) VALUES ('SpaceTelescope','ST');
INSERT INTO product_category (name, prefix) VALUES ('PopeScope','PS');

INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1001, 'TLN-1200', 'Reflector, newton, 250/1200', TRUE, 'TL');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1002, 'TS-1000', 'Reflector, newton, 200/1000', TRUE, 'TS');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1003, 'STS-RR-200', 'Refractor', TRUE, 'ST');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1004, 'STS-RL-1000', 'Reflector', TRUE, 'ST');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1005, 'GTN-1000', 'Reflector', TRUE, 'GT');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1006, 'GTR-1000', 'Refractor', TRUE, 'GT');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1007, 'GTR-1005', 'Refractor', FALSE, 'GT');
INSERT INTO products (article_number, name, description, valid, product_category_prefix) VALUES (1008, 'PS-1600', 'Reflector', TRUE, 'PS');
INSERT INTO products (article_number, name, description, valid) VALUES (1009, 'D-Demo', 'Demologia', TRUE);

INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'TLN-1200'), 1000, 800);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'TS-1000'), 1200, 900);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'STS-RR-200'), 2000, 1500);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'STS-RL-1000'), 1800, 1200);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'GTN-1000'), 1100, 900);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'GTR-1000'), 1800, 1100);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'GTR-1005'), 1850, 1300);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'PS-1600'), 1600, 1300);
INSERT INTO product_price (id, list_price, min_price) VALUES ( (SELECT id FROM products WHERE name = 'D-Demo'), 0, 0);

INSERT INTO orders (date, comment, order_type) VALUES ('2022-06-01', 'opening set','IN');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (1, (SELECT id FROM products WHERE name = 'TLN-1200'), 10, 700);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (1, (SELECT id FROM products WHERE name = 'TS-1000'), 15, 800);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (1, (SELECT id FROM products WHERE name = 'STS-RL-1000'), 5, 1000);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (1, (SELECT id FROM products WHERE name = 'GTR-1000'), 2, 1000);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (1, (SELECT id FROM products WHERE name = 'GTR-1005'), 3, 1100);

INSERT INTO orders (date, order_type) VALUES ('2022-06-02', 'IN');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (2, (SELECT id FROM products WHERE name = 'GTN-1000'), 8, 750);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (2, (SELECT id FROM products WHERE name = 'GTR-1000'), 8, 950);

INSERT INTO orders (date, order_type, comment) VALUES ('2022-06-03', 'OUT', '1+1 akcija');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (3, (SELECT id FROM products WHERE name = 'TS-1000'), 4, 1100);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (3, (SELECT id FROM products WHERE name = 'TS-1000'), 4, 900);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (3, (SELECT id FROM products WHERE name = 'GTN-1000'), 2, 1000);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (3, (SELECT id FROM products WHERE name = 'GTN-1000'), 2, 900);

INSERT INTO orders (date, order_type, comment) VALUES ('2022-06-03', 'IN', 'demologia');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (4, (SELECT id FROM products WHERE name = 'D-Demo'), 2, 0);

INSERT INTO orders (date, order_type) VALUES ('2022-06-06', 'OUT');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (5, (SELECT id FROM products WHERE name = 'TS-1000'), 2, 1000);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (5, (SELECT id FROM products WHERE name = 'STS-RL-1000'), 2, 1400);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (5, (SELECT id FROM products WHERE name = 'GTR-1000'), 2, 1200);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (5, (SELECT id FROM products WHERE name = 'GTR-1005'), 3, 1400);

INSERT INTO orders (date, order_type) VALUES ('2022-06-07', 'OUT');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (6, (SELECT id FROM products WHERE name = 'TS-1000'), 2, 1100);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (6, (SELECT id FROM products WHERE name = 'GTR-1000'), 3, 1200);

INSERT INTO orders (date, order_type) VALUES ('2022-06-07', 'OUT');
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (7, (SELECT id FROM products WHERE name = 'STS-RL-1000'), 1, 1400);
INSERT INTO order_details (order_number, product_id, number_of_item, price_per_item) VALUES (7, (SELECT id FROM products WHERE name = 'GTR-1000'), 1, 1200);