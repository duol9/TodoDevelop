create table todos
(
    id          bigint auto_increment primary key,
    title       varchar(20) null,
    contents    longtext     null,
    user_id     bigint,
    creat_at    date         not null, -- 자동 생성
    modified_at date,                  -- 수정일 자동 반영
    constraint FK_user_id
        foreign key (user_id) references user (id)
);

create table user
(
    id         bigint auto_increment primary key,
    user_name  varchar(10)  not null,
    email      varchar(255),
    password   varchar(255) not null,
    created_at date         not null -- 자동 생성
);
