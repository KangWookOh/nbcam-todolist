package com.example.todolist.Repository;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Schedule> findByWriterAndModDate(String writer, LocalDate modDate) {
        String sql = "SELECT * FROM schedule WHERE writer = ? AND DATE(mod_date) = ? ORDER BY mod_date DESC";
        return jdbcTemplate.query(sql, new Object[]{writer, modDate}, new ScheduleRowMapper());
    }

    public List<Schedule> findByWriter(String writer) {
        String sql = "SELECT * FROM schedule WHERE writer = ? ORDER BY mod_date DESC";
        return jdbcTemplate.query(sql, new Object[]{writer}, new ScheduleRowMapper());
    }

    public List<Schedule> findByModDate(LocalDate modDate) {
        String sql = "SELECT * FROM schedule WHERE DATE(mod_date) = ? ORDER BY mod_date DESC";
        return jdbcTemplate.query(sql, new Object[]{modDate}, new ScheduleRowMapper());
    }

    public List<Schedule> findAll() {
        String sql = "SELECT * FROM schedule ORDER BY mod_date DESC";
        return jdbcTemplate.query(sql, new ScheduleRowMapper());
    }

    public int update(Schedule schedule) {
        String sql = "UPDATE schedule SET task = ?, writer = ?, mod_date = ? WHERE sid = ?";
        return jdbcTemplate.update(sql, schedule.getTask(), schedule.getWriter(), schedule.getModDate(), schedule.getSid());
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