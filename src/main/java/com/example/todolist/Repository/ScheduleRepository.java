package com.example.todolist.Repository;

import com.example.todolist.Entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;


    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule scheduleRequestDto) {
        String sql = "INSERT INTO schedule (task, writer, password,reg_Date,mod_Date) VALUES (?, ?, ?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDate currentTime = LocalDate.now();
        jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"sid"});
            ps.setString(1, scheduleRequestDto.getTask());
            ps.setString(2, scheduleRequestDto.getWriter());
            ps.setString(3, scheduleRequestDto.getPassword());
            ps.setDate(4, Date.valueOf(currentTime));
            ps.setDate(5, Date.valueOf(currentTime));
            return ps;
        }, keyHolder);
        Long newScheduleSid = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return  Schedule.builder()
                .sid(newScheduleSid)
                .task(scheduleRequestDto.getTask())
                .writer(scheduleRequestDto.getWriter())
                .password(scheduleRequestDto.getPassword())
                .regDate(currentTime)
                .modDate(currentTime)
                .build();
    }

    public Schedule findById(Long sid) {
        String sql = "SELECT * FROM schedule WHERE sid = ?";
        try{
            return jdbcTemplate.queryForObject(sql,new ScheduleRowMapper(),sid);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<Schedule> findByScheduleByWriterAndModDate(String writer, LocalDate modDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<String> params = new ArrayList<>();
        if(writer != null){
            sql.append(" AND writer = ?");
            params.add(writer);
        }
        if(modDate != null){
            sql.append(" AND mod_Date = ?");
            params.add(String.valueOf(modDate));
        }
        sql.append(" ORDER BY mod_Date DESC");

        return jdbcTemplate.query(sql.toString(),new ScheduleRowMapper(),params.toArray());


    }

    public Schedule update(Schedule schedule) {
        String sql = "UPDATE schedule SET task = ?, writer = ?, mod_date = ? WHERE sid = ?";
        LocalDate currentTime =LocalDate.now();

        // Schedule의 `modDate`를 업데이트하고 저장
        jdbcTemplate.update(sql, schedule.getTask(), schedule.getWriter(), currentTime, schedule.getSid());

        // 업데이트 후, 변경된 Schedule 객체를 반환
        return Schedule.builder()
                .sid(schedule.getSid())
                .task(schedule.getTask())
                .writer(schedule.getWriter())
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
                   .writer(rs.getString("writer"))
                   .password(rs.getString("password"))
                   .regDate(rs.getDate("reg_date") != null ? rs.getDate("reg_date").toLocalDate() : null)
                   .modDate(rs.getDate("mod_date") != null ? rs.getDate("mod_date").toLocalDate() : null)
                   .build();




        }
    }
}