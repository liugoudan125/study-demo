create table `goudan`
(
    id          int         not null auto_increment,
    `name`      varchar(50) not null,
    create_time datetime    not null default current_timestamp,
    primary key (id)
);

alter table goudan add column update_time datetime not null default current_timestamp;