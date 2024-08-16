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
// create Schedule
INSERT INTO schedule (task,password,manager_id,reg_Date,mod_Date) VALUES (?,?,?,?,?)
// Schedule findALL()
SELECT *FROM schedule
//Schedule findById
SELECT *FROM schedule WHERE sid =?

//findBySchedules
SELECT s.* FROM schedule s JOIN manager m ON s.manager_id = m.mid
// name 으로 찾을때
AND m.name =?

//ModDate(수정일)로 찾을때
AND s.mod_Date >= ? AND s.mod_Date < ?
페이징 집계

SELECT COUNT(*) FROM SELECT s.* FROM schedule s JOIN manager m ON s.manager_id = m.mid  AS count_query


//CreateUpdate
UPDATE schedule SET task = ?, manager_id = ?, mod_date = ? WHERE sid = ?

//DeleteByID
DELETE FROM schedule WHERE sid = ?


// CreateManager
INSERT INTO manager (name,email,uuid,reg_date,mod_date) VALUES (?,?,?,?,?)
