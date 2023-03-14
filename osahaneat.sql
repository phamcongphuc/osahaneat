create database osahaneat;
use osahaneat;

create table roles(
	id int auto_increment,
    role_name varchar(10),
    description varchar(255),
	
    primary key(id)
);

create table users(
	id int auto_increment,
    email varchar(255) unique,
    password varchar(255),
    fullname varchar(50),
    address varchar(255),
    role_id int,
    
    primary key(id),
    constraint FK_users_role_id foreign key(role_id) references roles(id)
);

create table category(
	id int auto_increment,
    name varchar(255),
    description text,
    
    primary key(id)
);

create table orders(
	id int auto_increment,
    total_price decimal,
    user_id int,
    create_date timestamp,
    status varchar(20),
  
	primary key(id),
    constraint FK_orders_user_id foreign key(user_id) references users(id)
);

create table restaurant(
	id int auto_increment,
    name varchar(255),
    adderss varchar(255),
    description text,
    rating int,
    content text,
    
    primary key(id)
);

create table category_restaurant(
	id int auto_increment,
    category_id int,
    restaurant_id int,
    
    primary key(id),
    constraint FK_category_restaurant_cateid foreign key(category_id) references category(id),
    constraint FK_category_restaurant_resid foreign key(restaurant_id) references restaurant(id)
);

create table food(
	id int auto_increment,
    name varchar(255),
    description text,
    price decimal,
    instruction text,
	cate_res_id int,
    
    primary key(id),
    constraint FK_food_category_id foreign key(cate_res_id) references category_restaurant(id)
);

create table orders_item(
	order_id int,
    food_id int,
    amount int,
    price decimal,
    
    primary key(order_id,food_id),
    constraint FK_orders_item_order_id foreign key(order_id) references orders(id),
    constraint FK_orders_item_food_id foreign key(food_id) references food(id)
);

create table rating_order(
	id int auto_increment,
    user_id int,
    order_id int,
    star int,
    comment text,
    
    primary key(id),
    constraint FK_rating_order_user_id foreign key(user_id) references users(id),
    constraint FK_rating_order_order_id foreign key(order_id) references orders(id)
);

create table rating_food(
	id int auto_increment,
    user_id int,
    food_id int,
    star int,
    comment text,
    
    primary key(id),
    constraint FK_rating_food_user_id foreign key(user_id) references users(id),
    constraint FK_rating_food_food_id foreign key(food_id) references food(id)
);

create table rating_restaurant(
	id int auto_increment,
    user_id int,
    res_id int,
    star int,
    comment text,
    
    primary key(id),
    constraint FK_rating_res_user_id foreign key(user_id) references users(id),
    constraint FK_rating_res_res_id foreign key(res_id) references restaurant(id)
);


