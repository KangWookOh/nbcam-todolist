package com.example.todolist.Service;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(ScheduleRequestDto scheduleRequestDto);
    ScheduleResponseDto readByScheduleId(Long sid);
    List<Schedule>getSchedule(String writer, LocalDate modDate);
    Schedule update(Long sid,ScheduleRequestDto scheduleRequestDto);

}
