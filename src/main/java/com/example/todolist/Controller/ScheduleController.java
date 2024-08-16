package com.example.todolist.Controller;

import com.example.todolist.Dto.Schedule.ScheduleListResponse;
import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Exception.InvalidPasswordException;
import com.example.todolist.Exception.ScheduleNotFoundException;
import com.example.todolist.Repository.Schedule.ScheduleRepository;
import com.example.todolist.Service.Schedule.ScheduleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;
    private final ScheduleRepository scheduleRepository;

    @PostMapping("/register")
    public ResponseEntity<Schedule> register(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequestDto));
    }

    @GetMapping("/{sid}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long sid) {
        if(scheduleService.readByScheduleId(sid) == null ){
            throw new ScheduleNotFoundException("일정이 존재하지 않습니다.");
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
    public ResponseEntity<Schedule> updateSchedule(@Valid  @PathVariable Long sid, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        if(scheduleRequestDto.getPassword() == null || scheduleRequestDto.getPassword().isEmpty()){
            throw new InvalidPasswordException("비밀번호가 일치 하지 않습니다.");
        }
             Schedule update = scheduleService.updateTaskAndManager_id(sid,scheduleRequestDto);
             return ResponseEntity.ok(update);

    }

    @DeleteMapping("/delete/{sid}")
    public ResponseEntity<List<Schedule>> deleteSchedule(@PathVariable Long sid) {
        if(scheduleService.readByScheduleId(sid) == null){
            throw new ScheduleNotFoundException("이미 삭제된 일정입니다.");
        }
        scheduleService.deleteBySid(sid);
        List<Schedule> remainingSchedules =scheduleRepository.findAll();
        return new ResponseEntity<>(remainingSchedules,HttpStatus.OK);
    }

}
