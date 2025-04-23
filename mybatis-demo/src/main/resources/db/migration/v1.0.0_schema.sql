create table if not exists `goudan`
(
    id          int         not null auto_increment,
    `name`      varchar(50) not null,
    create_time datetime    not null default current_timestamp,
    primary key (id)
);

alter table goudan
    add column update_time datetime not null default current_timestamp;

CREATE TABLE if not exists `student`
(
    `id`          bigint                                                        NOT NULL,
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `sex`         tinyint                                                       NULL     DEFAULT NULL,
    `age`         int                                                           NULL     DEFAULT NULL,
    `sign`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `teacher_id`  bigint                                                        NOT NULL,
    `create_time` datetime(6)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `update_time` datetime(6)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `is_deleted`  int                                                           NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_name` (`name` ASC) USING BTREE,
    INDEX `FK3mphcmldvs29jl1w40ssg300j` (`teacher_id` ASC) USING BTREE
);

CREATE TABLE if not exists `teacher`
(
    `id`          bigint                                                        NOT NULL,
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `sex`         bit(1)                                                        NULL     DEFAULT NULL,
    `create_time` datetime(6)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `update_time` datetime(6)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE if not exists `user`
(
    `id`               int UNSIGNED                                                  NOT NULL AUTO_INCREMENT,
    `third_account_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '第三方用户ID',
    `user_name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '用户名',
    `password`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
    `login_type`       tinyint                                                       NOT NULL DEFAULT 0 COMMENT '登录方式: 0-微信登录，1-账号密码登录',
    `deleted`          tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `key_third_account_id` (`third_account_id` ASC) USING BTREE,
    INDEX `user_name` (`user_name` ASC) USING BTREE
);
