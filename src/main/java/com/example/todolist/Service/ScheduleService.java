package com.example.todolist.Service;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Entity.Schedule;

public interface ScheduleService {
    Schedule createSchedule(ScheduleRequestDto scheduleRequestDto);
}
