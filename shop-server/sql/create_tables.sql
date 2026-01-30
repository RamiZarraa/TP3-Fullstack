create table categories (
    id bigserial primary key,
    name varchar(255) not null
);

create table localized_product (
    id bigserial primary key,
    description varchar(255),
    locale varchar(255) not null,
    name varchar(255) not null
);

create table opening_hours (
    id bigserial primary key,
    close_at time not null,
    day int4 not null check (day>=1 AND day<=7),
    open_at time not null);

create table products (
    id bigserial primary key,
    price int8 not null,
    shop_id int8
);

create table products_categories (
    product_id int8 not null,
    category_id int8 not null
);

create table products_localized_product (
    product_id int8 not null,
    localized_product_id int8 not null
);

create table shops (
    id bigserial primary key,
    created_at date not null,
    in_vacations boolean not null,
    name varchar(255) not null
);

create table shops_opening_hours (
    shop_id int8 not null,
    opening_hours_id int8 not null
);

create table translation (
    id bigserial primary key,
    field_type varchar(255) not null,
    language varchar(255) not null,
    value varchar(255) not null
);

alter table products_localized_product
add constraint UK_n8q0vltkv2dgjclj2aqn26l03 unique(localized_product_id);

alter table shops_opening_hours
add constraint UK_cnkerx0e3gn4yuhpjkr1d7heu unique (opening_hours_id);

alter table products
add constraint FK7kp8sbhxboponhx3lxqtmkcoj foreign key (shop_id) references shops;

alter table products_categories
add constraint FKqt6m2o5dly3luqcm00f5t4h2p foreign key (category_id) references categories;

alter table products_categories
add constraint FKtj1vdea8qwerbjqie4xldl1el foreign key (product_id) references products;

alter table products_localized_product
add constraint FKjs8yfvw4we59oaei8c9txb4wy foreign key (localized_product_id) references localized_product;

alter table products_localized_product add constraint
FK6i2yelx9i3lagm1u7n6v0xnfh foreign key (product_id) references products;

alter table shops_opening_hours
add constraint FKti43xlm3mfbeodhgi4qn1yhgw foreign key (opening_hours_id) references opening_hours;

alter table shops_opening_hours
add constraint FK8dcjdnasobclsvyy8wjfki7gj foreign key (shop_id) references shops;

-- ========================================
-- INDEX POUR OPTIMISATION (E_AME_20)
-- ========================================

-- Index sur les foreign keys
create index if not exists idx_products_shop_id on products(shop_id);
create index if not exists idx_products_categories_product_id on products_categories(product_id);
create index if not exists idx_products_categories_category_id on products_categories(category_id);
create index if not exists idx_products_localized_product_product_id on products_localized_product(product_id);
create index if not exists idx_products_localized_product_localized_product_id on products_localized_product(localized_product_id);
create index if not exists idx_shops_opening_hours_shop_id on shops_opening_hours(shop_id);
create index if not exists idx_shops_opening_hours_opening_hours_id on shops_opening_hours(opening_hours_id);

-- Index pour les recherches fréquentes
create index if not exists idx_shops_created_at on shops(created_at);
create index if not exists idx_shops_in_vacations on shops(in_vacations);
create index if not exists idx_shops_name on shops(name);
create index if not exists idx_products_price on products(price);
create index if not exists idx_localized_product_locale on localized_product(locale);
create index if not exists idx_opening_hours_day on opening_hours(day);
