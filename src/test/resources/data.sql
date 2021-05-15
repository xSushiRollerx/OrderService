insert into user(username, email, id, user_role) values ('tsemaye', 't.oseragbaje@gmail.com', 1, 1);
insert into user(username, email, id, user_role) values ('hola', 'hello@hello.com', 2,1);
insert into user(username, email, id, user_role) values ('therealbrucewayne', 'batmanisawesome@brucewayne.net', 5,1);
insert into user(username, email, id, user_role) values ('im12', 'myfirstemail@hotmail.com', 4,2);

insert into food_order(id, customer_id, order_state) values (1, 1, 5);
insert into food_order(id, customer_id, order_state) values (2, 1, 1);
insert into food_order(id, customer_id, order_state) values (3, 1, 0);
insert into food_order(id, customer_id, order_state) values (4, 2, 0);
insert into food_order(id, customer_id, order_state) values (6, 2, 0);
insert into food_order(id, customer_id, order_state) values (5, 2, 2);

insert into order_item(id, order_id, food_id, count) values (1, 2, 1, 4);
insert into order_item(id, order_id, food_id, count) values (2, 2, 2, 2);

insert into order_item(id, order_id, food_id, count) values (3, 3, 1, 2);
insert into order_item(id, order_id, food_id, count) values (4, 3, 3, 1);


