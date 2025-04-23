Drop Table if exists `student`;
create table `student`
(
    id          bigint       not null primary key auto_increment,
    name        varchar(255) null,
    create_time datetime default current_timestamp,
    update_tiem datetime default current_timestamp on update current_timestamp
);
