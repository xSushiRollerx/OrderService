insert into user(id, role) values (1, 1);
insert into user(id, role) values (2,1);
insert into user(id, role) values (5,1);

insert into food_order(id, customer_id, order_state) values (1, 1, 5);
insert into food_order(id, customer_id, order_state) values (2, 1, 1);
insert into food_order(id, customer_id, order_state) values (3, 1, 0);
insert into food_order(id, customer_id, order_state) values (4, 2, 0);
insert into food_order(id, customer_id, order_state) values (5, 2, 2);

insert into food(id, restaurant_id, name, cost) values (1, 23, 'California Roll', 6.99);
insert into food(id, restaurant_id, name, cost) values (2, 23, 'Avocado Roll', 7.99);
insert into food(id, restaurant_id, name, cost) values (3, 23, 'Miso Soup', 2.99);


insert into order_item(id, order_id, food_id, count) values (1, 2, 1, 4);
insert into order_item(id, order_id, food_id, count) values (2, 2, 2, 2);

insert into order_item(id, order_id, food_id, count) values (3, 3, 1, 2);
insert into order_item(id, order_id, food_id, count) values (4, 3, 3, 1);


