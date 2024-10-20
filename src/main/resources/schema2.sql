DROP TABLE IF EXISTS names;
create table names
(
    id   bigserial primary key,
    name varchar(255) not null,
    age  int not null
);