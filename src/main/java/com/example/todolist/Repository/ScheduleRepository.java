package com.example.todolist.Repository;

import com.example.todolist.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("select s from Schedule s where s.sid =:sid")
    Optional<Schedule> findBysid(Long sid);
}
