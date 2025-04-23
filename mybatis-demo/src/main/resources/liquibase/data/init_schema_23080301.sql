alter table user
    drop column real_name;
alter table student
    add column real_name varchar(50) null comment '真实姓名'