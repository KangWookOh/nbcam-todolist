package com.example.todolist.Controller;

import com.example.todolist.Controller.Status.StatusCode;
import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;

    @PostMapping("/register")
    public ResponseEntity<Schedule> register(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequestDto));
    }
}
