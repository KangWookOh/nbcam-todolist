package com.example.todolist.Repository.Manager;

import com.example.todolist.Entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.todolist.Util.UUIDUitls.generateShortUUID;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public Manager createManager(Manager manager) {
        String sql ="INSERT INTO manager (name,email,uuid,reg_date,mod_date) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime currentDate = LocalDateTime.now();
        String uuid = generateShortUUID();
        jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"mid"});
            ps.setString(1,manager.getName());
            ps.setString(2,manager.getEmail());
            ps.setString(3,uuid);
            ps.setObject(4, Timestamp.valueOf(currentDate));
            ps.setObject(5, Timestamp.valueOf(currentDate));
            return ps;
        },keyHolder);
        Long newMid = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return Manager.builder()
                .mid(newMid)
                .name(manager.getName())
                .email(manager.getEmail())
                .uuid(uuid)
                .regDate(currentDate)
                .modDate(currentDate)
                .build();
    }
    public Manager findById(Long mid){
        String sql ="SELECT * FROM manager WHERE mid=?";
        try {
            return jdbcTemplate.queryForObject(sql,new ManagerRowMapper(),mid);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    private static class ManagerRowMapper implements RowMapper<Manager> {
        @Override
        public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
            String uuid = generateShortUUID();
            return Manager.builder()
                    .mid(rs.getLong("mid"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .uuid(uuid)
                    .regDate(rs.getTimestamp("reg_date").toLocalDateTime())
                    .regDate(rs.getTimestamp("mod_date").toLocalDateTime())
                    .build();
        }


    }
}







