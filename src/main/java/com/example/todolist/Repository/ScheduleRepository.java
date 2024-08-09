package com.example.todolist.Repository;

import com.example.todolist.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("select s from Schedule s where s.sid =:sid")
    Optional<Schedule> findBySid(Long sid);

    List<Schedule> findByWriterAndModDateOrderByModDateDesc(String writer, LocalDate modDate);
    List<Schedule> findByWriterOrderByModDateDesc(String writer);
    List<Schedule>findByModDateOrderByModDateDesc(LocalDate modDate);
    List<Schedule> findAllByOrderByModDateDesc();

    

}
