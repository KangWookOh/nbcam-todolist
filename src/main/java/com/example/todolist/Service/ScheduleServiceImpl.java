package com.example.todolist.Service;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule createSchedule(ScheduleRequestDto scheduleRequestDto) {
            Schedule schedule =Schedule.builder()
                    .task(scheduleRequestDto.getTask())
                    .writer(scheduleRequestDto.getWriter())
                    .password(scheduleRequestDto.getPassword())
                    .build();
            return scheduleRepository.save(schedule);
    }
}
