-- auto-generated definition
create table user
(
    id         bigint auto_increment comment 'id'
        primary key,
    account    varchar(256)                       null comment '账号',
    username   varchar(256)                       null comment '用户昵称',
    avatar     varchar(1024)                      null comment '用户头像',
    gender     tinyint                            null comment '性别',
    password   varchar(512)                       not null comment '密码',
    email      varchar(512)                       null comment '邮箱',
    status     int      default 0                 not null comment '用户状态 0是正常',
    createTime datetime default CURRENT_TIMESTAMP not null comment '用户创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null comment ' 更新时间',
    isDelete   tinyint  default 0                 not null comment '用户是否已被删除，0是没有删除',
    role       int      default 0                 not null comment '用户身份辨识  --0：普通用户  --1：管理员'
);

