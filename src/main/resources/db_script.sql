CREATE USER guest WITH ENCRYPTED PASSWORD 'guest';

CREATE DATABASE river_um_service OWNER guest;

create table rv_user
(
    id           bigserial
        primary key,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(50) not null,
    phone_number varchar(20) not null,
    company varchar(100) not null,
    address varchar(100) not null,
    designation  varchar(50) not null,
    status  varchar(10) not null,
    created_date       timestamp      not null,
    last_modified_date timestamp
);

--alter table

alter table rv_user
    owner to guest;

-- auto-generated definition
create table user_friend
(
    id                 bigserial
        primary key,
    sender       integer        not null
        constraint fk_sender_account_id
            references rv_user,
    receiver         integer        not null
        constraint fk_receiver_account_id
            references rv_user,
    status             varchar(10)    not null,
    created_date       timestamp      not null,
    last_modified_date timestamp
);

alter table user_friend
    owner to guest;


-- Insert into rv_user (id is auto-generated)
INSERT INTO rv_user (
    first_name,
    last_name,
    email,
    phone_number,
    company,
    address,
    designation,
    status,
    created_date,
    last_modified_date
) VALUES (
    'Masum',
    'Billah',
    'masumbillah2k12@gmail.com',
    '01943177909',
    'PSL',
    'Dhaka, Bangladesh', -- example address
    'SSE',
    'ACTIVE',
    NOW(),       -- created_date
    NULL         -- last_modified_date
);
