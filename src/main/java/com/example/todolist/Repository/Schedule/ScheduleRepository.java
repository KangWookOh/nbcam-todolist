package com.example.todolist.Repository.Schedule;

import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.Manager.ManagerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ManagerRepository managerRepository;


    public ScheduleRepository(JdbcTemplate jdbcTemplate, ManagerRepository managerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.managerRepository = managerRepository;
    }

    public Schedule createSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedule (task,password,manager_id,reg_Date,mod_Date) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime currentTime = LocalDateTime.now();
        jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"sid"});
            ps.setString(1, schedule.getTask());
            ps.setString(2, schedule.getPassword());
            ps.setLong(3, schedule.getManager_id());
            ps.setObject(4,Timestamp.valueOf(currentTime));
            ps.setObject(5, Timestamp.valueOf(currentTime));
            return ps;
        }, keyHolder);
        Long newScheduleSid = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return  Schedule.builder()
                .sid(newScheduleSid)
                .task(schedule.getTask())
                .password(schedule.getPassword())
                .manager_id(schedule.getManager_id())
                .regDate(currentTime)
                .modDate(currentTime)
                .build();
    }

    public Schedule findById(Long sid) {
        String sql ="SELECT *FROM schedule WHERE sid=?";
        try{
            return jdbcTemplate.queryForObject(sql,new ScheduleRowMapper(),sid);

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Page<Schedule> findBySchedules(String name, LocalDate modDate, Pageable pageable){
        StringBuilder sql = new StringBuilder("SELECT s.* FROM schedule s JOIN manager m ON s.manager_id = m.mid ");
        List<Object> params = new ArrayList<>();
        if(name != null && !name.isEmpty()){
            sql.append(" AND m.name = ?");
            params.add(name);
        }
        if(modDate != null){
            sql.append(" AND s.mod_Date >= ? AND s.mod_Date < ? ");
            params.add(modDate.atStartOfDay());
            params.add(modDate.plusDays(1).atStartOfDay());
        }

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") AS count_query";
        int totalCount = jdbcTemplate.queryForObject(countSql, Integer.class, params.toArray());

        int offset = (int) pageable.getOffset();
        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add(offset);
        List<Schedule>schedules = jdbcTemplate.query(sql.toString(),new ScheduleRowMapper(),params.toArray());
        return new PageImpl<>(schedules,pageable,totalCount);

    }




    public Schedule update(Schedule schedule) {
        String sql = "UPDATE schedule SET task = ?, manager_id = ?, mod_date = ? WHERE sid = ?";
        LocalDateTime currentTime = LocalDateTime.now();

        // Schedule의 `modDate`를 업데이트하고 저장
        jdbcTemplate.update(sql, schedule.getTask(), schedule.getManager_id(), currentTime, schedule.getSid());

        // 업데이트 후, 변경된 Schedule 객체를 반환
        return Schedule.builder()
                .sid(schedule.getSid())
                .task(schedule.getTask())
                .manager_id(schedule.getManager_id())
                .regDate(currentTime)
                .modDate(currentTime)
                .build();
    }

    public int deleteById(Long sid) {
        String sql = "DELETE FROM schedule WHERE sid = ?";
        return jdbcTemplate.update(sql, sid);
    }

    private static class ScheduleRowMapper implements RowMapper<Schedule> {

        @Override
        public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
           return Schedule.builder()
                   .sid(rs.getLong("sid"))
                   .task(rs.getString("task"))
                   .password(rs.getString("password"))
                   .manager_id(rs.getLong("manager_id"))
                   .regDate(rs.getTimestamp("reg_Date").toLocalDateTime())
                   .modDate(rs.getTimestamp("mod_Date").toLocalDateTime())
                   .build();




        }
    }
}