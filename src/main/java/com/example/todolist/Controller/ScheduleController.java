package com.example.todolist.Controller;

import com.example.todolist.Controller.Status.StatusCode;
import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.ScheduleRepository;
import com.example.todolist.Service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;
    private final ScheduleRepository scheduleRepository;

    @PostMapping("/register")
    public ResponseEntity<Schedule> register(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequestDto));
    }

    @GetMapping("/{sid}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long sid) {
        return ResponseEntity.ok(scheduleService.readByScheduleId(sid));
    }
}
