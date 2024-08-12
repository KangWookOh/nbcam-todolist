package com.example.todolist.Controller;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.ScheduleRepository;
import com.example.todolist.Service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        if(scheduleService.readByScheduleId(sid) == null ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(scheduleService.readByScheduleId(sid));
    }
    @GetMapping("/schedule")
    public ResponseEntity<List<Schedule>> getSchedules(@RequestBody(required = false) String writer, @RequestBody(required = false)LocalDate modDate) {
        List<Schedule> schedules = scheduleService.getSchedule(writer, modDate);
        if(schedules.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
            return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/update/{sid}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long sid, @RequestBody ScheduleRequestDto scheduleRequestDto) {

             Schedule update = scheduleService.updateTaskAndWriter(sid,scheduleRequestDto);
             return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/delete/{sid}")
    public ResponseEntity<Schedule> deleteSchedule(@PathVariable Long sid) {
        scheduleService.deleteBySid(sid);
        return ResponseEntity.noContent().build();
    }

}
