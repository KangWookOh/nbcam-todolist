package com.example.todolist.Service.Schedule;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Manager;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Exception.InvalidPasswordException;
import com.example.todolist.Exception.ScheduleNotFoundException;
import com.example.todolist.Repository.Manager.ManagerRepository;
import com.example.todolist.Repository.Schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final ManagerRepository managerRepository;
    LocalDate currentDate = LocalDate.now();

    @Transactional
    @Override
    public Schedule createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = Schedule.builder()
                .task(scheduleRequestDto.getTask())
                .password(scheduleRequestDto.getPassword())
                .manager_id(scheduleRequestDto.getManager_id())
                .build();
        return scheduleRepository.createSchedule(schedule);
    }

    @Override
    public ScheduleResponseDto readByScheduleId(Long sid) {
        Schedule schedule = scheduleRepository.findById(sid);
        Manager manager = managerRepository.findById(schedule.getManager_id());
        if(schedule == null) {
            throw new ScheduleNotFoundException("일정이 존재 하지 않습니다");
        }
        return ScheduleResponseDto.from(schedule,manager);
    }

    @Override
    public Page<ScheduleResponseDto> getSchedule(String name, LocalDate modDate, Pageable pageable) {

            Page<Schedule> schedules = scheduleRepository.findBySchedules(name,modDate,pageable);
            return schedules.map(schedule -> ScheduleResponseDto.from(schedule,managerRepository.findById(schedule.getManager_id())));

        }
    @Transactional
    @Override
    public Schedule updateTaskAndManager_id(Long sid, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(sid);
        if(!(schedule.getPassword().equals(scheduleRequestDto.getPassword()))){
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다");
        }
        Schedule update = Schedule.builder()
                .sid(sid)
                .task(scheduleRequestDto.getTask())
                .manager_id(scheduleRequestDto.getManager_id())
                .modDate(Timestamp.valueOf(schedule.getModDate()).toLocalDateTime())
                .build();
        return scheduleRepository.update(update);
    }

    @Transactional
    @Override
    public Schedule deleteBySid(Long sid) {
        Schedule schedule =scheduleRepository.findById(sid);
        if(schedule == null){
            throw new ScheduleNotFoundException("일정이 존재 하지 않습니다.");
        }
        scheduleRepository.deleteById(sid);

        return schedule;
    }
}

