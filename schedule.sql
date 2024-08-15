create table schedule
(
    sid        bigint auto_increment
        primary key,
    mod_date   datetime(6)  null,
    reg_date   datetime(6)  null,
    manager_id bigint       null,
    password   varchar(255) not null,
    task       varchar(200) null,
    constraint fk_manager
        foreign key (manager_id) references manager (mid)
);


create table manager
(
    mid      bigint auto_increment
        primary key,
    mod_date datetime(6)  null,
    reg_date datetime(6)  null,
    email    varchar(255) not null,
    name     varchar(255) null,
    uuid     varchar(255) null
);

ALTER TABLE `schedule` ADD CONSTRAINT `fk_manager` FOREIGN KEY (`manager_id`) REFERENCES `manager`(`mid`)

