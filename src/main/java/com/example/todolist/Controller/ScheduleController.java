package com.example.todolist.Controller;

import com.example.todolist.Dto.Schedule.ScheduleListResponse;
import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.Schedule.ScheduleRepository;
import com.example.todolist.Service.Schedule.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
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
    @GetMapping("/list")
    public ResponseEntity<ScheduleListResponse> getSchedules(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) LocalDate modDate,
                                                             @PageableDefault(page = 0, size = 10, sort = "modDate", direction = Sort.Direction.DESC) Pageable pageable) {

       ScheduleListResponse scheduleListResponse = ScheduleListResponse.of(scheduleService.getSchedule(name,modDate,pageable));
       return ResponseEntity.ok(scheduleListResponse);

    }

    @PutMapping("/update/{sid}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long sid, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        if(scheduleRequestDto.getPassword() == null || scheduleRequestDto.getPassword().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
             Schedule update = scheduleService.updateTaskAndManager_id(sid,scheduleRequestDto);
             return ResponseEntity.ok(update);

    }

    @DeleteMapping("/delete/{sid}")
    public ResponseEntity<Schedule> deleteSchedule(@PathVariable Long sid) {
        Schedule schedule= scheduleService.deleteBySid(sid);
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }

}
