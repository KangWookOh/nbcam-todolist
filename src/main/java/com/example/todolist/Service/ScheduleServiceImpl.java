package com.example.todolist.Service;

import com.example.todolist.Dto.Schedule.ScheduleRequestDto;
import com.example.todolist.Dto.Schedule.ScheduleResponseDto;
import com.example.todolist.Entity.Schedule;
import com.example.todolist.Repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public ScheduleResponseDto readByScheduleId(Long sid) {
        Schedule schedule = scheduleRepository.findBySid(sid).orElseThrow(()->new NoSuchElementException("해당 할일은 존재 하지 않습니다."));
        ScheduleResponseDto scheduleResponseDto = ScheduleResponseDto.builder()
                .sid(sid)
                .task(schedule.getTask())
                .writer(schedule.getWriter())
                .password(schedule.getPassword())
                .build();
        return scheduleResponseDto;
    }

    @Override
    public List<Schedule> getSchedule(String writer, LocalDate modDate) {
        if(writer != null && modDate != null) {
            return scheduleRepository.findByWriterAndModDateOrderByModDateDesc(writer,modDate);
        }
        else if(writer != null) {
            return scheduleRepository.findByWriterOrderByModDateDesc(writer);
        }
        else if(modDate != null) {
            return scheduleRepository.findByModDateOrderByModDateDesc(modDate);
        }
        else
            return scheduleRepository.findAllByOrderByModDateDesc();
    }
}
