package com.example.todolist.Service.Schedule;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;


public interface ScheduleService {
    Schedule createSchedule(ScheduleRequestDto scheduleRequestDto);

    ScheduleResponseDto readByScheduleId(Long sid);

    Page<ScheduleResponseDto> getSchedule(String name, LocalDate modDate, Pageable pageable) ;

    Schedule updateTaskAndManager_id(Long sid, ScheduleRequestDto scheduleRequestDto);

    Schedule deleteBySid(Long sid);

}
